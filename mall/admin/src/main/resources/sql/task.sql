drop table if exists task;
CREATE TABLE IF NOT EXISTS `task`(
    `task_id` int unsigned auto_increment COMMENT '自增id',
    `title` varchar(12) NOT NULL UNIRUE COMMENT '任务标题,不能重复',
    `code` varchar(20) NOT NULL UNIQUE DEFAULT CONCAT(`task_id`,'') COMMENT '任务编号，前4位固定8032代表任务编号,5-12位代表日期,13-14位代表创建小时数,15-17位序号排序',
    `content` varchar(255) NOT NULL COMMENT '任务内容',
    `create_time` DATETIME NOT NULL COMMENT '创建任务时间',
    `start_time` DATETIME  COMMENT '任务开始计时时间',
    `predict_time` DATETIME NOT NULL COMMENT '任务预计完成时间',
    `real_time` DATETIME COMMENT '任务实际完成时间',
    `status` INT(1) NOT NULL DEFAULT 0 COMMENT '任务状态：0--创建  1--进行中   2--已完成',
    `history` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '历史状态',
    `info` varchar(300) COMMENT '详细备注信息',
    `overtime` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否超时',
    PRIMARY KEY (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4 COMMENT '任务状态表';
