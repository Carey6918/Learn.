package com.example.demo.service.impl;

import com.example.demo.dao.AccountDao;
import com.example.demo.dao.ApplicationDao;
import com.example.demo.dao.InstitutionDao;
import com.example.demo.model.Account;
import com.example.demo.model.Institution;
import com.example.demo.model.InstitutionApplication;
import com.example.demo.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    ApplicationDao applicationDao;
    @Autowired
    InstitutionDao institutionDao;
    @Autowired
    AccountDao accountDao;

    /**
     * 获取申请列表
     *
     * @return
     */
    @Override
    public List<InstitutionApplication> getApplicationList() {
        return applicationDao.getList();
    }

    /**
     * 同意申请
     *
     * @param applicationID
     */
    @Override
    public void acceptApplication(int applicationID) {
        InstitutionApplication application = applicationDao.findByApplicationID(applicationID);
        Institution institution = new Institution(application.getApplicationID(), application.getPassword(), application.getName(),
                application.getLocation(), application.getTeacher(), application.getIntroduction());
        institutionDao.save(institution);
        Account account = new Account();
        account.setEmail(String.valueOf(institution.getInstitutionID()));
        account.setBalance(0);
        account.setPassword(institution.getPassword());
        accountDao.save(account);
        applicationDao.deleteByApplicationID(applicationID);
        System.out.println("批准成功");
    }

    /**
     * 拒绝申请
     *
     * @param applicationID
     */
    @Override
    public void rejectApplication(int applicationID) {
        applicationDao.deleteByApplicationID(applicationID);
        System.out.println("删除成功");
    }
}
