package com.example.demo.service.impl;

import com.example.demo.dao.AccountDao;
import com.example.demo.dao.TraineeDao;
import com.example.demo.dao.TraineeStatisticsDao;
import com.example.demo.model.Account;
import com.example.demo.model.Trainee;
import com.example.demo.model.TraineeStatistics;
import com.example.demo.service.TraineeService;
import com.example.demo.util.SendEmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class TraineeServiceImpl implements TraineeService {

    @Autowired
    private TraineeDao traineeDao;

    @Autowired
    private SendEmailUtil sendEmailUtil;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private TraineeStatisticsDao traineeStatisticsDao;
    public static final int[] EXCHANGE_POINTS = {100, 500, 1000, 2000, 3000};
    public static final int[] EXCHANGE_BALANCE = {5, 30, 80, 200, 400};

    /**
     * 检查密码
     *
     * @param email
     * @param password
     * @return
     */
    @Override
    public boolean checkPassword(String email, String password) {
        Trainee trainee = traineeDao.findByEmail(email);
        if (trainee != null && trainee.getPassword().equals(password)) {
            return true;
        } else return false;
    }

    /**
     * 创建一个账户
     *
     * @param trainee
     * @return
     */
    @Override
    public boolean createTrainee(Trainee trainee) {
        if (traineeDao.findByEmail(trainee.getEmail()) == null) {
            Account account = new Account(trainee.getEmail(), trainee.getPassword(), 10000);
            TraineeStatistics traineeStatistics = new TraineeStatistics();
            traineeStatistics.setTraineeID(trainee.getEmail());
            traineeStatisticsDao.save(traineeStatistics);
            traineeDao.save(trainee);
            accountDao.save(account);
            return true;
        } else return false;
    }

    /**
     * 验证邮箱是否存在
     *
     * @param email
     * @return 验证码，如果邮箱存在返回-1
     * @throws MessagingException
     */
    @Override
    public int checkEmail(String email) throws MessagingException {
        if (traineeDao.findByEmail(email) == null) {
            int identifyCode = (int) (Math.random() * 10000);
            sendEmailUtil.sendAttachmentsMail(identifyCode,email);
            return identifyCode;
        } else return -1;
    }

    /**
     * 根据邮箱查找用户
     *
     * @param email
     * @return
     */
    @Override
    public Trainee getTraineeByEmail(String email) {
        return traineeDao.findByEmail(email);
    }

    /**
     * 修改信息
     *
     * @param trainee
     * @return
     */
    @Override
    public Trainee saveTrainee(Trainee trainee) {
        Trainee trainee1 = traineeDao.findByEmail(trainee.getEmail());
        trainee1.setName(trainee.getName());
        trainee1.setAge(trainee.getAge());
        trainee1.setSex(trainee.getSex());
        return traineeDao.save(trainee1);
    }

    /**
     * 根据消费数额计算积分
     *
     * @param email
     * @param num
     */
    @Override
    public void consume(String email, double num) {
        Trainee trainee = traineeDao.findByEmail(email);
        trainee.setPoints((int) (trainee.getPoints() + num));
        trainee.setConsumption((int) (trainee.getConsumption() + num));
        int consumption = trainee.getConsumption();
        if (500 <= consumption && consumption < 1000) {
            trainee.setVIPlevel(1);
        } else if (1000 <= consumption && consumption < 2000) {
            trainee.setVIPlevel(2);
        } else if (2000 <= consumption && consumption < 4000) {
            trainee.setVIPlevel(3);
        } else if (4000 <= consumption && consumption < 8000) {
            trainee.setVIPlevel(4);
        } else if (8000 <= consumption) {
            trainee.setVIPlevel(5);
        }
        traineeDao.save(trainee);
    }

    /**
     * 将积分兑换为卡金额
     *
     * @param email
     * @param type
     * @return 如果积分不够，返回-1，如果积分够，返回当前卡金额
     */
    @Override
    public double creditsExchange(String email, int type) {
        int points = EXCHANGE_POINTS[type];
        int balance = EXCHANGE_BALANCE[type];
        Trainee trainee = traineeDao.findByEmail(email);
        if (trainee.getPoints() >= points) {
            trainee.setPoints(trainee.getPoints() - points);
            Account account = accountDao.findAccountByEmail(email);
            account.setBalance(account.getBalance() + balance);
            traineeDao.save(trainee);
            accountDao.save(account);
            System.out.println("兑换积分成功！");
            return account.getBalance();

        } else return -1;
    }

}
