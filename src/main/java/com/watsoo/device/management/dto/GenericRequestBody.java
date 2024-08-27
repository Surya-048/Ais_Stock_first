package com.watsoo.device.management.dto;

import java.util.Date;
import java.util.List;

import com.watsoo.device.management.enums.LastOnline;
import com.watsoo.device.management.enums.StatusMaster;

public class GenericRequestBody {

	private Long requestId;
	private String version;

	private Integer pageNo;

	private Integer pageSize;

	private Long fromDate;

	private Long toDate;

	private Long userId;

	private String token;

	private UserDTO createdBy;

	private Long createdById;

	private String createdByName;

	private String imeiNo;

	private String iccidNo;

	private String uuidNo;

	private String softwareVersion;

	private String search;

	private Long stateId;

	private StatusMaster statusMaster;

	private List<Long> ids;

	private Boolean showTabData;

	private Date updatedAt;

	private Long deviceId;

	private List<TcPackets> packetList;

	private String imeiNumber;

	private Boolean isConfigSent;

	private Boolean isConfigDone;

	private Boolean isConfigActive;

	private List<Long> clientIds;

	private List<String> softwareVersionList;

	private Boolean onlineDevice;

	private LastOnline lastOnline;

	private String simOperator;

	private List<StatusMaster> status;

	private Long clientId;

	private List<String> imeiNoList;

	private Integer minRenwalRequireDays;

	private Long simActivationDate;

	private Long appActivationDate;

	private Integer addSimExpireDays;

	private Integer addAppExpireDays;

	private String notifyEmailId;

	private Boolean isCompleted;

	private Long id;

	private Long modelId;

	private Long simProviderId;

	private String systemLotId;

	private String remark;

	private Long lotId;

	private Boolean isRejected;

	private Boolean isProcessing;

	private String deviceStatus;

	private Long masterId;

	private List<SubscriptionAmountDTO> subscriptionAmount;

	private String userName;

	private Double statePlatformCharges;

	private Integer totalDevice;

	private String debitNoteImage;

	private String debitNoteNo;

	private String ewayBillImage;

	private String ewayBillNo;

	private String reason;

	private Long returnDeviceMasterId;

	private Long repairDeviceMasterId;

	private Long repairDeviceId;

	private String openBoxImageBeforeTesting;

	private String closeBoxImageBeforeTesting;

	private String openBoxImageAfterTesting;

	private String closeBoxImageAfterTesting;

	private String rejectReason;

	private List<Long> chargesId;

	private Long replacedByDeviceId;

	private String companyName;

	private Long boxId;

	private String command;

	private String response;

	private Long reConfigureBoxId;

	private String boxCode;

	private Boolean isReConfigured;

	private String mfgLotId;

	private List<String> imeiOrBoxNoList;

	private Long requestedBy;
	
	private Long platformId;
	
	private Boolean isBoxSearch;
	
	private String reConfigBoxCode;
	private Date fromDateToDateType;
	private Date toDateToDateType;

	public Date getFromDateToDateType ( ) {
		return fromDateToDateType;
	}

	public void setFromDateToDateType ( Date fromDateToDateType ) {
		this.fromDateToDateType = fromDateToDateType;
	}

	public Date getToDateToDateType ( ) {
		return toDateToDateType;
	}

	public void setToDateToDateType ( Date toDateToDateType ) {
		this.toDateToDateType = toDateToDateType;
	}

	public Long getRequestId ( ) {
		return requestId;
	}

	public void setRequestId ( Long requestId ) {
		this.requestId = requestId;
	}

	public Long getMasterId() {
		return masterId;
	}

	public void setMasterId(Long masterId) {
		this.masterId = masterId;
	}

	public GenericRequestBody() {
		super();
	}

	public GenericRequestBody(String version) {
		super();
		this.version = version;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Long getFromDate() {
		return fromDate;
	}

	public void setFromDate(Long fromDate) {
		this.fromDate = fromDate;
	}

	public Long getToDate() {
		return toDate;
	}

	public void setToDate(Long toDate) {
		this.toDate = toDate;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getImeiNo() {
		return imeiNo;
	}

	public void setImeiNo(String imeiNo) {
		this.imeiNo = imeiNo;
	}

	public String getIccidNo() {
		return iccidNo;
	}

	public void setIccidNo(String iccidNo) {
		this.iccidNo = iccidNo;
	}

	public String getUuidNo() {
		return uuidNo;
	}

	public void setUuidNo(String uuidNo) {
		this.uuidNo = uuidNo;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public StatusMaster getStatusMaster() {
		return statusMaster;
	}

	public void setStatusMaster(StatusMaster statusMaster) {
		this.statusMaster = statusMaster;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public Boolean getShowTabData() {
		return showTabData;
	}

	public void setShowTabData(Boolean showTabData) {
		this.showTabData = showTabData;
	}

	public UserDTO getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserDTO createdBy) {
		this.createdBy = createdBy;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public List<TcPackets> getPacketList() {
		return packetList;
	}

	public void setPacketList(List<TcPackets> packetList) {
		this.packetList = packetList;
	}

	public String getImeiNumber() {
		return imeiNumber;
	}

	public void setImeiNumber(String imeiNumber) {
		this.imeiNumber = imeiNumber;
	}

	public Boolean getIsConfigSent() {
		return isConfigSent;
	}

	public void setIsConfigSent(Boolean isConfigSent) {
		this.isConfigSent = isConfigSent;
	}

	public Boolean getIsConfigDone() {
		return isConfigDone;
	}

	public void setIsConfigDone(Boolean isConfigDone) {
		this.isConfigDone = isConfigDone;
	}

	public Boolean getIsConfigActive() {
		return isConfigActive;
	}

	public void setIsConfigActive(Boolean isConfigActive) {
		this.isConfigActive = isConfigActive;
	}

	public List<Long> getClientIds() {
		return clientIds;
	}

	public void setClientIds(List<Long> clientIds) {
		this.clientIds = clientIds;
	}

	public List<String> getSoftwareVersionList() {
		return softwareVersionList;
	}

	public void setSoftwareVersionList(List<String> softwareVersionList) {
		this.softwareVersionList = softwareVersionList;
	}

	public Boolean getOnlineDevice() {
		return onlineDevice;
	}

	public void setOnlineDevice(Boolean onlineDevice) {
		this.onlineDevice = onlineDevice;
	}

	public LastOnline getLastOnline() {
		return lastOnline;
	}

	public void setLastOnline(LastOnline lastOnline) {
		this.lastOnline = lastOnline;
	}

	public String getSimOperator() {
		return simOperator;
	}

	public void setSimOperator(String simOperator) {
		this.simOperator = simOperator;
	}

	public List<StatusMaster> getStatus() {
		return status;
	}

	public void setStatus(List<StatusMaster> status) {
		this.status = status;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public List<String> getImeiNoList() {
		return imeiNoList;
	}

	public void setImeiNoList(List<String> imeiNoList) {
		this.imeiNoList = imeiNoList;
	}

	public Integer getMinRenwalRequireDays() {
		return minRenwalRequireDays;
	}

	public void setMinRenwalRequireDays(Integer minRenwalRequireDays) {
		this.minRenwalRequireDays = minRenwalRequireDays;
	}

	public Integer getAddSimExpireDays() {
		return addSimExpireDays;
	}

	public void setAddSimExpireDays(Integer addSimExpireDays) {
		this.addSimExpireDays = addSimExpireDays;
	}

	public Integer getAddAppExpireDays() {
		return addAppExpireDays;
	}

	public void setAddAppExpireDays(Integer addAppExpireDays) {
		this.addAppExpireDays = addAppExpireDays;
	}

	public Long getSimActivationDate() {
		return simActivationDate;
	}

	public void setSimActivationDate(Long simActivationDate) {
		this.simActivationDate = simActivationDate;
	}

	public Long getAppActivationDate() {
		return appActivationDate;
	}

	public void setAppActivationDate(Long appActivationDate) {
		this.appActivationDate = appActivationDate;
	}

	public String getNotifyEmailId() {
		return notifyEmailId;
	}

	public void setNotifyEmailId(String notifyEmailId) {
		this.notifyEmailId = notifyEmailId;
	}

	public Boolean getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(Boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public Long getSimProviderId() {
		return simProviderId;
	}

	public void setSimProviderId(Long simProviderId) {
		this.simProviderId = simProviderId;
	}

	public String getSystemLotId() {
		return systemLotId;
	}

	public void setSystemLotId(String systemLotId) {
		this.systemLotId = systemLotId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getLotId() {
		return lotId;
	}

	public void setLotId(Long lotId) {
		this.lotId = lotId;
	}

	public Boolean getIsRejected() {
		return isRejected;
	}

	public void setIsRejected(Boolean isRejected) {
		this.isRejected = isRejected;
	}

	public Boolean getIsProcessing() {
		return isProcessing;
	}

	public void setIsProcessing(Boolean isProcessing) {
		this.isProcessing = isProcessing;
	}

	public String getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public List<SubscriptionAmountDTO> getSubscriptionAmount() {
		return subscriptionAmount;
	}

	public void setSubscriptionAmount(List<SubscriptionAmountDTO> subscriptionAmount) {
		this.subscriptionAmount = subscriptionAmount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Double getStatePlatformCharges() {
		return statePlatformCharges;
	}

	public void setStatePlatformCharges(Double statePlatformCharges) {
		this.statePlatformCharges = statePlatformCharges;
	}

	public Integer getTotalDevice() {
		return totalDevice;
	}

	public void setTotalDevice(Integer totalDevice) {
		this.totalDevice = totalDevice;
	}

	public String getDebitNoteImage() {
		return debitNoteImage;
	}

	public void setDebitNoteImage(String debitNoteImage) {
		this.debitNoteImage = debitNoteImage;
	}

	public String getDebitNoteNo() {
		return debitNoteNo;
	}

	public void setDebitNoteNo(String debitNoteNo) {
		this.debitNoteNo = debitNoteNo;
	}

	public String getEwayBillImage() {
		return ewayBillImage;
	}

	public void setEwayBillImage(String ewayBillImage) {
		this.ewayBillImage = ewayBillImage;
	}

	public String getEwayBillNo() {
		return ewayBillNo;
	}

	public void setEwayBillNo(String ewayBillNo) {
		this.ewayBillNo = ewayBillNo;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getReturnDeviceMasterId() {
		return returnDeviceMasterId;
	}

	public void setReturnDeviceMasterId(Long returnDeviceMasterId) {
		this.returnDeviceMasterId = returnDeviceMasterId;
	}

	public Long getRepairDeviceMasterId() {
		return repairDeviceMasterId;
	}

	public void setRepairDeviceMasterId(Long repairDeviceMasterId) {
		this.repairDeviceMasterId = repairDeviceMasterId;
	}

	public Long getRepairDeviceId() {
		return repairDeviceId;
	}

	public void setRepairDeviceId(Long repairDeviceId) {
		this.repairDeviceId = repairDeviceId;
	}

	public String getOpenBoxImageBeforeTesting() {
		return openBoxImageBeforeTesting;
	}

	public void setOpenBoxImageBeforeTesting(String openBoxImageBeforeTesting) {
		this.openBoxImageBeforeTesting = openBoxImageBeforeTesting;
	}

	public String getCloseBoxImageBeforeTesting() {
		return closeBoxImageBeforeTesting;
	}

	public void setCloseBoxImageBeforeTesting(String closeBoxImageBeforeTesting) {
		this.closeBoxImageBeforeTesting = closeBoxImageBeforeTesting;
	}

	public String getOpenBoxImageAfterTesting() {
		return openBoxImageAfterTesting;
	}

	public void setOpenBoxImageAfterTesting(String openBoxImageAfterTesting) {
		this.openBoxImageAfterTesting = openBoxImageAfterTesting;
	}

	public String getCloseBoxImageAfterTesting() {
		return closeBoxImageAfterTesting;
	}

	public void setCloseBoxImageAfterTesting(String closeBoxImageAfterTesting) {
		this.closeBoxImageAfterTesting = closeBoxImageAfterTesting;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public List<Long> getChargesId() {
		return chargesId;
	}

	public void setChargesId(List<Long> chargesId) {
		this.chargesId = chargesId;
	}

	public Long getReplacedByDeviceId() {
		return replacedByDeviceId;
	}

	public void setReplacedByDeviceId(Long replacedByDeviceId) {
		this.replacedByDeviceId = replacedByDeviceId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Long getBoxId() {
		return boxId;
	}

	public void setBoxId(Long boxId) {
		this.boxId = boxId;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Long getReConfigureBoxId() {
		return reConfigureBoxId;
	}

	public void setReConfigureBoxId(Long reConfigureBoxId) {
		this.reConfigureBoxId = reConfigureBoxId;
	}

	public String getBoxCode() {
		return boxCode;
	}

	public void setBoxCode(String boxCode) {
		this.boxCode = boxCode;
	}

	public Boolean getIsReConfigured() {
		return isReConfigured;
	}

	public void setIsReConfigured(Boolean isReConfigured) {
		this.isReConfigured = isReConfigured;
	}

	public String getMfgLotId() {
		return mfgLotId;
	}

	public void setMfgLotId(String mfgLotId) {
		this.mfgLotId = mfgLotId;
	}

	public List<String> getImeiOrBoxNoList() {
		return imeiOrBoxNoList;
	}

	public void setImeiOrBoxNoList(List<String> imeiOrBoxNoList) {
		this.imeiOrBoxNoList = imeiOrBoxNoList;
	}

	public Long getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(Long requestedBy) {
		this.requestedBy = requestedBy;
	}

	public Long getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}

	public Boolean getIsBoxSearch() {
		return isBoxSearch;
	}

	public void setIsBoxSearch(Boolean isBoxSearch) {
		this.isBoxSearch = isBoxSearch;
	}

	public String getReConfigBoxCode() {
		return reConfigBoxCode;
	}

	public void setReConfigBoxCode(String reConfigBoxCode) {
		this.reConfigBoxCode = reConfigBoxCode;
	}

}
