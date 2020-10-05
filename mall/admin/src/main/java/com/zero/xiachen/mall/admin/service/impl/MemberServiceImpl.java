/* ServiceImpl 服务实现类 */


package com.zero.xiachen.mall.admin.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zero.xiachen.mall.admin.dao.MemberDao;
import com.zero.xiachen.mall.admin.entity.MemberEntity;
import com.zero.xiachen.mall.admin.service.MemberService;
import org.springframework.stereotype.Service;

/**
 * (Member)表服务实现类
 *
 * @author ZeroTower
 * @since 2020-09-28 18:54:37
 */
@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {
}