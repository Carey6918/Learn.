package com.example.demo.service.impl;

import com.example.demo.dao.ConsumptionDao;
import com.example.demo.dao.CourseDao;
import com.example.demo.dao.InstitutionDao;
import com.example.demo.dao.OrderDao;
import com.example.demo.model.*;
import com.example.demo.service.AccountService;
import com.example.demo.service.ConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ConsumptionServiceImpl implements ConsumptionService {
    @Autowired
    private ConsumptionDao consumptionDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private InstitutionDao institutionDao;
    @Autowired
    private AccountService accountService;

    /**
     * 查询已退订的订单的消费记录
     *
     * @param orderID
     * @return
     */
    @Override
    public Consumption getConsumption(int orderID) {
        return consumptionDao.findByOrderIDAndReason(orderID, "撤销订单");
    }

    /**
     * 获取机构消费信息
     *
     * @param institutionID
     * @return
     */
    @Override
    public List<Consumption> getConsumptionsByIns(int institutionID) {
        List<Course> courseList = courseDao.findByInstitutionID(institutionID);
        List<Consumption> consumptionList = new LinkedList<Consumption>();
        for (Course course : courseList) {
            List<Order> tempOrderList = orderDao.findByCourseID(course.getCourseID());
            for (Order order : tempOrderList) {
                List<Consumption> tempList = consumptionDao.findByOrderID(order.getOrderID());
                for (Consumption consumption : tempList) {
                    consumptionList.add(consumption);
                }
            }
        }
        return consumptionList;
    }

    /**
     * 获取用户消费信息
     *
     * @param email
     * @return
     */
    @Override
    public List<Consumption> getConsumptionsByTrainee(String email) {
        return consumptionDao.findByTraineeID(email);
    }

    /**
     * 获取未结算的数额
     *
     * @return
     */
    @Override
    public List<Unsettled> getUnsettledConsumption() {
        List<Unsettled> unsettledList = new LinkedList<>();
        List<Institution> institutionList = institutionDao.getList();
        for (Institution institution : institutionList) {
            List<Consumption> consumptionList = getConsumptionsByIns(institution.getInstitutionID());
            double unsettledPrice = 0;
            for (Consumption consumption : consumptionList) {
                if (!consumption.isSettled()) {
                    unsettledPrice += consumption.getPrice();
                }
            }
            unsettledList.add(new Unsettled(institution.getInstitutionID(), unsettledPrice));
        }
        return unsettledList;
    }

    /**
     * 根据比例结算数额
     *
     * @param profit        数额
     * @param proportion    比例
     * @param institutionID 机构名
     */
    @Override
    public void settleByInstitution(double profit, double proportion, int institutionID) {
        accountService.tradeAccount("admin", String.valueOf(institutionID),profit*proportion);
        List<Consumption> consumptionList = getConsumptionsByIns(institutionID);
        for (Consumption consumption : consumptionList) {
            if (!consumption.isSettled()) {
                consumption.setSettled(true);
                consumptionDao.save(consumption);
            }
        }
    }

    @Override
    public void settleAllInstitution(List<Double> profit, List<Double> proportion, List<Integer> institutionID){
        for(int i = 0; i<institutionID.size();i++){
            accountService.tradeAccount("admin",String.valueOf(institutionID.get(i)),proportion.get(i)*profit.get(i));
            List<Consumption> consumptionList = getConsumptionsByIns(institutionID.get(i));
            for (Consumption consumption : consumptionList) {
                if (!consumption.isSettled()) {
                    consumption.setSettled(true);
                }
            }
        }
    }

}
