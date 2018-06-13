package com.example.demo.service;

import com.example.demo.model.Course;

import java.util.List;

public interface CourseService {
    List getCourseListByType(String type);

    Course getCourseByID(int id);

    boolean createCourse(Course course);

    List<Course> getCourseByIns(int id);
}
