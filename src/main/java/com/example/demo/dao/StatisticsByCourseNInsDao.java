package com.example.demo.dao;

import com.example.demo.model.StatisticsByCourseNIns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StatisticsByCourseNInsDao extends JpaRepository<StatisticsByCourseNIns,String>{

    @Query("select s from StatisticsByCourseNIns s ")
    List<StatisticsByCourseNIns> getList();
}
