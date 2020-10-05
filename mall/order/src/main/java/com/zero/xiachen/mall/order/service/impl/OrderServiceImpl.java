/* ServiceImpl 服务实现类 */


package com.zero.xiachen.mall.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zero.xiachen.mall.order.dao.OrderDao;
import com.zero.xiachen.mall.order.entity.OrderEntity;
import com.zero.xiachen.mall.order.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * (Order)表服务实现类
 *
 * @author ZeroTower
 * @since 2020-09-28 23:46:56
 */
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {
}