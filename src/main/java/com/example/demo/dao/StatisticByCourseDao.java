package com.example.demo.dao;

import com.example.demo.model.StatisticByCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatisticByCourseDao extends JpaRepository<StatisticByCourse, Integer> {
    List<StatisticByCourse> findByInstitutionID(int id);
}
