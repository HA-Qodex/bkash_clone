package com.sigma.bkash;

public class RegHelper {

    private String phoneNo, PIN;

    public RegHelper() {
    }

    public RegHelper(String phoneNo, String PIN) {
        this.phoneNo = phoneNo;
        this.PIN = PIN;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPIN() {
        return PIN;
    }

    public void setPIN(String PIN) {
        this.PIN = PIN;
    }
}
