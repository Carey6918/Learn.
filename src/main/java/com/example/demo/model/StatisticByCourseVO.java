package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class StatisticByCourseVO {
    public List<String> type;
    public List<String> time;
    public List<List<Integer>> profit;
    public List<List<Integer>> volume;

    public StatisticByCourseVO() {
        type = new ArrayList<>();
        time = new ArrayList<>();
        profit = new ArrayList<>();
        volume = new ArrayList<>();
    }
}
