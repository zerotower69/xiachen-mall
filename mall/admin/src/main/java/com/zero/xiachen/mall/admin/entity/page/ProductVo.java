package com.zero.xiachen.mall.admin.entity.page;

import com.zero.xiachen.mall.admin.entity.ProductEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProductVo extends PageVo implements Serializable {
    /**
     * 每个分页返回类都是定义自己对象类型为元素的列表
     */
   private List<ProductEntity> data;
}
