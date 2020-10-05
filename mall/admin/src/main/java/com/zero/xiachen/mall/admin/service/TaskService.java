/* Service 服务层 */


package com.zero.xiachen.mall.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zero.xiachen.mall.admin.dao.TaskDao;
import com.zero.xiachen.mall.admin.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 任务状态表(Task)表服务接口
 *
 * @author ZeroTower
 * @since 2020-10-02 14:08:49
 */
public interface TaskService extends IService<TaskEntity> {
    IPage<TaskEntity> selectByPage(long current,long limit);

}