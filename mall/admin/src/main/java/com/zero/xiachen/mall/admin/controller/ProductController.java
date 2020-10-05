/* controller 控制层 */


package com.zero.xiachen.mall.admin.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zero.xiachen.mall.admin.entity.ProductEntity;
import com.zero.xiachen.mall.admin.entity.page.ProductVo;
import com.zero.xiachen.mall.admin.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 商品详细信息(Product)表控制层
 *
 * @author ZeroTower
 * @since 2020-09-28 18:56:51
 */
@RestController
@RequestMapping("product")
public class ProductController {
    /**
     * 服务对象
     */
    @Autowired
    private ProductService productService;

    /**
     * 列出所有的数据
     *
     * @return 表中所有的结果
     */
    @RequestMapping("/list")
    public List<ProductEntity> listAll() {
        return productService.list();
    }

    /**
     *  分页的api接口
     * @param current
     * @param limit
     * @param response
     * @return
     */
    @RequestMapping("/api/list")
    public ProductVo listByPages(@RequestParam long current,
                                 @RequestParam long limit,
                                 HttpServletResponse response){
        try {
            IPage<ProductEntity> productEntityIPage = productService.selectPage(current, limit);
            //封装返回的数据
            ProductVo productVo = new ProductVo();
            productVo.setCurrent(current);
            productVo.setLimit(limit);
            productVo.setPages(productEntityIPage.getPages());
            productVo.setTotal(productEntityIPage.getTotal());
            productVo.setData(productEntityIPage.getRecords());
            response.setStatus(200);
            return productVo;
        }
        catch (Exception e){
            response.setStatus(500);
            return null;
        }
    }

    @PostMapping("/save/products")
    public void saveOrUpdateProducts(@RequestBody JSONArray list, HttpServletResponse response){
        try{
            //System.out.println(list);
            //productService.saveOrUpdateBatch(list);
            List<ProductEntity> proList=JSONArray.parseArray(list.toString(),ProductEntity.class);
            //System.out.println("现在的数据"+proList);
            productService.saveOrUpdateBatch(proList);
            response.setStatus(200);
        }
        catch (Exception e){
            e.printStackTrace();
            response.setStatus(530);
        }
    }

    @PostMapping("/delete/products")
    @ResponseBody
    public void deleteProducts(@RequestBody List<Long> list,HttpServletResponse response){
        try{

            //System.out.println(list);
            productService.removeByIds(list);
            response.setStatus(200);
        }
        catch (Exception e){
            e.printStackTrace();
            response.setStatus(530);
        }
    }
}

/* 能力有限，只给出了 list 方法 其余自己加入*/