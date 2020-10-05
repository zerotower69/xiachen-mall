package com.zero.xiachen.mall.email.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/validate")
public class ValidateController {
    /**
     *
     * @param data
     * @param request
     * @param response
     */
    @PostMapping("/register/code")
    @ResponseBody
    public void validateUserCode(@RequestBody JSONObject data, HttpServletRequest request, HttpServletResponse response){
        //提取Session
        String verCode=data.getString("code");
        String userEmail=data.getString("email");
        //logger.info("接受到的验证码和邮箱分别是: 验证码："+verCode+", 邮箱: "+userEmail);
        HttpSession session=request.getSession();
        Map<String,String> codeMap=(Map<String, String>) session.getAttribute("XCVerCode");
        System.out.println(codeMap);
        String code=null;
        String email=null;
        try{
            //get info from session:XCVerCode
            code=codeMap.get("code");
            email=codeMap.get("email");
        } catch (Exception e){
            //logger.error("session过期或者读取错误\n"+e);
            response.setStatus(203);  //203 错误
            return;
        }
        //if the email and code don't match the session.
        if(!(email.equals(userEmail)) || !(code.equals(verCode))){
            //logger.error("emial or vercode don't match session info");
            response.setStatus(204);
            return;
        }
        //remove verCode after using
        session.removeAttribute("XCVerCode");
        response.setStatus(200);
        return;
    }

}
