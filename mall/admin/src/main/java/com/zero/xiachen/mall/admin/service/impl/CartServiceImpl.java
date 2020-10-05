/* ServiceImpl 服务实现类 */


package com.zero.xiachen.mall.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zero.xiachen.mall.admin.dao.CartDao;
import com.zero.xiachen.mall.admin.entity.CartEntity;
import com.zero.xiachen.mall.admin.service.CartService;
import org.springframework.stereotype.Service;

/**
 * (Cart)表服务实现类
 *
 * @author ZeroTower
 * @since 2020-09-28 18:53:29
 */
@Service("cartService")
public class CartServiceImpl extends ServiceImpl<CartDao, CartEntity> implements CartService {
}