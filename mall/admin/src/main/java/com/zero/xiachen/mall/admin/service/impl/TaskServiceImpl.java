/* ServiceImpl 服务实现类 */


package com.zero.xiachen.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zero.xiachen.mall.admin.dao.TaskDao;
import com.zero.xiachen.mall.admin.entity.TaskEntity;
import com.zero.xiachen.mall.admin.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 任务状态表(Task)表服务实现类
 *
 * @author ZeroTower
 * @since 2020-10-02 14:08:50
 */
@Service("taskService")
public class TaskServiceImpl extends ServiceImpl<TaskDao, TaskEntity> implements TaskService {
    @Autowired
    private TaskDao taskDao;

    @Override
    public IPage<TaskEntity> selectByPage(long current, long limit) {
        Page<TaskEntity> page=new Page<>();
        //如果要选择，就构建一个构造器
        //QueryWrapper<TaskEntity> queryWrapper=new QueryWrapper<>();
       return taskDao.selectPage(page,null);
    }
}