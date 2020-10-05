/* dao层 */
package com.zero.xiachen.mall.admin.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zero.xiachen.mall.admin.entity.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;


/**
 * (Category)表数据库访问层
 *
 * @author ZeroTower
 * @since 2020-09-28 18:54:05
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {

}