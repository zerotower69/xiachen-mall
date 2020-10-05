/* ServiceImpl 服务实现类 */


package com.zero.xiachen.mall.product.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zero.xiachen.mall.product.dao.ProductDao;
import com.zero.xiachen.mall.product.entity.ProductEntity;
import com.zero.xiachen.mall.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品详细信息(Product)表服务实现类
 *
 * @author ZeroTower
 * @since 2020-09-28 21:48:47
 */
@Service("productService")
public class ProductServiceImpl extends ServiceImpl<ProductDao, ProductEntity> implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public List<ProductEntity> listCatName(String catName) {
        List<ProductEntity> list = productDao.selectList(null);
        List<ProductEntity> cates = list.stream().filter(productEntity ->
                productEntity.getProductCategory().equals(catName)
        ).collect(Collectors.toList());
        return cates;
    }
}