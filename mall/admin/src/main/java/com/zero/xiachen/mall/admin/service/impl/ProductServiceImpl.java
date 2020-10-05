/* ServiceImpl 服务实现类 */


package com.zero.xiachen.mall.admin.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zero.xiachen.mall.admin.dao.ProductDao;
import com.zero.xiachen.mall.admin.entity.ProductEntity;
import com.zero.xiachen.mall.admin.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商品详细信息(Product)表服务实现类
 *
 * @author ZeroTower
 * @since 2020-09-28 18:56:51
 */
@Service("productService")
public class ProductServiceImpl extends ServiceImpl<ProductDao, ProductEntity> implements ProductService {
    //注入Dao
    @Autowired
    private ProductDao productDao;

    /**
     * 分页接口实现
     * @param current
     * @param limit
     * @return
     */
    @Override
    public IPage<ProductEntity> selectPage(long current, long limit) {
        Page<ProductEntity> page=new Page<>(current,limit);
        //查询所有，不用生成构造器
        IPage<ProductEntity> productIpage=productDao.selectPage(page,null);
        return productIpage;
    }
}