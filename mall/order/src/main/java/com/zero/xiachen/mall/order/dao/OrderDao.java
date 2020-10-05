/* dao层 */
package com.zero.xiachen.mall.order.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zero.xiachen.mall.order.entity.OrderEntity;
import org.apache.ibatis.annotations.Mapper;


/**
 * (Order)表数据库访问层
 *
 * @author ZeroTower
 * @since 2020-09-28 23:46:56
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {

}