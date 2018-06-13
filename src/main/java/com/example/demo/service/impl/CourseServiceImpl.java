package com.example.demo.service.impl;

import com.example.demo.dao.CourseDao;
import com.example.demo.model.Course;
import com.example.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseDao courseDao;

    /**
     * 根据类型获取课程列表
     *
     * @param type
     * @return
     */
    @Override
    public List getCourseListByType(String type) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0); //关键的一步，很多时候忽略了毫秒置0，而无法查询出想要的结果
        Date date = cal.getTime();

        if (type != null) {

            return courseDao.findByTypeAndStartTimeAfter(type,date);
        } else return courseDao.findByStartTimeAfter(date);
    }

    /**
     * 根据ID返回一门课程
     *
     * @param id
     * @return
     */
    @Override
    public Course getCourseByID(int id) {
        return courseDao.findByCourseID(id);
    }

    /**
     * 创建课程
     *
     * @param course
     * @return
     */
    @Override
    public boolean createCourse(Course course) {
        courseDao.save(course);
        return true;
    }

    /**
     * 查询某机构的所有课程
     *
     * @param id
     * @return
     */
    @Override
    public List<Course> getCourseByIns(int id) {
        return courseDao.findByInstitutionID(id);
    }
}
