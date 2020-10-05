/* dao层 */
package com.zero.xiachen.mall.user.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zero.xiachen.mall.user.entity.MemberEntity;
import org.apache.ibatis.annotations.Mapper;


/**
 * (Member)表数据库访问层
 *
 * @author ZeroTower
 * @since 2020-09-28 14:46:30
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {

}