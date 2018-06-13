package com.example.demo.controller;

import com.example.demo.model.Consumption;
import com.example.demo.model.Trainee;
import com.example.demo.service.ConsumptionService;
import com.example.demo.service.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TraineeController {

    @Autowired
    private TraineeService traineeService;

    @Autowired
    private ConsumptionService consumptionService;
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
