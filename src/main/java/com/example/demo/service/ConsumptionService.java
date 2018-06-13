package com.example.demo.service;

import com.example.demo.model.Consumption;
import com.example.demo.model.Unsettled;

import java.util.List;

public interface ConsumptionService {
    Consumption getConsumption(int orderID);

    List<Consumption> getConsumptionsByIns(int institutionID);

    List<Consumption> getConsumptionsByTrainee(String email);

    List<Unsettled> getUnsettledConsumption();

    void settleByInstitution(double profit, double proportion, int institutionID);


    void settleAllInstitution(List<Double> profit, List<Double> proportion, List<Integer> institutionID);
}
