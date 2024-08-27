package com.watsoo.device.management.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "renewal_device")
public class RenewalDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_id")
    private Long deviceId;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "request_id", referencedColumnName = "id")
    private DeviceRenewalRequest deviceRenewalRequest;

    @Column(name = "imei_no")
    private String imeiNo;

    @Column(name = "iccid_no")
    private String iccidNo;

    @Column(name = "old_expiry_date")
    private Date oldExpiryDate;

    @Column(name = "new_expiry_date")
    private Date newExpiryDate;


    public RenewalDevice() {
    }

    @Override
    public String toString() {
        return "RenewalDevice{" +
                "id=" + id +
                ", deviceId=" + deviceId +
                ", imeiNo='" + imeiNo + '\'' +
                ", iccidNo='" + iccidNo + '\'' +
                ", oldExpiryDate=" + oldExpiryDate +
                ", newExpiryDate=" + newExpiryDate +
                '}';
    }

    public RenewalDevice(Long deviceId, DeviceRenewalRequest deviceRenewalRequest, String imeiNo, String iccidNo, Date oldExpiryDate, Date newExpiryDate) {
        this.deviceId = deviceId;
        this.deviceRenewalRequest = deviceRenewalRequest;
        this.imeiNo = imeiNo;
        this.iccidNo = iccidNo;
        this.oldExpiryDate = oldExpiryDate;
        this.newExpiryDate = newExpiryDate;
    }

    public DeviceRenewalRequest getDeviceRenewalRequest() {
        return deviceRenewalRequest;
    }

    public void setDeviceRenewalRequest(DeviceRenewalRequest deviceRenewalRequest) {
        this.deviceRenewalRequest = deviceRenewalRequest;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
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

    public Date getOldExpiryDate() {
        return oldExpiryDate;
    }

    public void setOldExpiryDate(Date oldExpiryDate) {
        this.oldExpiryDate = oldExpiryDate;
    }

    public Date getNewExpiryDate() {
        return newExpiryDate;
    }

    public void setNewExpiryDate(Date newExpiryDate) {
        this.newExpiryDate = newExpiryDate;
    }
}
