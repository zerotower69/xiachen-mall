/* controller 控制层 */


package com.zero.xiachen.mall.user.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zero.xiachen.mall.user.entity.MemberEntity;
import com.zero.xiachen.mall.user.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * (Member)表控制层
 *
 * @author ZeroTower
 * @since 2020-09-28 14:46:32
 */
@RestController
@RequestMapping("member")
public class MemberController {
    /**
     * 服务对象
     */
    @Autowired
    private MemberService memberService;

    /**
     * 列出所有的数据
     *
     * @return 表中所有的结果
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<MemberEntity> listAll() {
        return memberService.list();
    }


    @RequestMapping("/get/userid")
    @ResponseBody
    public String getIdByUserName(@RequestBody String username){
        return memberService.checkUser(username).getUserId();
    }

    /**
     * 检查用户名是否存在
     * @param data
     * @return
     */
    @PostMapping("/check")
    @ResponseBody
    public void checkUserName(@RequestBody JSONObject data, HttpServletResponse response){
        String username=data.getString("username");
        if(memberService.checkUser(username)==null){
           response.setStatus(230);
           return;
        }
        else
        response.setStatus(200);
    }

    @GetMapping("/get/user/email")
    @ResponseBody
    public String getUserEmail(@RequestParam String username){
        QueryWrapper<MemberEntity> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_name",username);
        return memberService.list(queryWrapper).get(0).getUserEmail();
    }
}

/* 能力有限，只给出了 list 方法 其余自己加入*/