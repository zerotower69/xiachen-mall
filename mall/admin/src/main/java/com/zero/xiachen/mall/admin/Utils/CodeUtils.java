package com.zero.xiachen.mall.admin.Utils;

import com.zero.xiachen.mall.admin.entity.TaskEntity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 此工具类专门生成各个模块的编码
 */
public class CodeUtils {

    /**
     *
     * @return 任务实体的编码
     */
    public String generateTaskCode(Date date,List<TaskEntity> list){
        String preffix="8032"; //任务代号,可以把代号配置专门写入配置文件，从中读取
        //年-->时 十位
        SimpleDateFormat ft=new SimpleDateFormat("yyyyMMddHH");
        String formatTime=ft.format(date);
        //System.out.println("格式时间："+formatTime);
        int num=1;  //num初始为1，但如果list不为null，则按照长度加1
        if(list!=null) {
            num = list.stream().filter(taskEntity -> taskEntity.getCode().contains(formatTime))
                    .collect(Collectors.toList()).toArray().length+1;  //过滤后获取新数组的长度
        }
        //编号
        DecimalFormat dt=new DecimalFormat("000");
        String suffix=dt.format(num);
        return preffix+formatTime+suffix;
    }
}
