package com.example.demo.dao;

import com.example.demo.model.Consumption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ConsumptionDao extends JpaRepository<Consumption,Integer>{

    Consumption save(Consumption consumption);
    Consumption findByOrderIDAndReason(int orderID,String reason);
    List<Consumption> findByOrderID(int orderID);

    List<Consumption> findByTraineeID(String traineeID);

    List<Consumption> findByTimeBetween(Date date1,Date date2);
    List<Consumption> findByTime(Date date);
    List<Consumption> findByTimeBetweenAndTraineeID(Date date1,Date date2,String traineeID);
    List<Consumption> findByTimeBetweenAndOrderID(Date date1,Date date2,int orderID);
}
