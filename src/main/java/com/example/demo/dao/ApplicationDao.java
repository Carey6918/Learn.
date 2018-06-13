package com.example.demo.dao;

import com.example.demo.model.InstitutionApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ApplicationDao extends JpaRepository<InstitutionApplication, Integer> {
    InstitutionApplication save(InstitutionApplication institutionApplication);

    @Query("select a from InstitutionApplication a ")
    List<InstitutionApplication> getList();

    InstitutionApplication findByApplicationID(int applicationID);

    @Transactional
    void deleteByApplicationID(int applicationID);

}
