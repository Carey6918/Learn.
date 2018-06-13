package com.example.demo.controller;

import com.example.demo.model.Consumption;
import com.example.demo.model.Order;
import com.example.demo.service.ConsumptionService;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class StatisticsController {
    @Autowired
    ConsumptionService consumptionService;

    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/statistics")
    public String statisticsInfo(HttpServletRequest request, Model model){
        int institutionID = (int) request.getSession().getAttribute("loginins");
        List<Consumption> consumptionList = consumptionService.getConsumptionsByIns(institutionID);
        model.addAttribute("consumptionList",consumptionList);
        List<Order> canceledOrderList = orderService.getCanceledOrder(institutionID);
        List<Order> reservedOrderList = orderService.getReservedOrder(institutionID);
        model.addAttribute("canceledOrderList",canceledOrderList);
        model.addAttribute("reservedOrderList",reservedOrderList);
        return "ins/statistics";
    }
}
