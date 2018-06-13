package com.example.demo.controller;

import com.example.demo.model.Course;
import com.example.demo.service.CourseService;
import com.example.demo.service.OrderService;
import com.example.demo.service.RecordService;
import com.example.demo.service.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.tags.HtmlEscapeTag;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

@Controller
public class OnSitePayController {

    @Autowired
    CourseService courseService;
    @Autowired
    OrderService orderService;
    @Autowired
    RecordService recordService;
    @Autowired
    TraineeService traineeService;

    @RequestMapping(value = "/onSitePay")
    public String onSitePay(HttpServletRequest request,Model model){
        int institutionID = (int) request.getSession().getAttribute("loginins");
        List<Course> courseList =  courseService.getCourseByIns(institutionID);
        model.addAttribute("courseList",courseList);
        List<Double> priceList = new LinkedList<>();
        for(Course course : courseList){
             Double initPrice = course.getPrice() * course.getPeriod() * course.getTimes();
             priceList.add(initPrice);
        }
        model.addAttribute("priceList",priceList);
        return "ins/onsitepay";
    }

    @RequestMapping(value = "/submitOnsitePay")
    public String submitOnsitePay(int courseID,String traineeName,String traineeID,int classNum,double price){
        System.out.println(traineeID);
        if (traineeID==""){
            recordService.onsitePayWithoutID(courseID,traineeName);
        }else{
            orderService.onsitePayWithID(courseID,traineeName,traineeID,classNum,price);
            traineeService.consume(traineeID,price);
        }
        return "success";
    }

}
