/* 实体类 */


package com.zero.xiachen.mall.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 任务状态表(Task)实体类
 *
 * @author zerotower
 * @since 2020-10-03 22:45:55
 */


@Data
@TableName("task")
public class TaskEntity implements Serializable {
    private static final long serialVersionUID = -11985584054743343L;
    @TableId
    /**
     * 自增id
     */
    private long taskId;
    /**
     * 任务标题,不能重复
     */
    private String title;
    /**
     * 任务编号，前4位固定8032代表任务编号,5-12位代表日期,13-14位代表创建小时数,15-17位序号排序
     */
    private String code;
    /**
     * 任务内容
     */
    private String content;
    /**
     * 创建任务时间
     */
    private Date createTime;
    /**
     * 任务开始计时时间
     */
    private Date startTime;
    /**
     * 任务预计完成时间
     */
    private Date predictTime;
    /**
     * 任务实际完成时间
     */
    private Date realTime;
    /**
     * 总用时,记录的是秒数
     */
    private Long useTime;
    /**
     * 任务状态：0--创建  1--正在编辑  2--正在计时  3--暂停计时
     * 4--已经完成
     */
    private Integer status;
    /**
     * 历史状态
     */
    private Boolean history;
    /**
     * 详细备注信息
     */
    private String info;
    /**
     * 是否超时
     */
    private Boolean overtime;
    /**
     * 计时器,用来前端辅助计时使用
     */
    @TableField(exist = false)
    private Object  timer=null;

    @TableField(exist = false)
    private long leftTime=0;


}