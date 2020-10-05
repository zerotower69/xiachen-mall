/* controller 控制层 */


package com.zero.xiachen.mall.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zero.xiachen.mall.admin.Utils.CodeUtils;
import com.zero.xiachen.mall.admin.Utils.TimeUtils;
import com.zero.xiachen.mall.admin.entity.TaskEntity;
import com.zero.xiachen.mall.admin.entity.page.TaskVo;
import com.zero.xiachen.mall.admin.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;


/**
 * 任务状态表(Task)表控制层
 *
 * @author ZeroTower
 * @since 2020-10-02 14:08:50
 */
@RestController
@RequestMapping("task")
public class TaskController {
    /**
     * 服务对象
     */
    @Autowired
    private TaskService taskService;

    /**
     * 编号生成工具
     */
    CodeUtils codeUtils=new CodeUtils();

    /**
     * 列出所有的数据
     *
     * @return 表中所有的结果
     */
    @RequestMapping("/list")
    public List<TaskEntity> listAll() {
        return taskService.list();
    }

    @RequestMapping("/api/list")
    public TaskVo listByPages(@RequestParam long current,
                              @RequestParam long limit,
                              HttpServletResponse response){
        try{
            IPage<TaskEntity> taskIPage=taskService.selectByPage(current, limit);
        TaskVo taskVo=new TaskVo();
        //封装数据
        taskVo.setCurrent(current);
        taskVo.setLimit(limit);
        taskVo.setTotal(taskIPage.getTotal());
        taskVo.setPages(taskIPage.getPages());
        //在这封装数据，设置剩余时间
            List<TaskEntity> records=taskIPage.getRecords();
            for(int i=0;i<records.toArray().length;i++){
                System.out.println("第"+i+"个"+records.get(i).toString());
                if(records.get(i).getStartTime()!=null)
                { records.get(i).setUseTime(TimeUtils.passTheTime(records.get(i).getStartTime()));}
                else continue;
            }
        taskVo.setList(records);
        response.setStatus(200);
        return taskVo;
        }
        catch (Exception e){
            e.printStackTrace();
            response.setStatus(500);
            return null;
        }
    }

    /**
     * 更新或者插入一条数据
     *
     * @return void
     */
    @PostMapping("/update/one")
    public void updateOne(@RequestBody TaskEntity entity, HttpServletResponse response) {
        System.out.println("打印获取的实体:  "+entity.toString());
        try {
            taskService.saveOrUpdate(entity);
            response.setStatus(200);
            return;
        }
        catch (Exception e){
            response.setStatus(500);
            return;
        }
    }

    /**
     * 创建任务
     * @param entity
     * @param response
     */
    @PostMapping("/create/one")
    public void createOneTask(@RequestBody TaskEntity entity,
                              HttpServletResponse response){
        try{
            Date date=new Date(); //生成时间
            entity.setCreateTime(date);
            String code=codeUtils.generateTaskCode(date,taskService.list());
            //System.out.println("测试编号 "+code);
            entity.setCode(code);
            taskService.save(entity);
            response.setStatus(200);
            return;
        } catch (Exception e){
           e.printStackTrace();  //打印异常，测试使用
            response.setStatus(500);
            return;
        }
    }

    /**
     * 重置任务 任务回到创建状态
     * @param taskEntity
     * @param response
     */
    @PostMapping("/reset/one")
    public void resetOneTask(@RequestBody TaskEntity taskEntity,
                             HttpServletResponse response){
        try{
            //开始时间、预计时间、用时清零
            taskEntity.setStartTime(null);
            taskEntity.setPredictTime(null);
            taskEntity.setUseTime(0L);
            taskEntity.setStatus(0);
            taskService.saveOrUpdate(taskEntity);
            response.setStatus(200);
            return;
        } catch (Exception e){
            e.printStackTrace();
            response.setStatus(500);
            return;
        }
    }


    /**
     * 结束任务
     * @param taskEntity
     * @param response
     */
    @PostMapping("/finish/one")
    public void finishOneTask(@RequestBody TaskEntity taskEntity,
                              HttpServletResponse response){
        try{
            //结束任务，
            taskEntity.setStatus(4);
            Date finishTime=new Date();
            //判定任务是否超时
            if(finishTime.after(taskEntity.getPredictTime())){
                taskEntity.setOvertime(true);
            }
            else{
                taskEntity.setOvertime(false);
            }
            taskService.saveOrUpdate(taskEntity);
            response.setStatus(200);
            return;
        } catch (Exception e){
            e.printStackTrace();
            response.setStatus(500);
            return;
        }
    }

    /**
     * 暂停任务计时
     * @param taskEntity
     * @param response
     */
    @PostMapping("/pause/one")
    public void pauseOneTask(@RequestBody TaskEntity taskEntity,
                              HttpServletResponse response) {
        try {
            taskEntity.setStatus(3);
            taskService.saveOrUpdate(taskEntity);
            response.setStatus(200);
            return;
        } catch (Exception e){
            response.setStatus(500);
            return;
        }
    }

    /**
     * 继续计时/启动计时
     * @param taskEntity
     * @param response
     */
    @PostMapping("/continue/one")
    public void continueOneTask(@RequestBody TaskEntity taskEntity,
                              HttpServletResponse response){
        try {
            taskEntity.setStatus(2);
            taskService.saveOrUpdate(taskEntity);
            response.setStatus(200);
            return;
        } catch (Exception e){
            //e.printStackTrace();  //print test
            response.setStatus(500);
            return;
        }
        }

    /**
     * 更新批量数据
     * @param taskEntityList
     * @param response
     */
    @PostMapping("/update/list")
    public void updateByList(@RequestBody List<TaskEntity> taskEntityList,
                             HttpServletResponse response){
        try{
            taskService.saveOrUpdateBatch(taskEntityList);
            response.setStatus(200);
            return;
        } catch (Exception e){
            response.setStatus(500);
            return;
        }
        }
//    @RequestMapping("/start/task")
//    public void start
}

/* 能力有限，只给出了 list 方法 其余自己加入*/