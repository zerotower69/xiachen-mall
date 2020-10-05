package com.zero.xiachen.mall.admin.entity.page;

import com.zero.xiachen.mall.admin.entity.TaskEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Task 分页结果
 */
@Data
public class TaskVo extends PageVo implements Serializable {
   private List<TaskEntity> list;
}
