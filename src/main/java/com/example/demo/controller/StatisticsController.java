package com.example.demo.controller;

import com.example.demo.model.Consumption;
import com.example.demo.model.Order;
import com.example.demo.model.StatisticByTeacherVO;
import com.example.demo.model.StatisticByTime;
import com.example.demo.service.ConsumptionService;
import com.example.demo.service.OrderService;
import com.example.demo.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

@Controller
public class StatisticsController {
    @Autowired
    ConsumptionService consumptionService;

    @Autowired
    OrderService orderService;
    @Autowired
    StatisticsService statisticsService;

    @RequestMapping(value = "/statistics")
    public String statisticsInfo(HttpServletRequest request, Model model){
        int institutionID = (int) request.getSession().getAttribute("loginins");
        List<Consumption> consumptionList = consumptionService.getConsumptionsByIns(institutionID);
        model.addAttribute("consumptionList",consumptionList);
        List<Order> canceledOrderList = orderService.getCanceledOrder(institutionID);
        List<Order> reservedOrderList = orderService.getReservedOrder(institutionID);
        model.addAttribute("canceledOrderList",canceledOrderList);
        model.addAttribute("reservedOrderList",reservedOrderList);

        List<StatisticByTime> statisticByTimeList = statisticsService.getTimeStatistics(String.valueOf(institutionID));
        List<String> dateList = new LinkedList<>();
        List<Double> profitList = new LinkedList<>();
        List<Integer> volumeList = new LinkedList<>();
        for(int i = 0 ; i< statisticByTimeList.size();i++){
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String date = df.format(statisticByTimeList.get(i).getKey().getDate());
            dateList.add(date);
            profitList.add(statisticByTimeList.get(i).getProfit());
            volumeList.add(statisticByTimeList.get(i).getVolume());
        }
        model.addAttribute("profitList",profitList);
        model.addAttribute("dateList",dateList);
        model.addAttribute("volumeList",volumeList);
        List<Integer> onsitePercent = statisticsService.getOnsitePercent(institutionID);
        model.addAttribute("onsitePercent",onsitePercent);
        StatisticByTeacherVO teacherCancel = statisticsService.getTeacherCancelPercent(institutionID);
        model.addAttribute("teacherCancel",teacherCancel);
        return "ins/statistics";
    }
}
