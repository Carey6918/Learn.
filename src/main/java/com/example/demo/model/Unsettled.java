package com.example.demo.model;

public class Unsettled {

    private int institutionID;
    private double unsettledProfit;

    public Unsettled() {
    }
    public Unsettled(int institutionID,double unsettledProfit){
        this.institutionID =institutionID;
        this.unsettledProfit = unsettledProfit;
    }

    public double getUnsettledProfit() {
        return unsettledProfit;
    }

    public void setUnsettledProfit(double unsettledProfit) {
        this.unsettledProfit = unsettledProfit;
    }

    public int getInstitutionID() {
        return institutionID;
    }

    public void setInstitutionID(int institutionID) {
        this.institutionID = institutionID;
    }
}
