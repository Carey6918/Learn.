package com.example.demo.dao;

import com.example.demo.model.StatisticByTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatisticByTimeDao extends JpaRepository<StatisticByTime,String>{
    StatisticByTime save(StatisticByTime statisticByTime);
    List<StatisticByTime> findByKeyId(String id);
    List<StatisticByTime> findByType(int type);
}
