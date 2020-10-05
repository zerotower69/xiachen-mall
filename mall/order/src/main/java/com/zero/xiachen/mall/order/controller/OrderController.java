/* controller 控制层 */


package com.zero.xiachen.mall.order.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zero.xiachen.mall.order.entity.CartEntity;
import com.zero.xiachen.mall.order.entity.OrderEntity;
import com.zero.xiachen.mall.order.service.CartService;
import com.zero.xiachen.mall.order.service.OrderService;
import com.zero.xiachen.mall.order.toolUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.zero.xiachen.mall.order.feign.UserFeignService;


/**
 * (Order)表控制层
 *
 * @author ZeroTower
 * @since 2020-09-28 23:46:57
 */
@RestController
@RequestMapping("order")
public class OrderController {
    /**
     * 服务对象
     */
    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserFeignService userFeignService;

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 列出所有的数据
     *
     * @return 表中所有的结果
     */
    @RequestMapping("/list")
    public List<OrderEntity> listAll() {
        return orderService.list();
    }

    @PostMapping("/paying")
    @ResponseBody
    public void payingGoods(@RequestBody List<JSONObject> list, HttpServletResponse response){
        List<CartEntity> cartsList=null;
        //json数据转为对象数组
        try {
            cartsList = JSONObject.parseArray(list.toString(), CartEntity.class);
        } catch (Exception e){
            e.printStackTrace();  //转换错误
            response.setStatus(500);
            return;
        }
        System.out.println(cartsList);
        //改变状态并更新,创建订单总览
        Date date=new Date();  //订单的创建时间
        OrderEntity order=new OrderEntity();  //实例化一个订单实体
        String orderCode=null;
        double orderAmount=0.00;
        try{
            //String orderCode=new toolUtils().generateOrderCode(date,cartsList.get(0).getUserId());
            for (CartEntity item:cartsList
            ) {
                item.setUserId(userFeignService.getIdByUserName(item.getUserName()));
                orderCode=new toolUtils().generateOrderCode(date,cartsList.get(0).getUserId());
                System.out.println(item);
                item.setActive(1);  //订单态
                item.setOrderCode(orderCode);
                cartService.saveOrUpdate(item);
                orderAmount+=item.getAmount();
            }
        } catch (Exception e){
            e.printStackTrace();  //
            response.setStatus(500);
            return;
        }

        //订单数据封装
        order.setCreateTime(date);
        order.setOrderCode(orderCode);
        order.setUserName(cartsList.get(0).getUserName());
        order.setUserId(cartsList.get(0).getUserId());
        order.setCount(list.toArray().length);
        order.setAmount(orderAmount);
        order.setStatus(0);
        //openFeign调用用户服务查看用户的信息
        order.setSendAddr(userFeignService.getUserEmail(order.getUserName()));
        try{
            orderService.saveOrUpdate(order);
        } catch (Exception e){
            e.printStackTrace();
            response.setStatus(500);
            return;
        }
        try{
           //消息队列数据封装
            JSONObject object=new JSONObject();
            object.put("email",order.getSendAddr());
            object.put("ordercode",order.getOrderCode());
            SimpleDateFormat ft=new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
            object.put("time",ft.format(order.getCreateTime()));
            object.put("username",order.getUserName());
            object.put("number",order.getCount().toString());
            object.put("amount",order.getAmount().toString());

            amqpTemplate.convertAndSend("order",object);
            response.setStatus(200);
            return;
        } catch (AmqpException e){
            e.printStackTrace();
            response.setStatus(500);
            return;
        }
    }
}

/* 能力有限，只给出了 list 方法 其余自己加入*/