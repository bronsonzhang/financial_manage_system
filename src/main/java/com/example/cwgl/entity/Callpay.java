package com.example.cwgl.entity;

public class Callpay {
    private int id;
    private int customerId;
    private String callNo;
    private double cost;
    private int status;
    private String payTime;
    private String expireTime;
    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCallNo() {
        return callNo;
    }

    public void setCallNo(String callNo) {
        this.callNo = callNo;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Callpay(int customerId, String callNo, double cost, int status, String payTime, String expireTime, String createTime) {
        this.customerId = customerId;
        this.callNo = callNo;
        this.cost = cost;
        this.status = status;
        this.payTime = payTime;
        this.expireTime = expireTime;
        this.createTime = createTime;
    }

    public Callpay(int id, int customerId, String callNo, double cost, int status, String expireTime, String createTime) {
        this.id = id;
        this.customerId = customerId;
        this.callNo = callNo;
        this.cost = cost;
        this.status = status;
        this.expireTime = expireTime;
        this.createTime = createTime;
    }

    public Callpay(){

    }
}
