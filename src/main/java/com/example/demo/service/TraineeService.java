package com.example.demo.service;

import com.example.demo.model.Trainee;

import javax.mail.MessagingException;

public interface TraineeService {

    boolean checkPassword(String email, String password);

    boolean createTrainee(Trainee trainee);

    int checkEmail(String email) throws MessagingException;

    Trainee getTraineeByEmail(String email);

    Trainee saveTrainee(Trainee trainee);

    void consume(String email, double num);

    double creditsExchange(String email, int type);
}
