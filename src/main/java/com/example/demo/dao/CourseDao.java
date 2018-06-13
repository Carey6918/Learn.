package com.example.demo.dao;

import com.example.demo.model.Course;
import com.example.demo.model.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface CourseDao extends JpaRepository<Course, Integer> {
    Course findByCourseID(int id);

    List<Course> findByType(String type);
    List<Course> findByTypeAndStartTimeAfter(String type, Date date);

    Course save(Course course);

    @Query("select c from Course c ")
    List<Course> getList();

    List<Course> findByStartTimeAfter(Date date);
    List<Course> findByInstitutionID(int id);

}
