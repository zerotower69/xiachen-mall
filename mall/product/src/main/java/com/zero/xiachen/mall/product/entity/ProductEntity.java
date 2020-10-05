/* 实体类 */


package com.zero.xiachen.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品详细信息(Product)实体类
 *
 * @author zerotower
 * @since 2020-09-28 21:48:47
 */


@Data
@TableName("product")
public class ProductEntity implements Serializable {
    private static final long serialVersionUID = 223307212338441339L;
    @TableId
    /**
     * 商品自增id
     */
    private Long id;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品信息
     */
    private String productInfo;
    /**
     * 商品编码,前2位表示一级分类的代码，3-5表示二级分类代码，6-8三级分类，9-12随机数产生，但是不能重复，否则重新生成
     */
    private String productCode;
    /**
     * 商品库存
     */
    private Integer productNum;
    /**
     * 商品所属的三级分类
     */
    private String productCategory;
    /**
     * 商品价格
     */
    private Double productPrice;
    /**
     * 商品计量单位
     */
    private String productUnit;
    /**
     * 商品图片的保存地址
     */
    private String imgAddr;


}