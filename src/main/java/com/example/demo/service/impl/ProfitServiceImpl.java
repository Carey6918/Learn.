package com.example.demo.service.impl;

import com.example.demo.dao.ConsumptionDao;
import com.example.demo.dao.OrderDao;
import com.example.demo.dao.ProfitDao;
import com.example.demo.dao.VolumeDao;
import com.example.demo.model.Consumption;
import com.example.demo.model.Order;
import com.example.demo.model.Profit;
import com.example.demo.model.Volume;
import com.example.demo.service.ProfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static com.example.demo.util.GetDate.getDateAfter;

@Service
public class ProfitServiceImpl implements ProfitService{
    @Autowired
    ProfitDao profitDao;
    @Autowired
    ConsumptionDao consumptionDao;
    @Autowired
    VolumeDao volumeDao;
    @Autowired
    OrderDao orderDao;

    @Override
    public void updateProfit(){
        List<Date> dateList = getDateAfter(1);
        List<Consumption> consumptionList =consumptionDao.findByTimeBetween(dateList.get(0),dateList.get(1));
        double price = 0;
        for(Consumption consumption:consumptionList){
            price+=consumption.getPrice();
        }
        Profit profit = new Profit(dateList.get(0),price);
        profitDao.save(profit);
        System.out.println("成功更新今日利润！");
    }

    @Override
    public List<Profit> get7DaysProfit(){
        List<Date> dateList = getDateAfter(-7);
        return profitDao.findByDateBetween(dateList.get(1),dateList.get(0));
    }

    @Override
    public void updateVolume(){
        List<Date> dateList = getDateAfter(1);
        List<Order> orderList =orderDao.findByCreateTimeBetween(dateList.get(0),dateList.get(1));
        int size = orderList.size();
        Volume volume = new Volume();
        volume.setDate(dateList.get(0));
        volume.setVolume(size);
        volumeDao.save(volume);
        System.out.println("成功更新今日成交量！");
    }

    @Override
    public List<Volume> get7DaysVolume(){
        List<Date> dateList = getDateAfter(-7);
        return volumeDao.findByDateBetween(dateList.get(1),dateList.get(0));
    }
    

}
