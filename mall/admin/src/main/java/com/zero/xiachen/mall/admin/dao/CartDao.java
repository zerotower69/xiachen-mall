/* dao层 */
package com.zero.xiachen.mall.admin.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zero.xiachen.mall.admin.entity.CartEntity;
import org.apache.ibatis.annotations.Mapper;


/**
 * (Cart)表数据库访问层
 *
 * @author ZeroTower
 * @since 2020-09-28 18:53:27
 */
@Mapper
public interface CartDao extends BaseMapper<CartEntity> {

}