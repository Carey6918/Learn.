package com.example.demo.dao;

import com.example.demo.model.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface OrderDao extends JpaRepository<Order,Integer> {
    Order save(Order order);
    List<Order> findByStateAndTraineeID(String state,String traineeID);
    List<Order> findByTraineeID(String traineeID);
    List<Order> findByClassNum(int classNum);
    List<Order> findByCourseID(int courseID);
    Order findByOrderID(int id);
    List<Order> findByStateAndCourseID(String state,int courseID);

    List<Order> findByCreateTimeBetween(Date date1, Date date2);
}
