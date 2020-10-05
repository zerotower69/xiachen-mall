/* Service 服务层 */


package com.zero.xiachen.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zero.xiachen.mall.order.entity.CartEntity;

import java.util.List;

/**
 * (Cart)表服务接口
 *
 * @author ZeroTower
 * @since 2020-09-28 23:46:01
 */
public interface CartService extends IService<CartEntity> {
    /**
     * 根据用户名和商品id找到购物车信息
     *
     * @param userName
     * @param productCode
     * @return 购物车列表
     */
    List<CartEntity> queryByUserNameAndProductCode(String userName, String productCode);
    public List<CartEntity> listUserCarts(String userName);
}