package com.example.demo.dao;

import com.example.demo.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerDao extends JpaRepository<Manager,String> {
    Manager findByManagerID(String managerID);
}
