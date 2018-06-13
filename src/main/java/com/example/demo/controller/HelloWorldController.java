package com.example.demo.controller;

import com.example.demo.model.Trainee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HelloWorldController {
    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        return "index";
    }
}
