/* dao层 */
package com.zero.xiachen.mall.admin.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zero.xiachen.mall.admin.entity.OrderEntity;
import org.apache.ibatis.annotations.Mapper;


/**
 * (Order)表数据库访问层
 *
 * @author ZeroTower
 * @since 2020-09-28 18:55:02
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {

}