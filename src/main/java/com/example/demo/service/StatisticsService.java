package com.example.demo.service;

import com.example.demo.model.*;

import java.util.List;

public interface StatisticsService {
    void updateTraineeStatistics();

    void updateInstitutionStatistics();

    List<TraineeStatistics> getTraineeStatistics();

    List<InstitutionStatistics> getInstitutionStatistics();

    List<StatisticByTime> getTimeStatistics(String id);

    InsPercentVO getInsPercent(String id);

    CourseGradeVO getCourseGrade(String id);

    List<Integer> getOnsitePercent(int institutionID);

    StatisticByCourseVO getCourseStatistic(int institutionID);

    StatisticByTeacherVO getTeacherCancelPercent(int institutionID);

    void updateStatisticsByTime();
}
