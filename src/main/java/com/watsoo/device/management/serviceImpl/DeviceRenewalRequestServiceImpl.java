package com.watsoo.device.management.serviceImpl;

import com.watsoo.device.management.constant.Constant;
import com.watsoo.device.management.dto.*;
import com.watsoo.device.management.exception.ResourceNotFoundException;
import com.watsoo.device.management.model.*;
import com.watsoo.device.management.repository.*;
import com.watsoo.device.management.service.DeviceRenewalRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DeviceRenewalRequestServiceImpl implements DeviceRenewalRequestService {

    Logger logger = LoggerFactory.getLogger(DeviceRenewalRequestServiceImpl.class);


    //for storing all the requests in hashMap mapping with id for servicing like caching
    HashMap<Long,DeviceRenewalRequest> renewalRequestsMap = new HashMap<>();
    HashMap<Long,List<RenewalDevice>> renewalDevicesList = new HashMap<>();

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private IccidMasterRepository iccidMasterRepository;

    @Autowired
    private UserRepository userRepository;

//    private static RenewalDevice renewalDevice;

    private static DeviceRenewalSavedDataResponse deviceRenewalSavedDataResponse=null;

    @Autowired
    private DeviceLazyRepository deviceLazyRepository;
    @Autowired
    private DeviceRenewalRequestRepository deviceRenewalRequestRepository;

    @Autowired
    private RenewalDeviceRepository renewalDeviceRepository;

    int iccidNotFoundCount=0;
    @Override
    public Response<?> saveDeviceRenewalRequest(DeviceRenewalRequestDTO deviceRenewalRequestDTO) {

        Optional<User> user= userRepository.findById( deviceRenewalRequestDTO.getUserId());

        String requestCode = generateRequestCode();
        if(user.isPresent()==false){
            new Response<>(HttpStatus.NOT_FOUND.value(), "User doesnot exists");
        }

//         DeviceRenewalRequest deviceRenewalRequest=new DeviceRenewalRequest();
//         deviceRenewalRequest.setCreatedBy(user.get().getId());
//         deviceRenewalRequest.setReqCode(requestCode);

        List<DeviceRenewalSavedDataResponse> deviceRenewalSavedDataResponses = new ArrayList<>();

        List<DeviceRenewal> deviceRenewalList= deviceRenewalRequestDTO.getDeviceRenewalList();
        List<String> iccidNosList=new ArrayList<>();

        deviceRenewalList.stream().forEach(item->iccidNosList.add(item.getIccidNo()));

        List<Device> foundDevices=  deviceRepository.findByIccidNoIn(iccidNosList);

        Map<String,Device> deviceMap = foundDevices.stream().collect(Collectors.toMap(Device::getIccidNo,device -> device));

        //Test This
//         Map<String,Device> deviceMap = foundDevices.stream().collect(Collectors.toMap(e->e.getIccidNo(),e -> e));

        this.iccidNotFoundCount=0;

        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");

        DeviceRenewalRequest deviceRenewalRequest=new DeviceRenewalRequest();
        deviceRenewalRequest.setCreatedBy(user.get().getId());
        deviceRenewalRequest.setReqCode(requestCode);
        deviceRenewalRequest.setCreatedAt(new Date());

        for(DeviceRenewal item:deviceRenewalList){
            String iccidNo=item.getIccidNo();

            DeviceRenewalSavedDataResponse deviceRenewalSavedDataResponse=new DeviceRenewalSavedDataResponse();
            RenewalDevice renewalDevice=new RenewalDevice();

            if(deviceMap.containsKey(iccidNo)){
                Device device=deviceMap.get(iccidNo);
                renewalDevice.setDeviceId(device.getId());
                renewalDevice.setImeiNo(device.getImeiNo());
                renewalDevice.setIccidNo(device.getIccidNo());
                renewalDevice.setOldExpiryDate(device.getSim2ExpiryDate());

                deviceRenewalSavedDataResponse.setIccidNo(device.getIccidNo());

                try {
                    Date date=inputFormat.parse(item.getDate());
                    renewalDevice.setNewExpiryDate(date);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                //only Saving DeviceRenewalRequestObject to Data Base when the device id is found so no new request code is generated
                DeviceRenewalRequest savedDeviceRenewalRequestObject= deviceRenewalRequestRepository.save(deviceRenewalRequest);

                //saving RenewalDevice Object to DB
                renewalDevice.setDeviceRenewalRequest(savedDeviceRenewalRequestObject);
                RenewalDevice savedRenewalDeviceObject= renewalDeviceRepository.save(renewalDevice);

                //For Updating in DashBoard
                device.setSim1ExpiryDate(savedRenewalDeviceObject.getNewExpiryDate());
                device.setSim2ExpiryDate(savedRenewalDeviceObject.getNewExpiryDate());
                device.setUpdatedAt(new Date());
                device.setModifiedBy(user.get().getId());
                deviceRepository.save(device);


                deviceRenewalSavedDataResponse.setNewExpiryDate(savedRenewalDeviceObject.getNewExpiryDate());
                deviceRenewalSavedDataResponse.setUpdated(true);


            }
            else{
                //No Such ICCID Found
                try {
                    Date date = inputFormat.parse(item.getDate());
                    deviceRenewalSavedDataResponse.setNewExpiryDate(date);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                deviceRenewalSavedDataResponse.setUpdated(false);
                deviceRenewalSavedDataResponse.setIccidNo(iccidNo);
                iccidNotFoundCount++;
            }
            deviceRenewalSavedDataResponses.add(deviceRenewalSavedDataResponse);
        }


        if (iccidNotFoundCount == 0) {
            return new Response<>(HttpStatus.OK.value(),   deviceRenewalSavedDataResponses,"Updated Successfully",requestCode);
        } else if (iccidNotFoundCount == deviceRenewalList.size()) {
            return new Response<>(HttpStatus.NOT_FOUND.value(),  deviceRenewalSavedDataResponses,"No Such ICCIDs Found", requestCode);
        } else {
            return new Response<>(HttpStatus.PARTIAL_CONTENT.value(),   deviceRenewalSavedDataResponses,"Updated Successfully with some unsuccessful attempts (No such ICCID Found)", requestCode);
        }

    }

    private String generateRequestCode() {
        String businessPrefix = "REQ";
        UUID uuid = UUID.randomUUID();
        String uniqueRequestCode = businessPrefix + "-" + uuid.toString();
        return  uniqueRequestCode;
    }

    @Override
    public PaginationV2<?> getDeviceRenewalRequest(GenericRequestBody genericRequestBody) {

        PageRequest pageRequest = PageRequest.of(genericRequestBody.getPageNo(), genericRequestBody.getPageSize(), Sort.Direction.DESC, "id");
        PaginationV2 paginationV2 = new PaginationV2();

        Page<DeviceRenewalRequest> deviceRenewalRequestsPaging = null;

        //for setting the date in genericRequestBody like timeStamp
        if ((genericRequestBody.getFromDate() != null && genericRequestBody.getToDate() != null) && genericRequestBody.getPageSize() != 0
                && (genericRequestBody.getFromDate() !=0 || genericRequestBody.getToDate() != 0 ) ) {

            logger.info("From Date Receive : "+genericRequestBody.getFromDate());
            logger.info("To Date Receive : "+genericRequestBody.getToDate());

            SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
            String fromDate = sdf.format(new Date(genericRequestBody.getFromDate()));
            String toDate = sdf.format(new Date(genericRequestBody.getToDate()));
            logger.info("After converting From Date to UTC : "+ new SimpleDateFormat(Constant.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS).format(genericRequestBody.getFromDate()));
            logger.info("After converting To Date to UTC  :"+new SimpleDateFormat(Constant.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS).format(genericRequestBody.getToDate()));

            try {
                genericRequestBody.setFromDateToDateType(sdf.parse(fromDate));
                genericRequestBody.setToDateToDateType(sdf.parse(toDate));
            } catch (ParseException e) {
                throw new RuntimeException("Date Parsing Exception");
            }
        }

//        if (genericRequestBody.getSearch() != null && !genericRequestBody.getSearch().isEmpty() && !genericRequestBody.getSearch().equals("")) {

        if(genericRequestBody.getSearch().matches("^[0-9]+")){
            List<RenewalDevice> collect = this.renewalDeviceRepository
                    .findAllByImeiNoContaining(genericRequestBody.getSearch().trim());
            HashMap<Long,DeviceRenewalResponseDTO> redundantCheckingSettForRequestCode = new HashMap<>();

            collect
                    .forEach(renew ->
                    {
                        if (renew.getDeviceRenewalRequest().getReqCode() != null && !renew.getDeviceRenewalRequest().getReqCode().equals("")) {
                            Optional<DeviceRenewalRequest> byId = this.deviceRenewalRequestRepository.findById(renew.getDeviceRenewalRequest().getId());

                            if (byId.isPresent() && redundantCheckingSettForRequestCode.get(byId.get().getId()) == null ) {
                                Optional<User> user = this.userRepository.findById(byId.get().getCreatedBy());
                                if (user.isPresent()) {
                                    DeviceRenewalResponseDTO deviceRenewalResponse = new DeviceRenewalResponseDTO();
                                    deviceRenewalResponse.setRequestId(byId.get().getId());
                                    deviceRenewalResponse.setRequestCode(byId.get().getReqCode());
                                    deviceRenewalResponse.setRequestDate(byId.get().getCreatedAt());
                                    deviceRenewalResponse.setCreatedBy(user.get().getName());
                                    deviceRenewalResponse.setDevices(new ArrayList<>());
                                    deviceRenewalResponse.setTotalDevices(this.renewalDeviceRepository.deviceCountForRequest(byId.get().getId()));
                                    redundantCheckingSettForRequestCode.put(deviceRenewalResponse.getRequestId(),deviceRenewalResponse);
                                }
                            }
                        }
                    });

            //all the device list having given imei no
            List<DeviceRenewalResponseDTO> allById = redundantCheckingSettForRequestCode
                    .values()
                    .stream()
                    .collect(Collectors.toList());

            List<DeviceRenewalResponseDTO> output = new ArrayList<>();

            //for pagination

            for(int i = (genericRequestBody.getPageNo()*genericRequestBody.getPageSize()),count = 0;i< allById.size() && count < genericRequestBody.getPageSize(); i++,++count){
                output.add(allById.get(i));
            }

            paginationV2.setPageSize(genericRequestBody.getPageSize());
            paginationV2.setTotalItems(allById.size());
            paginationV2.setItems(output);

            return paginationV2;
//            }else{
//                deviceRenewalRequestsPaging = this.deviceRenewalRequestRepository.findByReqCode(genericRequestBody.getSearch(), pageRequest);
//            }

//        } else if(genericRequestBody.getFromDate() == 0 && genericRequestBody.getToDate() == 0
//                && genericRequestBody.getSearch().equals("") && genericRequestBody.getSearch().isEmpty()){
//
//            deviceRenewalRequestsPaging = this.deviceRenewalRequestRepository.findAll(pageRequest);
//
        }
        else {
            deviceRenewalRequestsPaging = this.deviceRenewalRequestRepository.findAllForSearching(genericRequestBody,pageRequest);
        }

        if(deviceRenewalRequestsPaging != null && !deviceRenewalRequestsPaging.isEmpty()) {

            List<DeviceRenewalResponseDTO> deviceRenewalResponse = deviceRenewalRequestsPaging
                    .get()
                    .map(object -> {
                        DeviceRenewalResponseDTO deviceRenewalResponseDTO = new DeviceRenewalResponseDTO();
                        try {

                            Optional<User> user = userRepository.findById(object.getCreatedBy());
                            if (user.isPresent()) {
                                deviceRenewalResponseDTO.setRequestId(object.getId());
                                deviceRenewalResponseDTO.setRequestCode(object.getReqCode());
                                deviceRenewalResponseDTO.setCreatedBy(user.get().getName());
                                deviceRenewalResponseDTO.setRequestDate(object.getCreatedAt());
                                deviceRenewalResponseDTO.setDevices(new ArrayList<>());
                                deviceRenewalResponseDTO.setTotalDevices(this.renewalDeviceRepository.deviceCountForRequest(object.getId()));
                            }
                        } catch (NoSuchElementException exception) {
                            throw new ResourceNotFoundException("User Not Found With Give ID");
                        }
                        return deviceRenewalResponseDTO;
                    })
                    .collect(Collectors.toList());

            paginationV2.setTotalItems(deviceRenewalRequestsPaging.getTotalElements());
            paginationV2.setPageSize(genericRequestBody.getPageSize());
            paginationV2.setItems(deviceRenewalResponse);
        }else {
            paginationV2.setItems(new ArrayList<>());
        }


        return  paginationV2;
    }

    @Override
    public Response<?> getDeviceRenewalRequest(Long reqId) {

        //for end response
        Response<List> response = new Response<>();


        DeviceRenewalRequest request = null;
        List<RenewalDevice> allByRequestId = null;

        if(reqId != 0){
            if(renewalRequestsMap.get(reqId) != null)
            {
                request = renewalRequestsMap.get(reqId);
                logger.info("Inside the mapping for request");
            }
            else{
                request = this.deviceRenewalRequestRepository.findById(reqId).get();
                renewalRequestsMap.put(reqId,request);
                logger.info("Inside the repo and mapping for request");
            }
        }

        if(request != null){

            try {

                if(renewalDevicesList.get(reqId) != null){
                    allByRequestId = renewalDevicesList.get(reqId);
                    logger.info("Inside the mapping for renewal Device list");
                }
                else {
                    allByRequestId = this.renewalDeviceRepository.findAllByRequestId(reqId);

                    renewalDevicesList.put(reqId,allByRequestId);

                    logger.info("Inside the repo and mapping for renewal Device list");
                }

                response.setMessage("Task Completed");
                response.setRequestCode(HttpStatus.OK.getReasonPhrase());
                response.setResponseCode(HttpStatus.OK.value());
                response.setData(allByRequestId);

            }catch (NoSuchElementException e){
                throw new ResourceNotFoundException("No Request Present with Request Id : "+reqId);
            }
        }else {
            response.setMessage("No DATA");
            response.setRequestCode(HttpStatus.NOT_FOUND.getReasonPhrase());
            response.setResponseCode(HttpStatus.NOT_FOUND.value());
            response.setData(new ArrayList<>());
        }
        return response;
    }
}