package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.service.ApplicationService;
import com.example.demo.service.ConsumptionService;
import com.example.demo.service.ProfitService;
import com.example.demo.service.StatisticsService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Controller
public class ApplicationController {

    @Autowired
    ApplicationService applicationService;
    @Autowired
    ConsumptionService consumptionService;
    @Autowired
    ProfitService profitService;
    @Autowired
    StatisticsService statisticsService;
    /**
     * 管理员首页
     * @param model
     * @return
     */
    @RequestMapping(value = "/managerIndex")
    public String managerIndex(Model model){
        List<InstitutionApplication> list = applicationService.getApplicationList();
        model.addAttribute("applicationList",list);
        List<Profit> profitList = profitService.get7DaysProfit();
        List<Volume> volumeList = profitService.get7DaysVolume();
        List<String> dateList = new LinkedList<>();
        List<Double> priceList = new LinkedList<>();
        List<Integer> numberList = new LinkedList<>();
        for(int i = 0 ; i< profitList.size();i++){
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String date = df.format(profitList.get(i).getDate());
            dateList.add(date);
            priceList.add(profitList.get(i).getProfit());
            numberList.add(volumeList.get(i).getVolume());
        }
        List<TraineeStatistics> traineeStatisticsList = statisticsService.getTraineeStatistics();
        List<InstitutionStatistics> institutionStatisticsList = statisticsService.getInstitutionStatistics();
        model.addAttribute("traineeStatisticsList",traineeStatisticsList);
        model.addAttribute("institutionStatisticsList",institutionStatisticsList);

        model.addAttribute("priceList",priceList);
        model.addAttribute("dateList",dateList);
        model.addAttribute("numberList",numberList);
        List<Unsettled> unsettledList = consumptionService.getUnsettledConsumption();
        model.addAttribute("unsettledList",unsettledList);

        return "manager/index";
    }

    /**
     * 管理员同意申请
     * @param applicationID
     * @return
     */
    @RequestMapping(value = "/acceptApplication")
    public String acceptApplication(int applicationID){
        applicationService.acceptApplication(applicationID);
        return "redirect:/managerIndex";
    }

    /**
     * 管理员拒绝申请
     * @param applicationID
     * @return
     */
    @RequestMapping(value = "/rejectApplication")
    public String rejectApplication(int applicationID){
        applicationService.rejectApplication(applicationID);
        return "redirect:/managerIndex";
    }

    @RequestMapping(value = "/settleOne")
    public String settleOne(int institutionID,double profit,double proportion){
        consumptionService.settleByInstitution(profit,proportion,institutionID);
        return "success";
    }

    @RequestMapping(value = "/settleAll")
    public String settleAll(String profit, String proportion, String institutionID){
        List<Double> profitList = JSONArray.fromObject(profit);
        List<Double> proportionList = JSONArray.fromObject(proportion);
        List<Integer> idList = JSONArray.fromObject(institutionID);
        consumptionService.settleAllInstitution(profitList,proportionList,idList);
        return "success";
    }
}
