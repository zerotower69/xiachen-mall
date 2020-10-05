/* controller 控制层 */


package com.zero.xiachen.mall.product.controller;

import com.alibaba.fastjson.JSONArray;
import com.zero.xiachen.mall.product.entity.ProductEntity;
import com.zero.xiachen.mall.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 商品详细信息(Product)表控制层
 *
 * @author ZeroTower
 * @since 2020-09-28 21:48:48
 */
@RestController
@RequestMapping("product")
public class ProductController {
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
     * TODO 查找商品功能是否改进
     * 根据三级目录查找所有的商品
     * @param catName
     * @return
     */
    @RequestMapping("/listCatName")
    @ResponseBody
    public List<ProductEntity> listCatName(@RequestParam("catName") String catName){

        return productService.listCatName(catName);
    }

    @PostMapping("/save/products")
    @ResponseBody
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
}

/* 能力有限，只给出了 list 方法 其余自己加入*/