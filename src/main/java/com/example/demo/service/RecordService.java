package com.example.demo.service;

import com.example.demo.model.ClassRecord;
import com.example.demo.model.Order;

import java.util.List;

public interface RecordService {

    ClassRecord getRecord(Order order);

    List<ClassRecord> getRecordListByCourseID(int courseID);

    void registerOne(int registerID, int time, int grade);

    void registerAll(List id, List time, List grade);

    void onsitePayWithoutID(int courseID, String traineeName);
}
