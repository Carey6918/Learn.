package com.example.demo.service.impl;

import com.example.demo.dao.ApplicationDao;
import com.example.demo.dao.InstitutionDao;
import com.example.demo.model.Institution;
import com.example.demo.model.InstitutionApplication;
import com.example.demo.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstitutionServiceImpl implements InstitutionService {

    @Autowired
    private InstitutionDao institutionDao;
    @Autowired
    private ApplicationDao applicationDao;

    /**
     * 检查机构密码是否正确
     *
     * @param id
     * @param password
     * @return
     */
    @Override
    public boolean checkPassword(int id, String password) {
        Institution institution = institutionDao.findByInstitutionID(id);
        if (institution != null && institution.getPassword().equals(password)) {
            return true;
        } else return false;
    }

    /**
     * 根据ID返回机构
     *
     * @param id
     * @return
     */
    @Override
    public Institution getInstitutionById(int id) {
        return institutionDao.findByInstitutionID(id);
    }

    /**
     * 申请修改机构信息
     *
     * @param application
     */
    @Override
    public void applyForModifyInstitution(InstitutionApplication application) {
        application.setType("修改");
        applicationDao.save(application);
    }

    /**
     * 申请注册机构信息
     *
     * @param application
     */
    @Override
    public void applyForRegisterInstitution(InstitutionApplication application) {
        int id = (int) ((Math.random() * 9 + 1) * 1000000);
        while (institutionDao.findByInstitutionID(id) != null) {
            id = (int) ((Math.random() * 9 + 1) * 1000000);
        }
        application.setApplicationID(id);
        application.setType("注册");
        applicationDao.save(application);
    }
}
