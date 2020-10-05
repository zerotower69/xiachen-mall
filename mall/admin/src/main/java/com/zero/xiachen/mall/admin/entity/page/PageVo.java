package com.zero.xiachen.mall.admin.entity.page;

import com.zero.xiachen.mall.admin.entity.OrderEntity;
import lombok.Data;

import java.util.List;

/**
 * 父类，写这个为了学习学习继承
 * 一个父类可以有多个子类，但一个子类只能有一个父类
 */
@Data  //即使是父类也需要引入lombok的get和set方法
public class PageVo {
    private long current;  //当前页
    private long limit; //一页的数据条数
    private long total;   //总数据数
    private long pages ;  //总页面数
}
