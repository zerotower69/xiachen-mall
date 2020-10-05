/* 实体类 */


package com.zero.xiachen.mall.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * (Order)实体类
 *
 * @author zerotower
 * @since 2020-09-28 18:55:01
 */


@Data
@TableName("myorder")
public class OrderEntity implements Serializable {
    private static final long serialVersionUID = 603511656939022474L;
    @TableId
    /**
     * 订单自增id
     */
    private Long id;
    /**
     * 订单编号年月日小时加上0000统计 小时最大订单数9999
     */
    private String orderCode;
    /**
     * 创建订单的用户名
     */
    private String userName;
    /**
     * 创建订单的用户id
     */
    private String userId;
    /**
     * 订单的内容
     */
    private String orderInfo;
    /**
     * 订单创建的时间
     */
    private Date createTime;

    /**
     * 格式化的时间
     */
    @TableField(exist = false)
    private String formatTime;
    /**
     * 订单的总商品数
     */
    private Integer count;
    /**
     * 订单的总额
     */
    private Double amount;
    /**
     * 订单状态 0--下单  1--取消  2--发货  3--运送中 4-- 已签收
     */
    private Integer status;
    /**
     * 订单发送的邮箱地址
     */
    private String sendAddr;

    //订单详情，查找详细的每种商品的信息
    @TableField(exist = false)
    List<CartEntity> info;


}