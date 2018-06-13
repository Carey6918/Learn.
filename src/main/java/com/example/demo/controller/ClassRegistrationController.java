package com.example.demo.controller;

import com.example.demo.model.ClassRecord;
import com.example.demo.model.Course;
import com.example.demo.service.CourseService;
import com.example.demo.service.RecordService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ClassRegistrationController {
    @Autowired
    RecordService recordService;
    @Autowired
    CourseService courseService;

    /**
     * 获取登录机构的课程列表
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/registrationList")
    public String getCourseList(HttpServletRequest request, Model model) {
        int insID = (int) request.getSession().getAttribute("loginins");
        List<Course> courseList = courseService.getCourseByIns(insID);
        model.addAttribute("courseList", courseList);
        return "ins/class";
    }

    /**
     * 获取课程的学生记录列表
     *
     * @param courseID
     * @param model
     * @return
     */
    @RequestMapping(value = "/classRegistration")
    public String classRegistration(int courseID, Model model) {
        Course course = courseService.getCourseByID(courseID);
        model.addAttribute("courseName", course.getName());
        List<ClassRecord> recordList = recordService.getRecordListByCourseID(courseID);
        model.addAttribute("recordList", recordList);
        return "ins/registration";
    }

    /**
     * 登记一个记录
     *
     * @param recordID
     * @param time
     * @param grade
     * @return
     */
    @RequestMapping(value = "/registerOne")
    public String registerOne(int recordID, int time, int grade) {
        recordService.registerOne(recordID, time, grade);
        return "success";
    }

    /**
     * 登记所有记录
     *
     * @param recordID
     * @param time
     * @param grade
     * @return
     */
    @RequestMapping(value = "/registerAll")
    public String registerAll(String recordID, String time, String grade) {
        List idlist = JSONArray.fromObject(recordID);
        List timelist = JSONArray.fromObject(time);
        List gradelist = JSONArray.fromObject(grade);
        recordService.registerAll(idlist, timelist, gradelist);
        return "success";
    }
}
