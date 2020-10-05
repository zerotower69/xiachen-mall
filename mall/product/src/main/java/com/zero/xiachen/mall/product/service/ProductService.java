/* Service 服务层 */


package com.zero.xiachen.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zero.xiachen.mall.product.entity.ProductEntity;

import java.util.List;

/**
 * 商品详细信息(Product)表服务接口
 *
 * @author ZeroTower
 * @since 2020-09-28 21:48:47
 */
public interface ProductService extends IService<ProductEntity> {
    /**
     * 按照给定的三级目录找商品
     * @param catName
     * @return
     */
    List<ProductEntity> listCatName(String catName);
}