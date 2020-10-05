/* controller 控制层 */


package com.zero.xiachen.mall.admin.controller;

import com.zero.xiachen.mall.admin.entity.CategoryEntity;
import com.zero.xiachen.mall.admin.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * (Category)表控制层
 *
 * @author ZeroTower
 * @since 2020-09-28 18:54:05
 */
@RestController
@RequestMapping("category")
public class CategoryController {
    /**
     * 服务对象
     */
    @Autowired
    private CategoryService categoryService;

    /**
     * 列出所有的数据
     *
     * @return 表中所有的结果
     */
    @RequestMapping("/list")
    public List<CategoryEntity> listAll() {
        return categoryService.list();
    }

    /**
     * 三级目录
     * @return
     */
    @GetMapping("/list/three")
    public List listThree() {
        return categoryService.listThree();
    }
}

/* 能力有限，只给出了 list 方法 其余自己加入*/