package com.example.demo.controller;

import com.example.demo.model.Institution;
import com.example.demo.model.Trainee;
import com.example.demo.service.InstitutionService;
import com.example.demo.service.ManagerService;
import com.example.demo.service.ProfitService;
import com.example.demo.service.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class LoginController {
    @Autowired
    private TraineeService traineeService;
    @Autowired
    private InstitutionService institutionService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private ProfitService profitService;
    public LoginController() {
    }

    /**
     * 暂存的注册的人
     */
    Map<Integer, Trainee> registerUser = new ConcurrentHashMap<>();

    /**
     * 跳转到登录界面
     *
     * @param model
     * @return
     */
    @RequestMapping("/login")
    public String getLoginForm(Model model,HttpServletRequest request) {
        request.getSession().removeAttribute("loginuser");
        request.getSession().removeAttribute("loginname");
        request.getSession().removeAttribute("loginmanager");
        model.addAttribute("user", new Trainee());
        return "login";
    }


    /**
     * 登录
     *
     * @param trainee
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/dologin", method = RequestMethod.POST)
    public String doLogin(@ModelAttribute Trainee trainee, Model model, HttpServletRequest request) {
        boolean isManager = managerService.checkPassword(trainee.getEmail(), trainee.getPassword());
        if (isManager) {
            profitService.updateProfit();
            profitService.updateVolume();
            request.getSession().setAttribute("loginmanager",trainee.getEmail());
            return "redirect:/managerIndex";
        } else {
            boolean canLogin = traineeService.checkPassword(trainee.getEmail(), trainee.getPassword());
            if (canLogin) {
                trainee = traineeService.getTraineeByEmail(trainee.getEmail());
                request.getSession().setAttribute("loginuser", trainee.getEmail());
                request.getSession().setAttribute("loginname", trainee.getName());
                model.addAttribute("user", trainee);
                System.out.println("welcome," + request.getSession().getAttribute("loginname"));
                return "redirect:/index";
            } else return "error";
        }

    }

    /**
     * 注册
     *
     * @param trainee
     * @param model
     * @return
     */
    @RequestMapping(value = "/doregister", method = RequestMethod.POST)
    public String doRegister(@ModelAttribute Trainee trainee, Model model) throws MessagingException {
        int identifyCode = traineeService.checkEmail(trainee.getEmail());
        if (identifyCode == -1) {
            return "error";
        } else {
            model.addAttribute("identifyCode", identifyCode);
//            model.addAttribute("user",trainee);
            System.out.println("验证码："+identifyCode);
            registerUser.put(identifyCode, trainee);
            return "identify";
        }
    }

    /**
     * 注册认证成功
     *
     * @param identifyCode
     * @param request
     * @return
     */
    @RequestMapping(value = "/identifySuccess", method = RequestMethod.POST)
    public String identifyEmail(int identifyCode, HttpServletRequest request) {
        Trainee trainee = registerUser.get(identifyCode);
        boolean create = traineeService.createTrainee(trainee);
        if (create) {
            request.getSession().setAttribute("loginuser", trainee.getEmail());
            request.getSession().setAttribute("loginname", trainee.getName());
//            return "index";
        }
        return "redirect:index";
    }

    /**
     * 注销
     *
     * @param trainee
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout")
    public String logout(@ModelAttribute Trainee trainee, Model model, HttpServletRequest request) {
        request.getSession().removeAttribute("loginuser");
        request.getSession().removeAttribute("loginname");
        request.getSession().removeAttribute("loginins");
        request.getSession().removeAttribute("insname");
        model.addAttribute("user", trainee);
        return "redirect:/login";
    }

    /**
     * 机构登录界面
     * @param model
     * @return
     */
    @RequestMapping("/loginForIns")
    public String getLoginForInsForm(Model model) {
        model.addAttribute("institution", new Institution());
        return "ins/login";
    }

    /**
     * 机构登录
     * @param institution
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/dologinForIns", method = RequestMethod.POST)
    public String doLoginForIns(@ModelAttribute Institution institution, Model model, HttpServletRequest request) {
        boolean canLogin = institutionService.checkPassword(institution.getInstitutionID(), institution.getPassword());
        if (canLogin) {
            institution = institutionService.getInstitutionById(institution.getInstitutionID());
            request.getSession().setAttribute("loginins", institution.getInstitutionID());
            request.getSession().setAttribute("insname", institution.getName());
            System.out.println(institution.getName());
            model.addAttribute("institution", institution);
            System.out.println("welcome," + request.getSession().getAttribute("loginins"));
            return "ins/index";
        } else return "error";

    }
}
