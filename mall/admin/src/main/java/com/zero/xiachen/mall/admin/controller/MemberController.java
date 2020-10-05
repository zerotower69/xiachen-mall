/* controller 控制层 */


package com.zero.xiachen.mall.admin.controller;

import com.zero.xiachen.mall.admin.entity.MemberEntity;
import com.zero.xiachen.mall.admin.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * (Member)表控制层
 *
 * @author ZeroTower
 * @since 2020-09-28 18:54:37
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
    public List<MemberEntity> listAll() {
        return memberService.list();
    }
}

/* 能力有限，只给出了 list 方法 其余自己加入*/