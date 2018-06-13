package com.example.demo.controller;

import com.example.demo.model.Course;
import com.example.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ReleaseCourseController {
    @Autowired
    CourseService courseService;
    @RequestMapping("/releaseCourse")
    public String releaseCourse(@ModelAttribute Course course, Model model){
        model.addAttribute("course",course);
        return "ins/courses";
    }

    @RequestMapping("/saveCourse")
    public String saveCourse(@ModelAttribute Course course,HttpServletRequest request){
        course.setInstitutionID((Integer) request.getSession().getAttribute("loginins"));
        course.setInstitutionName((String) request.getSession().getAttribute("insname"));
        courseService.createCourse(course);
        return "ins/success";
    }
}
