/* ServiceImpl 服务实现类 */


package com.zero.xiachen.mall.admin.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zero.xiachen.mall.admin.dao.CategoryDao;
import com.zero.xiachen.mall.admin.entity.CategoryEntity;
import com.zero.xiachen.mall.admin.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (Category)表服务实现类
 *
 * @author ZeroTower
 * @since 2020-09-28 18:54:05
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {
    /**
     * 三级目录
     * @return
     */
    @Override
    public ArrayList listThree() {
        List<CategoryEntity> list=baseMapper.selectList(null);

        List<CategoryEntity> level3Menu=list.stream().filter(categoryEntity ->
                categoryEntity.getCatLevel()==3).collect(Collectors.toList());
        ArrayList<String> levelName=new ArrayList<>();
        for (CategoryEntity item:level3Menu
        ) {
            levelName.add(item.getName());
        }
        return levelName;
    }
}