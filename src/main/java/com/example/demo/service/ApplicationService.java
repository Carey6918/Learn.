package com.example.demo.service;

import com.example.demo.model.InstitutionApplication;

import java.util.List;

public interface ApplicationService {
    List<InstitutionApplication> getApplicationList();

    void acceptApplication(int applicationID);

    void rejectApplication(int applicationID);
}
