package com.example.demo.dao;

import com.example.demo.model.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TraineeDao extends JpaRepository<Trainee, String> {

//    @Query("update Score t set t.score = :score where t.id = :id")
//    int updateScoreById(@Param("score") float score, @Param("id") int id);

    @Query("select t from Trainee t ")
    List<Trainee> getList();

    Trainee findByEmail(String email);

    Trainee save(Trainee trainee);

}
