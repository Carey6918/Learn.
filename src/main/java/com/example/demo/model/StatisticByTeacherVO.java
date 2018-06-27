package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class StatisticByTeacherVO {
    public List<String> teacher;
    public List<String> time;
    public List<List<Double>> percent;
    public List<List<Double>> goodPercent;
    public List<List<Double>> failPercent;


    public StatisticByTeacherVO() {
        teacher = new ArrayList<>();
        time = new ArrayList<>();
        percent = new ArrayList<>();
        goodPercent = new ArrayList<>();
        failPercent = new ArrayList<>();
    }
}
