package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class StatisticByTimeVO {
    public List<String> name;
    public List<String> time;
    public List<List<Integer>> profit;
    public List<List<Integer>> volume;

    public StatisticByTimeVO() {
        name = new ArrayList<>();
        time = new ArrayList<>();
        profit = new ArrayList<>();
        volume = new ArrayList<>();
    }
}
