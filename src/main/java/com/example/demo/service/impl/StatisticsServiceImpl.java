package com.example.demo.service.impl;

import com.example.demo.dao.*;
import com.example.demo.model.*;
import com.example.demo.service.ConsumptionService;
import com.example.demo.service.OrderService;
import com.example.demo.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static com.example.demo.util.GetDate.*;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    TraineeStatisticsDao traineeStatisticsDao;
    @Autowired
    InstitutionStatisticsDao institutionStatisticsDao;
    @Autowired
    ConsumptionDao consumptionDao;
    @Autowired
    TraineeDao traineeDao;
    @Autowired
    InstitutionDao institutionDao;
    @Autowired
    OrderDao orderDao;
    @Autowired
    ConsumptionService consumptionService;
    @Autowired
    OrderService orderService;
    @Autowired
    StatisticByTimeDao statisticByTimeDao;

    @Override
    public void updateTraineeStatistics() {
        List<Trainee> traineeList = traineeDao.getList();
        Calendar cal = Calendar.getInstance();
        calToDate(cal);
        Date date = cal.getTime();
        cal.add(Calendar.DATE, -7);
//        Date date1 = cal.getTime();
        Date date1 = getThisWeekMonday(date);
        DecimalFormat df = new DecimalFormat("#.00");
        for (Trainee trainee : traineeList) {

            List<Consumption> consumptionList = consumptionDao.findByTimeBetweenAndTraineeID(date1, date, trainee.getEmail());
            List<Order> orderList = orderDao.findByTraineeID(trainee.getEmail());
            double weekConsumption = 0;
            int weekVolume = 0;
            for (Consumption consumption : consumptionList) {
                weekConsumption += consumption.getPrice();
            }
            for (Order order : orderList) {
                if (!order.getState().equals("已退订")) {
                    weekVolume += 1;
                }
            }
            TraineeStatistics traineeStatistics = traineeStatisticsDao.findByTraineeID(trainee.getEmail());
            double consumptionRise = 1;
            if (traineeStatistics.getWeekConsumption() != 0) {
                consumptionRise = (weekConsumption - traineeStatistics.getWeekConsumption()) / traineeStatistics.getWeekConsumption() * 100;
            }
            double totalConsumption = traineeStatistics.getTotalConsumption() + weekConsumption;
            double volumeRise = 1;
            if (traineeStatistics.getWeekVolume() != 0) {
                volumeRise = (weekVolume - traineeStatistics.getWeekVolume()) / traineeStatistics.getWeekVolume();
            }
            int totalVolume = traineeStatistics.getTotalVolume() + weekVolume;
            traineeStatistics.setWeekConsumption(weekConsumption);
            traineeStatistics.setConsumptionRise(Double.parseDouble(df.format(consumptionRise)));
            traineeStatistics.setTotalConsumption(totalConsumption);
            traineeStatistics.setWeekVolume(weekVolume);
            traineeStatistics.setVolumeRise(Double.parseDouble(df.format(volumeRise)));
            traineeStatistics.setTotalVolume(totalVolume);
            traineeStatisticsDao.save(traineeStatistics);

        }
        System.out.println("成功更新学员统计！");
    }


    @Override
    public void updateInstitutionStatistics() {
        List<Institution> institutionList = institutionDao.getList();
        Calendar cal = Calendar.getInstance();
        calToDate(cal);
        Date date = cal.getTime();
        cal.add(Calendar.DATE, -7);
//        Date date1 = cal.getTime();
        Date date1 = getThisWeekMonday(date);
        DecimalFormat df = new DecimalFormat("#.00");
        for (Institution institution : institutionList) {
            List<Consumption> tempList = consumptionService.getConsumptionsByIns(institution.getInstitutionID());
            List<Consumption> consumptionList = new LinkedList<>();
            for (Consumption consumption : tempList) {
                if (isEffectiveDate(consumption.getTime(), date1, date)) {
                    consumptionList.add(consumption);
                }
            }
            List<Order> orderList = orderService.getReservedOrder(institution.getInstitutionID());
            double weekProfit = 0;
            int weekVolume = orderList.size();
            for (Consumption consumption : consumptionList) {
                weekProfit += consumption.getPrice();
            }
            InstitutionStatistics institutionStatistics = institutionStatisticsDao.findByInstitutionID(institution.getInstitutionID());
            double profitRise = 100;
            if (institutionStatistics.getWeekProfit() != 0) {
                profitRise = (weekProfit - institutionStatistics.getWeekProfit()) / institutionStatistics.getWeekProfit() * 100;
            }
            double totalProfit = institutionStatistics.getTotalProfit() + weekProfit;
            double volumeRise = 100;
            if (institutionStatistics.getWeekVolume() != 0) {
                volumeRise = (weekVolume - institutionStatistics.getWeekVolume()) / institutionStatistics.getWeekVolume() * 100;
            }
            int totalVolume = institutionStatistics.getTotalVolume() + weekVolume;
            institutionStatistics.setWeekProfit(weekProfit);
            institutionStatistics.setProfitRise(Double.parseDouble(df.format(profitRise)));
            institutionStatistics.setTotalProfit(totalProfit);
            institutionStatistics.setWeekVolume(weekVolume);
            institutionStatistics.setVolumeRise(Double.parseDouble(df.format(volumeRise)));
            institutionStatistics.setTotalVolume(totalVolume);
            institutionStatisticsDao.save(institutionStatistics);
        }
        System.out.println("成功更新机构统计！");

    }

    @Override
    public List<TraineeStatistics> getTraineeStatistics() {
        return traineeStatisticsDao.getList();
    }

    @Override
    public List<InstitutionStatistics> getInstitutionStatistics() {
        return institutionStatisticsDao.getList();
    }


    /**
     * 更新每日数据
     */
    @Override
    public void updateStatisticsByTime() {
        List<Date> dateList = getDateAfter(1);
        List<Trainee> traineeList = traineeDao.getList();

        //获取学员当日的消费额数据
        for (Trainee trainee : traineeList) {
            List<Consumption> consumptionList = consumptionDao.findByTraineeID(trainee.getEmail());
            double profit = 0;
            for (Consumption consumption : consumptionList) {
                if (isEffectiveDate(consumption.getTime(), dateList.get(0), dateList.get(1))) {
                    profit += consumption.getPrice();
                }
            }
            List<Order> orderList = orderDao.findByTraineeID(trainee.getEmail());
            int volumn = 0;
            for (Order order : orderList) {
                if (!order.getState().equals("已退订") && isEffectiveDate(order.getCreateTime(), dateList.get(0), dateList.get(1))) {
                    volumn++;
                }
            }
            StatisticByTime statisticByTime = new StatisticByTime(dateList.get(0), 3, trainee.getEmail(), volumn, profit);
            statisticByTimeDao.save(statisticByTime);
        }

        List<Institution> institutionList = institutionDao.getList();
        for (Institution institution : institutionList) {
            List<Consumption> consumptionList = consumptionService.getConsumptionsByIns(institution.getInstitutionID());
            double profit = 0;
            for (Consumption consumption : consumptionList) {
                if (isEffectiveDate(consumption.getTime(), dateList.get(0), dateList.get(1))) {
                    profit += consumption.getPrice();
                }
            }

            List<Order> orderList = orderService.getReservedOrder(institution.getInstitutionID());
            int volumn = 0;
            for (Order order : orderList) {
                if (isEffectiveDate(order.getCreateTime(), dateList.get(0), dateList.get(1))) {
                    volumn++;
                }
            }
            StatisticByTime statisticByTime = new StatisticByTime(dateList.get(0), 2, String.valueOf(institution.getInstitutionID()), volumn, profit);
            statisticByTimeDao.save(statisticByTime);
        }

        List<Consumption> consumptionList = consumptionDao.findByTimeBetween(dateList.get(0), dateList.get(1));
        double profit =0;
        for (Consumption consumption : consumptionList) {
                profit += consumption.getPrice();
        }
        List<Order> orderList = orderDao.findByCreateTimeBetween(dateList.get(0), dateList.get(1));
        int volumn =0;
        for (Order order : orderList) {
            if (!order.getState().equals("已退订") ) {
                volumn++;
            }
        }
        StatisticByTime statisticByTime = new StatisticByTime(dateList.get(0), 1, "0", volumn, profit);
        statisticByTimeDao.save(statisticByTime);

    }
}
