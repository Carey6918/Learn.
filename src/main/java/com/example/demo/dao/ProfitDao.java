package com.example.demo.dao;

import com.example.demo.model.Profit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ProfitDao extends JpaRepository<Profit, Date>{

    Profit save(Profit profit);
    List<Profit> findByDateBetween(Date date1,Date date2);
}
