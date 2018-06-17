package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.service.ConsumptionService;
import com.example.demo.service.StatisticsService;
import com.example.demo.service.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

@Controller
public class TraineeController {

    @Autowired
    private TraineeService traineeService;

    @Autowired
    private ConsumptionService consumptionService;

    @Autowired
    private StatisticsService statisticsService;
    public TraineeController() {

    }

    /**
     * 学员信息界面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/info")
    public String getInfo(HttpServletRequest request, Model model) {
        String email = (String) request.getSession().getAttribute("loginuser");
        Trainee trainee = traineeService.getTraineeByEmail(email);
        model.addAttribute("user",trainee);
        List<Consumption> consumptionList = consumptionService.getConsumptionsByTrainee(email);
        model.addAttribute("consumptionList",consumptionList);
        List<StatisticByTime> statisticByTimeList = statisticsService.getTimeStatistics(email);
        List<String> dateList = new LinkedList<>();
        List<Double> profitList = new LinkedList<>();
        List<Integer> volumeList = new LinkedList<>();
        for(int i = 0 ; i< statisticByTimeList.size();i++){
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String date = df.format(statisticByTimeList.get(i).getKey().getDate());
            dateList.add(date);
            profitList.add(statisticByTimeList.get(i).getProfit());
            volumeList.add(statisticByTimeList.get(i).getVolume());
        }
        model.addAttribute("profitList",profitList);
        model.addAttribute("dateList",dateList);
        model.addAttribute("volumeList",volumeList);
        InsPercentVO insPercentVO = statisticsService.getInsPercent(email);
        model.addAttribute("insPercent",insPercentVO);
        CourseGradeVO courseGradeVO = statisticsService.getCourseGrade(email);
        model.addAttribute("courseGrade",courseGradeVO);
        return "info";
    }

    /**
     * 修改学员信息
     * @param model
     * @param trainee
     * @return
     */
    @RequestMapping(value = "/saveInfo")
    public String saveInfo(Model model, @ModelAttribute Trainee trainee) {
        System.out.println(trainee.getEmail());
        traineeService.saveTrainee(trainee);
        model.addAttribute("user",trainee);
        return "info";
    }


}
