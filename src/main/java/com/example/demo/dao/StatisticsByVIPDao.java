package com.example.demo.dao;

import com.example.demo.model.StatisticsByVIP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StatisticsByVIPDao extends JpaRepository<StatisticsByVIP, String> {
    @Query("select s from StatisticsByVIP s ")
    List<StatisticsByVIP> getList();
}
