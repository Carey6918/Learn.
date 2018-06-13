package com.example.demo.service;

import com.example.demo.model.Order;

import java.util.List;

public interface OrderService {
    void createOrder(Order order);


    List<Order> getOrderByState(String state, String traineeID);

    Order getOrderById(int id);

    void cancelOrder(Order order, double price);

    void updateState();


    List<Order> getCanceledOrder(int institutionID);

    List<Order> getReservedOrder(int institutionID);

    void onsitePayWithID(int courseID, String traineeName, String traineeID, int classNum, double price);

}
