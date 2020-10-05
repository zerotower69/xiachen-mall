/* dao层 */
package com.zero.xiachen.mall.admin.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zero.xiachen.mall.admin.entity.TaskEntity;
import org.apache.ibatis.annotations.Mapper;


/**
 * 任务状态表(Task)表数据库访问层
 *
 * @author ZeroTower
 * @since 2020-10-02 14:08:48
 */
@Mapper
public interface TaskDao extends BaseMapper<TaskEntity> {

}