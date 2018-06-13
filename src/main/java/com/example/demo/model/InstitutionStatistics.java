package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "institutionStatistics")
public class InstitutionStatistics {
    private int institutionID;
    private double weekProfit;
    private double profitRise;
    private double totalProfit;
    private int weekVolume;
    private double volumeRise;
    private int totalVolume;


    public InstitutionStatistics() {
    }

    @Id
    public int getInstitutionID() {
        return institutionID;
    }

    public void setInstitutionID(int institutionID) {
        this.institutionID = institutionID;
    }

    @Column(name = "weekProfit")
    public double getWeekProfit() {
        return weekProfit;
    }

    public void setWeekProfit(double weekProfit) {
        this.weekProfit = weekProfit;
    }

    @Column(name = "profitRise")
    public double getProfitRise() {
        return profitRise;
    }

    public void setProfitRise(double profitRise) {
        this.profitRise = profitRise;
    }

    @Column(name = "totalProfit")
    public double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(double totalProfit) {
        this.totalProfit = totalProfit;
    }

    @Column(name = "weekVolume")
    public int getWeekVolume() {
        return weekVolume;
    }

    public void setWeekVolume(int weekVolume) {
        this.weekVolume = weekVolume;
    }

    @Column(name = "volumeRise")
    public double getVolumeRise() {
        return volumeRise;
    }

    public void setVolumeRise(double volumeRise) {
        this.volumeRise = volumeRise;
    }

    @Column(name = "totalVolume")
    public int getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(int totalVolume) {
        this.totalVolume = totalVolume;
    }
}
