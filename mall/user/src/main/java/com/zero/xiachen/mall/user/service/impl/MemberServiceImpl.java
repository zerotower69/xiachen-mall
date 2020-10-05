package com.zero.xiachen.mall.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zero.xiachen.mall.user.dao.MemberDao;
import com.zero.xiachen.mall.user.entity.MemberEntity;
import com.zero.xiachen.mall.user.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (Member)表服务实现类
 *
 * @author ZeroTower
 * @since 2020-09-13 16:30:23
 */
@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public MemberEntity checkUser(String userName) {
        QueryWrapper<MemberEntity> queryWrapper =new QueryWrapper<MemberEntity>().eq("user_name",userName);
        MemberEntity entity= memberDao.selectOne(queryWrapper);
        return entity;
    }
}