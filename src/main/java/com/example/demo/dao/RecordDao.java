package com.example.demo.dao;

import com.example.demo.model.ClassRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface RecordDao extends JpaRepository<ClassRecord,Integer>{
    List<ClassRecord> findByCourseID(int courseID);
    List<ClassRecord> findByTraineeID(String traineeID);
    ClassRecord save(ClassRecord classRecord);
    @Transactional
    void deleteByCourseIDAndTraineeID(int courseID,String traineeID);
    List<ClassRecord> findByTraineeIDAndCourseID(String traineeID,int courseID);
    ClassRecord findById(int id);
    List<ClassRecord> findByOrderID(int OrderID);
    @Transactional
    void deleteByOrderID(int orderID);
}
