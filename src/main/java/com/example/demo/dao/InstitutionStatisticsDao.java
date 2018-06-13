package com.example.demo.dao;

import com.example.demo.model.InstitutionStatistics;
import com.example.demo.model.TraineeStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InstitutionStatisticsDao extends JpaRepository<InstitutionStatistics,Integer>{

    InstitutionStatistics save(InstitutionStatistics institutionStatistics);
    InstitutionStatistics findByInstitutionID(int institutionID);


    @Query("select t from InstitutionStatistics t ")
    List<InstitutionStatistics> getList();
}
