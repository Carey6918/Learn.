package com.example.demo.controller;

import com.example.demo.model.Course;
import com.example.demo.model.Order;
import com.example.demo.service.AccountService;
import com.example.demo.service.CourseService;
import com.example.demo.service.TraineeService;
import com.example.demo.util.Cache;
import com.example.demo.util.CacheManager;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private TraineeService traineeService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CourseService courseService;

    private long startMili;
    /**
     * 检查其他用户是否存在
     *
     * @param email
     * @param request
     * @return
     */
    @RequestMapping(value = "/checkOtherTrainee", method = RequestMethod.POST)
    public boolean checkOtherTrainee(String email, HttpServletRequest request) {
        if (traineeService.getTraineeByEmail(email) == null) {
            System.out.println("用户不存在");
            return false;
        } else return true;
    }

    /**
     * 提交订单
     *
     * @param order
     * @return
     */
    @RequestMapping(value = "/commitOrder")
    public Order commitOrder(String order) {
        Order cacheOrder;
        JSONObject jsonObject = JSONObject.fromObject(order);
        cacheOrder = (Order) JSONObject.toBean(jsonObject, Order.class);
        Cache cache = new Cache();
        cache.setKey("order");
        cache.setValue(cacheOrder);
        CacheManager.putCacheInfo("order", cache, 1000 * 60 * 1);
        System.out.println("暂存订单成功");
        startMili =System.currentTimeMillis();
        return cacheOrder;
    }

    /**
     * 支付是否成功
     *
     * @param email
     * @param password
     * @param num
     * @return
     */
    @RequestMapping(value = "/paySuccess")
    public int paySuccess(String email, String password, double num) {
        System.out.println(System.currentTimeMillis()-startMili);
        if (System.currentTimeMillis()-startMili<=1000*60*1){
            if (!accountService.checkAccount(email, password)) {
                System.out.println("账号密码错误");
                return -1;
            } else if (!accountService.checkBalance(email, num)) {
                System.out.println("账户余额不足");
                return -2;
            }else{
                traineeService.consume(email, num);
                accountService.tradeAccount(email, "admin", num);
                System.out.println("交易成功");
                return 0;
            }
        } else {
            System.out.println("超时");
            return -3;
        }

    }

    /**
     * 积分兑换卡金额
     *
     * @param type
     * @param request
     * @return
     */
    @RequestMapping(value = "/creditsExchange")
    public double creditsExchange(int type, HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("loginuser");
        return traineeService.creditsExchange(email, type);
    }

    /**
     * 检查用户是否存在及其会员登记
     *
     * @param email
     * @param request
     * @return
     */
    @RequestMapping(value = "/checkTrainee", method = RequestMethod.POST)
    public int checkTrainee(String email, HttpServletRequest request) {
        if (traineeService.getTraineeByEmail(email) == null) {
            System.out.println("用户不存在");
            return -1;
        } else return traineeService.getTraineeByEmail(email).getVIPlevel();
    }

    /**
     * 获取一门课程的价格
     *
     * @param courseID
     * @return
     */
    @RequestMapping(value = "/getPrice", method = RequestMethod.POST)
    public double getPrice(int courseID) {
        Course course = courseService.getCourseByID(courseID);
        double initPrice = course.getPrice() * course.getPeriod() * course.getTimes();
        return initPrice;
    }

}
