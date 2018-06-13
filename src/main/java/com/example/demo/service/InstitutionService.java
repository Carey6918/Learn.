package com.example.demo.service;

import com.example.demo.model.Institution;
import com.example.demo.model.InstitutionApplication;

public interface InstitutionService {
    boolean checkPassword(int id, String password);

    Institution getInstitutionById(int id);

    void applyForModifyInstitution(InstitutionApplication application);

    void applyForRegisterInstitution(InstitutionApplication application);
}
