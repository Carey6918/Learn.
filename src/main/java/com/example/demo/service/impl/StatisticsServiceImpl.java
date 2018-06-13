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

    @Override
    public void updateTraineeStatistics() {
        List<Trainee> traineeList = traineeDao.getList();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0); //关键的一步，很多时候忽略了毫秒置0，而无法查询出想要的结果
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
            double volumeRise =1;
            if (traineeStatistics.getWeekVolume()!=0) {
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
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0); //关键的一步，很多时候忽略了毫秒置0，而无法查询出想要的结果
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
    public List<TraineeStatistics> getTraineeStatistics(){
        return traineeStatisticsDao.getList();
    }

    @Override
    public List<InstitutionStatistics> getInstitutionStatistics(){
        return institutionStatisticsDao.getList();
    }

    /**
     * 判断日期是否在区间内
     *
     * @param nowTime
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    public static Date getThisWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }
}
