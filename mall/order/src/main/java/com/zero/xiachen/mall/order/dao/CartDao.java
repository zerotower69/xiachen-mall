/* dao层 */
package com.zero.xiachen.mall.order.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zero.xiachen.mall.order.entity.CartEntity;
import org.apache.ibatis.annotations.Mapper;


/**
 * (Cart)表数据库访问层
 *
 * @author ZeroTower
 * @since 2020-09-28 23:46:00
 */
@Mapper
public interface CartDao extends BaseMapper<CartEntity> {

}