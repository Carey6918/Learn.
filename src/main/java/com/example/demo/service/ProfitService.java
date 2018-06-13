package com.example.demo.service;

import com.example.demo.model.Profit;
import com.example.demo.model.Volume;

import java.util.List;

public interface ProfitService {
    void updateProfit();

    List<Profit> get7DaysProfit();

    void updateVolume();

    List<Volume> get7DaysVolume();
}
