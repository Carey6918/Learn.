package com.example.demo.service;

import com.example.demo.model.InstitutionStatistics;
import com.example.demo.model.TraineeStatistics;

import java.util.List;

public interface StatisticsService {
    void updateTraineeStatistics();

    void updateInstitutionStatistics();

    List<TraineeStatistics> getTraineeStatistics();

    List<InstitutionStatistics> getInstitutionStatistics();
}
