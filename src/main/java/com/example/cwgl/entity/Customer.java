package com.example.cwgl.entity;

public class Customer {
    private int cId;
    private String cNo;
    private String cName;
    private String password;
    private String phone;
    private String company;
    private String address;
    private String email;
    private String createTime;

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getcNo() {
        return cNo;
    }

    public void setcNo(String cNo) {
        this.cNo = cNo;
    }

    public Customer(String cName, String phone, String company, String address, String email, String createTime) {
        this.cName = cName;
        this.phone = phone;
        this.company = company;
        this.address = address;
        this.email = email;
        this.createTime = createTime;
    }

    public Customer(int cId, String cName, String phone, String company, String address, String email) {
        this.cId = cId;
        this.cName = cName;
        this.phone = phone;
        this.company = company;
        this.address = address;
        this.email = email;
        this.createTime = createTime;
    }

    public Customer(){

    }
}
