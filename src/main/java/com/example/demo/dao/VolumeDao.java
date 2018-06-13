package com.example.demo.dao;

import com.example.demo.model.Profit;
import com.example.demo.model.Volume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface VolumeDao extends JpaRepository<Volume, Date>{

    Volume save(Volume volume);
    List<Volume> findByDateBetween(Date date1,Date date2);
}
