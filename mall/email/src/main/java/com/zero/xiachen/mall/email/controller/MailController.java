package com.zero.xiachen.mall.email.controller;

import com.alibaba.fastjson.JSONObject;
import com.zero.xiachen.mall.email.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping(value="/check")
    public void checkemail(@RequestBody JSONObject data, HttpServletResponse response){
        String email=data.getString("email");
        mailService.checkEmail(email,response);
        return;
    }
}
