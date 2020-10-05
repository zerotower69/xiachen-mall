package com.zero.xiachen.mall.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/page")
public class PageController {

    @GetMapping("/login")
    public String returnLoginPage(HttpServletResponse response){
        response.setStatus(200);
        return "login";
    }

    @GetMapping("/logup")
    public String returnLogupPage(HttpServletResponse response){
        response.setStatus(200);
        return "logup";
    }

    @GetMapping("/member")
    public String returnMemberPage(HttpServletResponse response){
        response.setStatus(200);
        return "member";
    }

    @GetMapping("/carts")
    public String returnCartsPage(HttpServletResponse response){
        response.setStatus(200);
        return "carts";
    }

    @GetMapping("/active")
    public String returnActivePage(@RequestParam String userCode, HttpServletResponse response){
        if(true==true)
        {
            return "active_yes";
        }
        else{
            return "active_no";
        }
    }

    @GetMapping("/main")
    public String returnMainPage(){
        return "index";
    }

    @GetMapping("/show/carts")
    public String returnCartPage(){
        return "carts";
    }

}
