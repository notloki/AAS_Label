package com.notloki.aas_label;

public class Data {

    private String qty;
    private String ft;
    private String in;
    private String custName;
    private String jobName;
    private String poNumber;
    private String location;

    public void setQty(String qty) {
        this.qty = qty;
    }

    public void setFt(String ft) {
        this.ft = ft;
    }

    public void setIn(String in) {
        this.in = in;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCustName() {
        return custName;
    }

    public String getJobName() {
        return jobName;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public String getLocation() {
        return location;
    }

    public String getQty() {
        return qty;
    }

    public String getFt() {
        return ft;
    }

    public String getIn() {
        return in;
    }

    @Override
    public String toString() {
        if(qty.equals("              ")) {
            return "              ";
        }

        return qty + "EA " + ft + "' " + in + "\"";
    }
}
