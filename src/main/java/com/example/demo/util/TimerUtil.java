package com.example.demo.util;

import com.example.demo.service.OrderService;
import com.example.demo.service.ProfitService;
import com.example.demo.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 定时器
 */
@Component
public class TimerUtil {
    @Autowired
    OrderService orderService;
    @Autowired
    ProfitService profitService;
    @Autowired
    StatisticsService statisticsService;
    private static TimerUtil timerUtil;

    @PostConstruct
    public void init() {
        timerUtil = this;
    }

    @Scheduled(fixedDelay = 1000 * 60 * 60 * 24)
    public void updateState() {
        orderService.updateState();
        profitService.updateProfit();
        profitService.updateVolume();
//        statisticsService.updateStatisticsByTime();
    }

    @Scheduled(cron = "0 0 0 ? * MON")
    public void updateStatistics() {
        statisticsService.updateInstitutionStatistics();
        statisticsService.updateTraineeStatistics();
    }

//    @Scheduled

}
