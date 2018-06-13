package com.example.demo.dao;

import com.example.demo.model.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InstitutionDao extends JpaRepository<Institution, Integer> {
    @Query("select t from Institution t ")
    List<Institution> getList();

    Institution findByInstitutionID(int institutionID);

    Institution save(Institution institution);

}
