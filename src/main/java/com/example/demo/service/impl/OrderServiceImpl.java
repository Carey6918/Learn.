package com.example.demo.service.impl;

import com.example.demo.dao.*;
import com.example.demo.model.*;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private RecordDao recordDao;
    @Autowired
    private ConsumptionDao consumptionDao;
    @Autowired
    private TraineeDao traineeDao;
    @Autowired
    private VolumeDao volumeDao;

    /**
     * 创建订单
     *
     * @param order
     */
    @Override
    public void createOrder(Order order) {
        //先查询是否选择班级，如果没有，要分配班级
        //分配班级的方案为：从小到大检查班级数，没满就分配进去
        if (!order.isChooseClass()) {
            for (int i = 1; i < 100; i++) {
                if (orderDao.findByClassNum(i).size() < courseDao.findByCourseID(order.getCourseID()).getTraineeNums()) {
                    order.setClassNum(i);
                    break;
                }
            }
        }
        order.setCourseName(courseDao.findByCourseID(order.getCourseID()).getName());
        order.setState("未开始");
        Date createTime = new Date();
        order.setCreateTime(createTime);
        order = orderDao.save(order);
        ClassRecord classRecord = new ClassRecord();
        classRecord.setCourseID(order.getCourseID());
        classRecord.setTraineeID(order.getTraineeID());
        classRecord.setOrderID(order.getOrderID());
        classRecord.setTraineeName(traineeDao.findByEmail(order.getTraineeID()).getName());
        recordDao.save(classRecord);
        //如果有其他学员，也生成对应的成绩和时间记录
        for (String otherTrainee : order.getOtherTrainee()) {
            if (!otherTrainee.equals("")) {
                ClassRecord otherClassRecord = new ClassRecord();
                otherClassRecord.setCourseID(order.getCourseID());
                otherClassRecord.setTraineeID(otherTrainee);
                otherClassRecord.setOrderID(order.getOrderID());
                otherClassRecord.setTraineeName(traineeDao.findByEmail(otherTrainee).getName());
                recordDao.save(otherClassRecord);
            }
        }
        //生成消费记录
        Consumption consumption = new Consumption(order.getTraineeID(), order.getPrice(), "支付订单", createTime, order.getOrderID());
        consumptionDao.save(consumption);
    }

    /**
     * 根据状态和登录用户查询订单
     *
     * @param state
     * @param traineeID
     * @return
     */
    @Override
    public List<Order> getOrderByState(String state, String traineeID) {
        if (state != null) {
            return orderDao.findByStateAndTraineeID(state, traineeID);
        } else return orderDao.findByTraineeID(traineeID);
    }

    /**
     * 根据ID获得订单详细信息
     *
     * @param id
     * @return
     */
    @Override
    public Order getOrderById(int id) {
        return orderDao.findByOrderID(id);
    }

    /**
     * 取消订单并退钱
     *
     * @param order
     * @param price
     */
    @Override
    public void cancelOrder(Order order, double price) {
        order.setState("已退订");
        orderDao.save(order);
        recordDao.deleteByOrderID(order.getOrderID());
        Consumption consumption = new Consumption(order.getTraineeID(), 0 - price, "撤销订单", new Date(), order.getOrderID());
        consumptionDao.save(consumption);
    }

    /**
     * 更新订单状态（定时器调用方法）
     */
    @Override
    public void updateState() {
        Date date = new Date();
        List<Course> list = courseDao.getList();
        for (Course course : list) {
            //如果开始时间比现在时间小，那么把该课程的"未开始"订单状态全部修改为"进行中"
            if (course.getStartTime().getTime() < date.getTime()) {
                List<Order> orderList = orderDao.findByCourseID(course.getCourseID());
                for (Order order : orderList) {
                    if (order.getState().equals("未开始")) {
                        order.setState("进行中");
                        orderDao.save(order);
                    }
                }
            }
        }
        System.out.println("成功更新订单状态！");
    }

    /**
     * 获取退订订单
     *
     * @param institutionID
     * @return
     */
    @Override
    public List<Order> getCanceledOrder(int institutionID) {
        List<Course> courseList = courseDao.findByInstitutionID(institutionID);
        List<Order> orderList = new LinkedList<Order>();
        for (Course course : courseList) {
            List<Order> tempList = orderDao.findByStateAndCourseID("已退订", course.getCourseID());
            for (Order order : tempList) {
                orderList.add(order);
            }
        }
        return orderList;
    }

    /**
     * 获取付款订单
     *
     * @param institutionID
     * @return
     */
    @Override
    public List<Order> getReservedOrder(int institutionID) {
        List<Course> courseList = courseDao.findByInstitutionID(institutionID);
        List<Order> orderList = new LinkedList<Order>();
        for (Course course : courseList) {
            List<Order> tempList = orderDao.findByCourseID(course.getCourseID());
            for (Order order : tempList) {
                if (!order.getState().equals("已退订")) {
                    orderList.add(order);
                }
            }
        }
        return orderList;
    }

    /**
     * 会员线下付款，则生成订单，记录，消费
     *
     * @param courseID
     * @param traineeName
     * @param traineeID
     * @param classNum
     * @param price
     */
    @Override
    public void onsitePayWithID(int courseID, String traineeName, String traineeID, int classNum, double price) {
        Order order = new Order();
        order.setCourseID(courseID);
        order.setTraineeID(traineeID);
        order.setClassNum(classNum);
        order.setPrice(price);
        order.setCourseName(courseDao.findByCourseID(order.getCourseID()).getName());
        order.setState("未开始");
        Date createTime = new Date();
        order.setCreateTime(createTime);
        order = orderDao.save(order);
        ClassRecord classRecord = new ClassRecord();
        classRecord.setCourseID(order.getCourseID());
        classRecord.setTraineeID(order.getTraineeID());
        classRecord.setOrderID(order.getOrderID());
        classRecord.setTraineeName(traineeName);
        recordDao.save(classRecord);
        //生成消费记录
        Consumption consumption = new Consumption(order.getTraineeID(), order.getPrice(), "线下缴费", createTime, order.getOrderID());
        consumptionDao.save(consumption);
    }


}
