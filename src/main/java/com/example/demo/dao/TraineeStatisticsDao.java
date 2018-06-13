package com.example.demo.dao;

import com.example.demo.model.TraineeStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TraineeStatisticsDao extends JpaRepository<TraineeStatistics, String> {

    TraineeStatistics save(TraineeStatistics traineeStatistics);

    TraineeStatistics findByTraineeID(String traineeID);

    @Query("select t from TraineeStatistics t ")
    List<TraineeStatistics> getList();
}
