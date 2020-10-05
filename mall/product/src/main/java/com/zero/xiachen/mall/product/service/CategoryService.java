/* Service 服务层 */


package com.zero.xiachen.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zero.xiachen.mall.product.entity.CategoryEntity;

import java.util.List;

/**
 * (Category)表服务接口
 *
 * @author ZeroTower
 * @since 2020-09-28 21:48:26
 */
public interface CategoryService extends IService<CategoryEntity> {
    /**
     * 树状结构返回
     * @return
     */
    List<CategoryEntity> listTree();
}