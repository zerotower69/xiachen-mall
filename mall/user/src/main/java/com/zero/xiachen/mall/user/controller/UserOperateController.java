package com.zero.xiachen.mall.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zero.xiachen.mall.user.entity.MemberEntity;
import com.zero.xiachen.mall.user.service.MemberService;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("user")
public class UserOperateController {

    /*注入服务层*/
    @Autowired
    private MemberService memberService;

    /**
     *  用户登录请求
     * @param data
     * @param request
     * @param response
     * @return
     * @throws Exception
     * 通过设置响应代码确定登录状态
     *
     */

    //线程池对象
    private ScheduledExecutorService scheduledExecutorService= Executors.newScheduledThreadPool(10);

    @Autowired
    private AmqpTemplate amqpTemplate;  //消息队列实例

    @PostMapping("/loginData")
    @ResponseBody
    public void loginData(@RequestBody JSONObject data, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.addHeader("Access-Control-Expose-Headers","message");
        if(data.equals(null)){
            response.setStatus(400);
           response.addHeader("message","user not exit");
            return;
        //提取JSON信息
    }
        String username=data.getString("username");
        String password=data.getString("password");
        String Cookie_username="xiachenMall";


        try {
            //获取此账户的信息,查库
            MemberEntity entity = memberService.checkUser(username);
            //System.out.println("输出当前的查询结果" + entity);  //测试
            //结果为空，用户不存在
            if(entity==null){
                response.setStatus(202);
                response.addHeader("message","user not exit");
                return;
            }
            if ((!entity.getPassword().equals(password)) ) {
                //密码不对,
                response.setStatus(202);
                response.addHeader("message","password wrong");
                return;
            }
            if(entity.getActive()==0){
                //未激活
                response.setStatus(202);
                response.addHeader("message","user not active");
                return;
            }
            //登录成功的
            Cookie[] cookies=request.getCookies();
            boolean create=true;
            if(cookies!=null){
                for (Cookie item:cookies
                ) {
                    if((item.getName().equals(Cookie_username) )){
                        create=false;
                        System.out.println("检测到了");
                        break;
                    }
                }}
            //System.out.println("检测输出222");
            if(create){
                Cookie cookie =new Cookie(Cookie_username,username);
                response.addCookie(cookie);
            }
            //System.out.println("创建完成");
            //登录成功，创建会话
            HttpSession session=request.getSession();
            session.setAttribute("XiachenMall",entity);
            response.setStatus(201);
            response.addHeader("message","ok");
            return;
        } //TODO
        catch (Exception e){
            //e.printStackTrace();  //打印这个异常
            //System.out.println(request.getContextPath());
            //response.sendRedirect(request.getContextPath());
            /*服务器异常*/
            response.setStatus(500);
            response.addHeader("message","service wrong");
            return;
        }

    }

    @RequestMapping("/get/session")
    @ResponseBody
    public String getSession(HttpServletRequest request,HttpServletResponse response){
        HttpSession session=request.getSession();
//        Cookie[] cookies =request.getCookies();
//        String username=null;
//        if(cookies!=null)
        MemberEntity user=(MemberEntity) session.getAttribute("XiachenMall");
        System.out.println("测试会话的内容"+user);
        if(user==null){
            return "default";
        }
        else{
            return user.getUserName();
        }
    }

    @RequestMapping("/logout")
    @ResponseBody
    public void releaseSession(HttpServletRequest request,HttpServletResponse response) throws IOException {
        try {
            HttpSession session = request.getSession();
            //TODO 打印提醒
            System.out.println("登出当前账户");
            MemberEntity user = new MemberEntity();
            user.setUserName("default");
            user.setPassword("");
            session.setAttribute("XiachenMall", user);
            //System.out.println("路径测试"+request.getHeader());
            //logger.info(request.getCookies().toString());
            response.setStatus(200);
            return;
        } catch (Exception e){
            e.printStackTrace();
            response.setStatus(500);
            return;
        }
    }

    /**
     * 用户邮箱验证码,验证码只管发送到消息队列，不管邮件服务由其它服务提供
     * @param data
     * @param request
     */
    @RequestMapping("/register/code")
    @ResponseBody
    public void sendUserValidetorCode(@RequestBody JSONObject data, HttpServletRequest request,HttpServletResponse response) {
        String email = data.getString("email");  //从json中获得email
        HttpSession session = request.getSession();  //获取session
        //生成验证码
        Random random = new Random();
        String code = null;
        while (true) {
            code = Integer.toString(random.nextInt(1000000));  //六位验证码
            if (code.length() < 6) {
                continue;
            } else {
                break;
            }
        }
        //logger.info("生成的六位密码为: "+code);
        //获取日期,并格式化
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = ft.format(date);
        //封装数据放入session
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        map.put("email", email);
        session.setAttribute("XCVerCode", map);
        Map<String, String> codeMap = (Map<String, String>) session.getAttribute("XCVerCode");
        System.out.println("错误定位");
        //创建线程池，到时间自动移除验证码
        try {
            scheduledExecutorService.schedule(new Thread(() -> {
                if (email.equals(codeMap.get("email"))) {
                    session.removeAttribute("XCVerCode");
                }
            }), 5 * 60, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
            return;
        }
        //验证码和邮箱数据封装后加入消息队列
        JSONObject object =new JSONObject();
        object.put("email",email);
        object.put("RegisterCode",code);
        object.put("time",time);
        try{
            //发送
            amqpTemplate.convertAndSend("RegisterCode",object);
            response.setStatus(220);  //正确响应代码
            return;
        } catch (AmqpException e){
            e.printStackTrace();  //打印异常
            response.setStatus(500);
            return;
        }
    }

    /**
     *  创建一个新的用户
     * JSON 包含用户信息
     * @param request
     * @param response
     * @throws IOException
     */
    @PostMapping("/add/one")
    @ResponseBody
    public void addNewUser(@RequestBody JSONObject data,
                           HttpServletRequest request,HttpServletResponse response) throws IOException {
        //TODO 数据是否正确接收
        //logger.info("注册信息传入后端数据库...");
        MemberEntity newUser=new MemberEntity();
        //数据提取封装
        newUser.setUserName(data.getString("username"));
        newUser.setUserEmail(data.getString("email"));
        newUser.setPassword(data.getString("password"));
        //生成用户的id,18位
        Date date=new Date();
        newUser.setUserId(generateUserId(date));
        newUser.setStatus(0);
        newUser.setActive(0);  //激活态为0
        //logger.info("打印这个新用户："+newUser);
        try {
            //logger.info("新用户即将被插入数据库...");
            memberService.save(newUser);
        } catch (Exception e){
            //logger.error("用户："+data.getString("username")+", 邮箱: "+data.getString("email")+" 注册失败");
            response.setStatus(501);
            e.printStackTrace();
            return;
        }
        try{
            //数据封装加入休息队列
            JSONObject object=new JSONObject();
            object.put("email",newUser.getUserEmail());
            object.put("userid",newUser.getUserId());
            object.put("username",newUser.getUserName());
            amqpTemplate.convertAndSend("User",object);
            response.setStatus(220);
            return;

        } catch (Exception e){
            e.printStackTrace();  //打印异常
            response.setStatus(230);
            return;
        }
    }

    @GetMapping("/active")
    public String activeUser(@RequestParam(value = "usercode") String usercode){
        //给定的用户编号
        //找用户
        Wrapper<MemberEntity> queryWrapper=new QueryWrapper<MemberEntity>().eq("user_id",usercode);
        MemberEntity entity=memberService.getOne(queryWrapper);
        if(entity==null){
            return "active_no";
        }
        else{
            if(entity.getActive()==1){
                return "active_no";
            }
            else{
                entity.setActive(1);
                memberService.saveOrUpdate(entity);
                return "active_yes";
            }
        }
    }

    /**
     *
     * @param data
     * @param request
     * @param response
     */
    @PostMapping("/validate/register/code")
    @ResponseBody
    public void validateUserCode(@RequestBody JSONObject data, HttpServletRequest request, HttpServletResponse response) {
        //提取Session
        String verCode = data.getString("code");
        String userEmail = data.getString("email");
        //logger.info("接受到的验证码和邮箱分别是: 验证码："+verCode+", 邮箱: "+userEmail);
        HttpSession session = request.getSession();
        Map<String, String> codeMap = (Map<String, String>) session.getAttribute("XCVerCode");
        System.out.println(codeMap);
        String code = null;
        String email = null;
        try {
            //get info from session:XCVerCode
            code = codeMap.get("code");
            email = codeMap.get("email");
        } catch (Exception e) {
            //logger.error("session过期或者读取错误\n"+e);
            response.setStatus(203);  //203 错误
            return;
        }
        //if the email and code don't match the session.
        if (!(email.equals(userEmail)) || !(code.equals(verCode))) {
            //logger.error("emial or vercode don't match session info");
            response.setStatus(204);
            return;
        }
        //remove verCode after using
        session.removeAttribute("XCVerCode");
        response.setStatus(200);
        return;
    }

    //辅助函数区

    /**
     *
     * @param date
     * @return (String) userID
     */
    private String generateUserId(Date date){
        //格式化日期输出
        SimpleDateFormat ft =new SimpleDateFormat("yyyyMMddHH");
        String time=ft.format(date);
        //测试输出
        System.out.println(time);
        //五位随机数
        Random random=new Random();
        String randNumber=Integer.toString(random.nextInt(100000));
        return "103"+time+randNumber;
    }

}
