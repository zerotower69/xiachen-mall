package com.zero.xiachen.mall.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zero.xiachen.mall.user.entity.MemberEntity;


/**
 * (Member)表服务接口
 *
 * @author ZeroTower
 * @since 2020-09-13 16:30:23
 */
public interface MemberService extends IService<MemberEntity> {
    MemberEntity checkUser(String userName);
}