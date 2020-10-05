/* Service 服务层 */


package com.zero.xiachen.mall.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zero.xiachen.mall.admin.entity.ProductEntity;

/**
 * 商品详细信息(Product)表服务接口
 *
 * @author ZeroTower
 * @since 2020-09-28 18:56:50
 */
public interface ProductService extends IService<ProductEntity> {
    /**
     * 商品列出分页选择api
     * @param current
     * @param limit
     * @return
     */
   IPage<ProductEntity> selectPage(long current,long limit);
}