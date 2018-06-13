package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "traineeStatistics")
public class TraineeStatistics {
    private String traineeID;
    private double weekConsumption;
    private double consumptionRise;
    private double totalConsumption;
    private int weekVolume;
    private double volumeRise;
    private int totalVolume;


    public TraineeStatistics() {
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

    @Id
    public String getTraineeID() {
        return traineeID;
    }

    public void setTraineeID(String traineeID) {
        this.traineeID = traineeID;
    }

    @Column(name = "weekConsumption")
    public double getWeekConsumption() {
        return weekConsumption;
    }

    public void setWeekConsumption(double weekConsumption) {
        this.weekConsumption = weekConsumption;
    }
    @Column(name = "consumptionRise")
    public double getConsumptionRise() {
        return consumptionRise;
    }

    public void setConsumptionRise(double consumptionRise) {
        this.consumptionRise = consumptionRise;
    }
    @Column(name = "totalConsumption")
    public double getTotalConsumption() {
        return totalConsumption;
    }

    public void setTotalConsumption(double totalConsumption) {
        this.totalConsumption = totalConsumption;
    }
}
