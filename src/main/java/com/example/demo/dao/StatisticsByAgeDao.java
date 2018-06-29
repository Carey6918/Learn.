package com.example.demo.dao;

import com.example.demo.model.StatisticsByAge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StatisticsByAgeDao extends JpaRepository<StatisticsByAge, String> {
    @Query("select s from StatisticsByAge s ")
    List<StatisticsByAge> getList();
}
