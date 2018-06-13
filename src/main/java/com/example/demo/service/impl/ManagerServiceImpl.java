package com.example.demo.service.impl;

import com.example.demo.dao.ManagerDao;
import com.example.demo.model.Manager;
import com.example.demo.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    private ManagerDao managerDao;

    /**
     * 检查管理员密码
     *
     * @param id
     * @param password
     * @return
     */
    @Override
    public boolean checkPassword(String id, String password) {
        Manager manager = managerDao.findByManagerID(id);
        if (manager != null && manager.getPassword().equals(password)) {
            return true;
        } else return false;
    }
}
