package com.example.cwgl.entity;

public class Report {

    private int id;

    private String reportName;

    private String reportPurpose;

    private String reportPath;

    private String dateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportPurpose() {
        return reportPurpose;
    }

    public void setReportPurpose(String reportPurpose) {
        this.reportPurpose = reportPurpose;
    }

    public String getReportPath() {
        return reportPath;
    }

    public void setReportPath(String reportPath) {
        this.reportPath = reportPath;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
