package org.xxpay.task.reconciliation.nc.entity;


public class HospitalBean {

    private String hospitalId;
    private String hospitalName;

    public HospitalBean() {

    }

    public HospitalBean(String hospitalId, String hospitalName) {
        this.hospitalId= hospitalId;
        this.hospitalName = hospitalName;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }
}
