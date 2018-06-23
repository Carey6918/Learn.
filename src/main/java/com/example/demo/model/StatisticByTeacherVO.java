package com.example.demo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StatisticByTeacherVO {
    public List<String> teacher;
    public List<String> time;
    public List<List<Double>> percent;

    public StatisticByTeacherVO(){
        teacher=new ArrayList<>();
        time=new ArrayList<>();
        percent=new ArrayList<>();
    }
}
