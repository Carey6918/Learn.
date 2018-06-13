package com.example.demo.controller;

import com.example.demo.model.Institution;
import com.example.demo.model.InstitutionApplication;
import com.example.demo.service.InstitutionService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class InstitutionController {
    @Autowired
    private InstitutionService institutionService;

    public InstitutionController() {

    }

    /**
     * 机构首页
     * @param request
     * @return
     */
    @GetMapping("/indexForIns")
    public String index(HttpServletRequest request) {
        return "ins/index";
    }

    /**
     * 机构信息
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/infoForIns")
    public String getInfo(HttpServletRequest request, Model model) {
        Institution institution = institutionService.getInstitutionById((Integer) request.getSession().getAttribute("loginins"));
        InstitutionApplication application = new InstitutionApplication(institution.getInstitutionID(), institution.getPassword(), institution.getName(),
                institution.getLocation(), institution.getTeacher(), institution.getIntroduction(), "修改", null);
        model.addAttribute("application", application);
        return "ins/info";
    }

    /**
     * 申请修改机构信息
     * @param model
     * @param application 申请
     * @return
     */
    @RequestMapping(value = "/saveInfoForIns")
    public String saveInfo(Model model, @ModelAttribute InstitutionApplication application) {
        System.out.println("save application for: " + application.getApplicationID());
        institutionService.applyForModifyInstitution(application);
        model.addAttribute("application", application);
        return "ins/success";
    }

    @RequestMapping(value = "/applyInstitution")
    public String applyIns(Model model,@ModelAttribute InstitutionApplication application){
        model.addAttribute("application",application);
        return "apply";
    }

    @RequestMapping(value = "/applySuccess")
    public String applySuccess(Model model,@ModelAttribute InstitutionApplication application){
        institutionService.applyForRegisterInstitution(application);
        return "success";
    }
}
