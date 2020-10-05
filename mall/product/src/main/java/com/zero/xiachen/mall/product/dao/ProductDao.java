/* dao层 */
package com.zero.xiachen.mall.product.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zero.xiachen.mall.product.entity.ProductEntity;
import org.apache.ibatis.annotations.Mapper;


/**
 * 商品详细信息(Product)表数据库访问层
 *
 * @author ZeroTower
 * @since 2020-09-28 21:48:47
 */
@Mapper
public interface ProductDao extends BaseMapper<ProductEntity> {

}