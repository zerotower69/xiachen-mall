/* controller 控制层 */


package com.zero.xiachen.mall.admin.controller;

import com.zero.xiachen.mall.admin.entity.CartEntity;
import com.zero.xiachen.mall.admin.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * (Cart)表控制层
 *
 * @author ZeroTower
 * @since 2020-09-28 18:53:29
 */
@RestController
@RequestMapping("cart")
public class CartController {
    /**
     * 服务对象
     */
    @Autowired
    private CartService cartService;

    /**
     * 列出所有的数据
     *
     * @return 表中所有的结果
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<CartEntity> listAll() {
        return cartService.list();
    }
}

/* 能力有限，只给出了 list 方法 其余自己加入*/