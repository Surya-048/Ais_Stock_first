package com.watsoo.device.management.dto;

import com.watsoo.device.management.model.RenewalDevice;

import java.util.Date;
import java.util.List;

public class DeviceRenewalResponseDTO {

    private String requestCode;
    private Date requestDate;
    private String createdBy;
    private int totalDevices;
    private  Long requestId;
    private List<RenewalDevice> devices;

    public Long getRequestId ( ) {
        return requestId;
    }

    public void setRequestId ( Long requestId ) {
        this.requestId = requestId;
    }

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public List<RenewalDevice> getDevices() {
        return devices;
    }

    public void setDevices(List<RenewalDevice> devices) {
        this.devices = devices;
    }

    public int getTotalDevices() {
        return totalDevices;
    }

    public void setTotalDevices(int totalDevices) {
        this.totalDevices = totalDevices;
    }
}
