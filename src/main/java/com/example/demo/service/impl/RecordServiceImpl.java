package com.example.demo.service.impl;

import com.example.demo.dao.OrderDao;
import com.example.demo.dao.RecordDao;
import com.example.demo.model.ClassRecord;
import com.example.demo.model.Order;
import com.example.demo.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordDao recordDao;
    @Autowired
    private OrderDao orderDao;

    /**
     * 根据订单获取课程记录
     *
     * @param order
     * @return
     */
    @Override
    public ClassRecord getRecord(Order order) {
        return recordDao.findByTraineeIDAndCourseID(order.getTraineeID(), order.getCourseID()).get(0);
    }

    /**
     * 根据课程id获取课程记录
     *
     * @param courseID
     * @return
     */
    @Override
    public List<ClassRecord> getRecordListByCourseID(int courseID) {
        return recordDao.findByCourseID(courseID);
    }

    /**
     * 登记一个记录
     *
     * @param registerID
     * @param time
     * @param grade
     */
    @Override
    public void registerOne(int registerID, int time, int grade) {
        ClassRecord record = recordDao.findById(registerID);
        record.setTime(time);
        record.setGrade(grade);
        recordDao.save(record);
        //如果成绩登记了，那么订单状态设置为已完成
        if (grade != 0) {
            Order order = orderDao.findByOrderID(record.getOrderID());
            order.setState("已完成");
            orderDao.save(order);
        }
    }

    /**
     * 登记某门课程的所有记录
     *
     * @param id
     * @param time
     * @param grade
     */
    @Override
    public void registerAll(List id, List time, List grade) {
        for (int i = 0; i < id.size(); i++) {
            ClassRecord record = recordDao.findById((Integer) id.get(i));
            record.setTime((Integer) time.get(i));
            record.setGrade((Integer) grade.get(i));
            recordDao.save(record);
            if ((Integer) grade.get(i) != 0) {
                Order order = orderDao.findByOrderID(record.getOrderID());
                order.setState("已完成");
                orderDao.save(order);
            }
        }
    }

    /**
     * 非会员线下缴费，只生成听课记录，不生成订单
     *
     * @param courseID
     * @param traineeName
     */
    @Override
    public void onsitePayWithoutID(int courseID, String traineeName) {
        ClassRecord record = new ClassRecord();
        record.setTraineeName(traineeName);
        record.setCourseID(courseID);
        record.setOrderID(0);
        record.setTraineeID("非会员线下缴费");
        recordDao.save(record);
    }
}
