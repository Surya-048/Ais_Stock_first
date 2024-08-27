package com.watsoo.device.management.controller;
import com.watsoo.device.management.dto.*;

import com.watsoo.device.management.service.DeviceRenewalRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class DeviceRenewalController {


    @Autowired
    private DeviceRenewalRequestService deviceRenewalRequestService;


    @PostMapping("/save/device_renewal_request")
    public Response<?> saveDeviceRenewalData(@RequestBody DeviceRenewalRequestDTO deviceRenewalRequest){

         Response<?> response= deviceRenewalRequestService.saveDeviceRenewalRequest(deviceRenewalRequest);


        return  response;
    }


    @PostMapping(path = "/get/All/device_renewal_request/")
    public ResponseEntity<?> getDeviceRenewalRequest(@RequestBody GenericRequestBody genericRequestBody){

        PaginationV2<?> deviceRenewalRequest = this.deviceRenewalRequestService.getDeviceRenewalRequest(genericRequestBody);

        return new ResponseEntity<>(deviceRenewalRequest, HttpStatus.OK);
    }

    @GetMapping(path = "/get/one/device_renewal_request/{reqId}")
    public  ResponseEntity<?> getDeviceRenewalRequest(@PathVariable("reqId")Long reqId){

        Response<?> renewalRequest = this.deviceRenewalRequestService.getDeviceRenewalRequest(reqId);

        return ResponseEntity.ok(renewalRequest);
    }

}
