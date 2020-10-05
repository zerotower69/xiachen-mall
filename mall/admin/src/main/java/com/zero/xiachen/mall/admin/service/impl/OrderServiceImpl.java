/* ServiceImpl 服务实现类 */


package com.zero.xiachen.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zero.xiachen.mall.admin.dao.CartDao;
import com.zero.xiachen.mall.admin.dao.OrderDao;
import com.zero.xiachen.mall.admin.entity.CartEntity;
import com.zero.xiachen.mall.admin.entity.OrderEntity;
import com.zero.xiachen.mall.admin.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Order)表服务实现类
 *
 * @author ZeroTower
 * @since 2020-09-28 18:55:02
 */
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public IPage<OrderEntity> selectByPage(int current, int limit) {
        //查询对象
        //QueryWrapper<CartEntity> wrapper=new QueryWrapper<>();
        //分页，第一参数是第几页，第二个是每页多少条数据
        Page<OrderEntity> page=new Page<>(current,limit);
        //
        IPage<OrderEntity> orderIpage =orderDao.selectPage(page,null);
        return orderIpage;
    }
}