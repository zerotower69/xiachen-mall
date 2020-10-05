/* Service 服务层 */


package com.zero.xiachen.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zero.xiachen.mall.admin.entity.CategoryEntity;

import java.util.ArrayList;

/**
 * (Category)表服务接口
 *
 * @author ZeroTower
 * @since 2020-09-28 18:54:05
 */
public interface CategoryService extends IService<CategoryEntity> {
    /**
     * 获取三级目录
     * @return
     */
    ArrayList listThree();
}