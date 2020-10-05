package com.zero.xiachen.mall.admin.entity.page;

import com.zero.xiachen.mall.admin.entity.OrderEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderVo extends PageVo implements Serializable {
    List<OrderEntity> data; //返回的数据
}
