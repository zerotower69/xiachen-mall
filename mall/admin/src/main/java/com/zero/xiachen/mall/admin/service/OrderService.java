/* Service 服务层 */


package com.zero.xiachen.mall.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zero.xiachen.mall.admin.entity.OrderEntity;

import java.util.List;

/**
 * (Order)表服务接口
 *
 * @author ZeroTower
 * @since 2020-09-28 18:55:02
 */
public interface OrderService extends IService<OrderEntity> {
    /**
     * 分页操作
     * @param current
     * @param limit
     * @return
     */
    IPage<OrderEntity> selectByPage(int current, int limit);
}