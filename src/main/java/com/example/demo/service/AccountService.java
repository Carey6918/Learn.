package com.example.demo.service;

public interface AccountService {
    boolean checkAccount(String email, String password);

    boolean checkBalance(String email, double num);

    void tradeAccount(String fromEmail, String toEmail, double num);
}
