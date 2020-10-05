/* ServiceImpl 服务实现类 */


package com.zero.xiachen.mall.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zero.xiachen.mall.order.dao.CartDao;
import com.zero.xiachen.mall.order.entity.CartEntity;
import com.zero.xiachen.mall.order.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * (Cart)表服务实现类
 *
 * @author ZeroTower
 * @since 2020-09-28 23:46:02
 */
@Service("cartService")
public class CartServiceImpl extends ServiceImpl<CartDao, CartEntity> implements CartService {

    @Autowired
    private CartDao cartDao;

    @Override
    public List<CartEntity> queryByUserNameAndProductCode(String userName, String productCode) {
        //使用queryWrapper和过滤器
        QueryWrapper<CartEntity> queryWrapper = new QueryWrapper<CartEntity>().eq("user_name", userName);
        //找到用户名符合的列表
        List<CartEntity> list = cartDao.selectList(queryWrapper);
        //过滤出商品id符合的并且是非订单的
        List<CartEntity> results = list.stream().filter(cartEntity ->
                cartEntity.getProductCode().equals(productCode)
        ).collect(Collectors.toList()).stream().filter(cartEntity ->
                cartEntity.getActive().equals(0)
        ).collect(Collectors.toList());
        return results;
    }

    @Override
    public List<CartEntity> listUserCarts(String userName) {
        QueryWrapper<CartEntity> queryWrapper = new QueryWrapper<CartEntity>().eq("user_name", userName);
        List<CartEntity> list=cartDao.selectList(queryWrapper);
        //过滤一次，把属于订单项过滤
        List<CartEntity> results=list.stream().filter(cartEntity ->
                cartEntity.getActive().equals(0)
        ).collect(Collectors.toList());
        return results;
    }
}