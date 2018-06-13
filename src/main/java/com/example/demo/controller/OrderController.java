package com.example.demo.controller;

import com.example.demo.model.ClassRecord;
import com.example.demo.model.Consumption;
import com.example.demo.model.Course;
import com.example.demo.model.Order;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    CourseService courseService;
    @Autowired
    AccountService accountService;
    @Autowired
    TraineeService traineeService;
    @Autowired
    RecordService recordService;
    @Autowired
    ConsumptionService consumptionService;

    /**
     * 获取登录学员的订单列表
     *
     * @param state
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/getOrderList")
    public String getOrderList(String state, HttpServletRequest request, Model model) {
        List<Order> orderList = orderService.getOrderByState(state, (String) request.getSession().getAttribute("loginuser"));
        model.addAttribute("orderList", orderList);
        return "orders";
    }

    /**
     * 获取订单信息
     *
     * @param orderID
     * @param model
     * @return
     */
    @RequestMapping(value = "/orderInfo")
    public String getOrderInfo(int orderID, Model model) {
        Order order = orderService.getOrderById(orderID);
        Course course = courseService.getCourseByID(order.getCourseID());
        model.addAttribute("order", order);
        Date date = new Date();
        long interval = (course.getStartTime().getTime() - date.getTime()) / (1000 * 3600 * 24);

        model.addAttribute("courseTime", course.getStartTime());
        double price = order.getPrice();

        if (interval < 60 && interval >= 30) { //如果距离课程开始还有一个月到两个月，则退还80%费用
            price = price * 0.8;
        } else if (interval < 30 && interval >= 7) { //如果距离课程开始还有一周到一个月，则退还50%费用
            price = price * 0.5;
        } else if (interval < 7 && interval > 0) { //如果距离课程开始还有一周不到，则退还10%费用
            price = price * 0.1;
        }
        System.out.println("时间间隔为：" + interval);
        model.addAttribute("interval", interval);
        model.addAttribute("price", price);
        ClassRecord classRecord = new ClassRecord();
        if (order.getState().equals("进行中") || order.getState().equals("已完成")) {
            classRecord = recordService.getRecord(order);
        }
        model.addAttribute("record", classRecord);
        Consumption consumption = new Consumption();
        if (order.getState().equals("已退订")) {
            consumption = consumptionService.getConsumption(orderID);
        }
        model.addAttribute("consumption", consumption);
        return "orderInfo";
    }

    /**
     * 退订并且退款
     *
     * @param orderID
     * @param price
     * @param request
     * @return
     */
    @RequestMapping(value = "/cancelOrderWithRefund")
    public String cancelOrderWithRefund(int orderID, double price, HttpServletRequest request) {
        Order order = orderService.getOrderById(orderID);
        String traineeEmail = (String) request.getSession().getAttribute("loginuser");
        if (order.getTraineeID().equals(traineeEmail)) {
            orderService.cancelOrder(order, price);
            accountService.tradeAccount("admin", traineeEmail, price);
            traineeService.consume(traineeEmail, 0 - price);
            return "success";
        } else return "error";
    }

    /**
     * 仅退订不退款
     *
     * @param orderID
     * @param request
     * @return
     */
    @RequestMapping(value = "/cancelOrderWithoutRefund")
    public String cancelOrderWithoutRefund(int orderID, HttpServletRequest request) {
        Order order = orderService.getOrderById(orderID);
        String traineeEmail = (String) request.getSession().getAttribute("loginuser");
        if (order.getTraineeID().equals(traineeEmail)) {
            orderService.cancelOrder(order, 0);
            return "success";
        } else return "error";
    }
}
