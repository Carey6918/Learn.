package com.example.demo.controller;

import com.example.demo.model.Account;
import com.example.demo.model.Course;
import com.example.demo.model.Order;
import com.example.demo.model.Trainee;
import com.example.demo.service.CourseService;
import com.example.demo.service.OrderService;
import com.example.demo.service.TraineeService;
import com.example.demo.util.Cache;
import com.example.demo.util.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class ChooseCourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private TraineeService traineeService;
    @Autowired
    private OrderService orderService;

    /**
     * 获取课程列表
     *
     * @param model
     * @param type  课程类型
     * @return
     */
    @RequestMapping(value = "/courses")
    public String getCourseList(Model model, String type) {
        System.out.println("type: " + type);
        List<Course> list = courseService.getCourseListByType(type);
        for (Course course : list) {
            System.out.println(course.getName());
        }
        model.addAttribute("courseList", list);
        return "courses";
    }

    /**
     * 预约课程
     *
     * @param order
     * @param course
     * @param model
     * @param courseID
     * @param request
     * @return
     */
    @RequestMapping(value = "/reserve")
    public String reserveCourse(@ModelAttribute Order order, @ModelAttribute Course course, Model model, int courseID, HttpServletRequest request) {
        course = courseService.getCourseByID(courseID);
        System.out.println(course.getName());
        model.addAttribute("course", course);
        model.addAttribute("order", order);
        double initPrice = course.getPrice() * course.getPeriod() * course.getTimes();
        Trainee trainee = traineeService.getTraineeByEmail((String) request.getSession().getAttribute("loginuser"));
        initPrice = initPrice * (1 - trainee.getVIPlevel() * 0.05);
        BigDecimal b  =  new  BigDecimal(initPrice);
        initPrice  =  b.setScale(1,  BigDecimal.ROUND_HALF_UP).doubleValue();
        model.addAttribute("initPrice", initPrice);

        return "reserve";
    }

    /**
     * 返回支付订单页面
     *
     * @param account
     * @param model
     * @param price
     * @return
     */
    @RequestMapping(value = "/pay")
    public String payPage(@ModelAttribute Account account, Model model, double price) {
        model.addAttribute("account", account);
        model.addAttribute("price", price);
        return "pay";
    }

    @RequestMapping(value = "/generateOrder")
    public String generateOrder() {
        Cache cache = CacheManager.getCacheInfo("order");
        System.out.println("取出暂存课程");
        if (cache != null) {
            Cache currentCache = (Cache) cache.getValue();
            Order order = (Order) currentCache.getValue();
            System.out.println(order.getPrice());
            orderService.createOrder(order);
            return "redirect:/orderInfo?orderID="+order.getOrderID();
        } else return "timeout";
    }


}
