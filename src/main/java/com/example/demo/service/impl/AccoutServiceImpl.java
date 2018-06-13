package com.example.demo.service.impl;

import com.example.demo.dao.AccountDao;
import com.example.demo.model.Account;
import com.example.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccoutServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;

    /**
     * 判断支付账户密码是否正确
     *
     * @param
     * @return
     */
    @Override
    public boolean checkAccount(String email,String password) {
        Account rightAccount = accountDao.findAccountByEmail(email);
        if (rightAccount.getPassword().equals(password) ){
            return true;
        } else return false;
    }

    /**
     * 检查账户余额是否足够
     *
     * @param email
     * @param num
     * @return
     */
    @Override
    public boolean checkBalance(String email, double num) {
        Account account = accountDao.findAccountByEmail(email);
        //如果账户余额比支付金额多
        if (account.getBalance() >= num) {
            return true;
        } else return false;
    }

    /**
     * 转移账户余额
     *
     * @param fromEmail
     * @param toEmail
     * @param num
     */
    @Override
    public void tradeAccount(String fromEmail, String toEmail, double num) {
        Account fromAccount = accountDao.findAccountByEmail(fromEmail);
        Account toAccount = accountDao.findAccountByEmail(toEmail);
        double fromBalance = fromAccount.getBalance();
        double toBalance = toAccount.getBalance();
        fromAccount.setBalance(fromBalance - num);
        toAccount.setBalance(toBalance + num);
        accountDao.save(fromAccount);
        accountDao.save(toAccount);
    }
}
