package com.mericaltikardes.bigdatamultisearch.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Information {
    @Override
    public String toString() {
        return "Information{" +
                "product=" + product +
                ", issue=" + issue +
                ", company=" + company +
                ", state=" + state +
                ", zipCode=" + zipCode +
                ", complaintId=" + complaintId +
                '}';
    }

    private SimpleStringProperty product;
    private SimpleStringProperty issue;
    private SimpleStringProperty company;
    private SimpleStringProperty state;
    private SimpleStringProperty zipCode;
    private SimpleStringProperty complaintId;

    public Information(String product, String issue, String company, String state, String zipCode, String complaintId) {
        this.product = new SimpleStringProperty(product) ;
        this.issue = new SimpleStringProperty(issue);
        this.company = new SimpleStringProperty(company);
        this.state = new SimpleStringProperty(state);
        this.zipCode = new SimpleStringProperty(zipCode);
        this.complaintId = new SimpleStringProperty(complaintId);
    }

    public String getProduct() {
        return product.get();
    }

    public void setProduct(String product) {
        this.product = new SimpleStringProperty(product);
    }

    public String getIssue() {
        return issue.get();
    }

    public void setIssue(String issue) {
        this.issue = new SimpleStringProperty(issue);
    }

    public String getCompany() {
        return company.get();
    }

    public void setCompany(String company) {
        this.company = new SimpleStringProperty(company);
    }

    public String getState() {
        return state.get();
    }

    public void setState(String state) {
        this.state = new SimpleStringProperty(state);
    }

    public String getZipCode() {
        return zipCode.get();
    }

    public void setZipCode(String zipCode) {
        this.zipCode = new SimpleStringProperty(zipCode);
    }

    public String getComplaintId() {
        return complaintId.get();
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = new SimpleStringProperty(complaintId);
    }
}
