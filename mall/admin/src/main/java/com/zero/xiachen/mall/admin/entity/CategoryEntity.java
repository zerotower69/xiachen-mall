/* 实体类 */


package com.zero.xiachen.mall.admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * (Category)实体类
 *
 * @author zerotower
 * @since 2020-09-28 18:54:05
 */


@Data
@TableName("category")
public class CategoryEntity implements Serializable {
    private static final long serialVersionUID = -79528629977445033L;
    @TableId
    /**
     * 分类自增id
     */
    private Long catId;
    /**
     * 分类名
     */
    private String name;
    /**
     * 父分类的id
     */
    private Long parentId;
    /**
     * 分类层级，目前只有1 2 3
     */
    private Integer catLevel;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 此分类下具有的商品总数
     */
    private Integer productCount;


}