/* controller 控制层 */


package com.zero.xiachen.mall.order.controller;

import com.alibaba.fastjson.JSONObject;
import com.zero.xiachen.mall.order.entity.CartEntity;
import com.zero.xiachen.mall.order.service.CartService;
import com.zero.xiachen.mall.order.feign.UserFeignService;
import com.zero.xiachen.mall.order.toolUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;


/**
 * (Cart)表控制层
 *
 * @author ZeroTower
 * @since 2020-09-28 23:46:02
 */
@RestController
@RequestMapping("cart")
public class CartController {
    /**
     * 服务对象
     */
    @Autowired
    private CartService cartService;

    @Autowired
    private UserFeignService userFeign;

    /**
     * 列出所有的数据
     *
     * @return 表中所有的结果
     */
    @RequestMapping("/list")
    public List<CartEntity> listAll() {
        return cartService.list();
    }

    /**
     * 加入购物车
     * @param data
     */
    @RequestMapping("/insert/one")
    @ResponseBody
    public void addOneToCart(@RequestBody JSONObject data){

        CartEntity cartEntity=data.parseObject(data.toJSONString(),CartEntity.class);
        //找到用户对应的id
        String userId=userFeign.getIdByUserName(cartEntity.getUserName());
        cartEntity.setUserId(userId);
        System.out.println("插入的购物车信息: "+cartEntity);
        //订单封装完毕，插入购物车
        //TODO 如果此商品已经加入购物车又是同一用户就要数量加1
        try {
            List<CartEntity> exList = cartService.queryByUserNameAndProductCode(
                    cartEntity.getUserName(),cartEntity.getProductCode()
            );
            if (exList != null && exList.toArray().length!=0) {
                //System.out.println("找到的结果"+exList.get(0));
                System.out.println("打印找到的结果:"+exList);
                //结果不是空指针，则数量加1
                exList.get(0).setProductNum(exList.get(0).getProductNum()+1);
                cartService.saveOrUpdate(exList.get(0));
            }
            else{
                cartEntity.setActive(0);
                Date date =new Date();
                cartEntity.setCreateTime(date);
                cartEntity.setOrderCode(new toolUtils().generateOrderCode(date,cartEntity.getUserId()));
                cartService.save(cartEntity);
            }
        }catch (Exception e){   //打印异常测试
            e.printStackTrace();
        }
        return;
    }

    /**
     * 删除一个
     * @param entity
     */
    @RequestMapping("/delete/one")
    @ResponseBody
    public void deleteOneCartProduct(@RequestBody CartEntity entity){
        cartService.removeById(entity.getId());
    }

    /**
     * 更新数量
     * @param entity
     */
    @RequestMapping("/update/number")
    @ResponseBody
    public void updateCartnumber(@RequestBody CartEntity entity, HttpServletResponse response){
        try{
        cartService.saveOrUpdate(entity);
        response.setStatus(200);
        return;
        }catch (Exception e){
            e.printStackTrace();  //数据库请求异常
            response.setStatus(500);
            return;
        }
    }

    @RequestMapping("/list/user/carts")
    @ResponseBody
    public List<CartEntity> listCartsByUserName(@RequestParam String username){
        return cartService.listUserCarts(username);
    }


}

/* 能力有限，只给出了 list 方法 其余自己加入*/