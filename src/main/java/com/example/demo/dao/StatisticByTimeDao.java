package com.example.demo.dao;

import com.example.demo.model.StatisticByTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticByTimeDao extends JpaRepository<StatisticByTime,String>{
    StatisticByTime save(StatisticByTime statisticByTime);
}
