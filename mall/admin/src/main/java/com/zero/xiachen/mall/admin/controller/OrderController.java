/* controller 控制层 */


package com.zero.xiachen.mall.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zero.xiachen.mall.admin.entity.CartEntity;
import com.zero.xiachen.mall.admin.entity.OrderEntity;
import com.zero.xiachen.mall.admin.entity.page.OrderVo;
import com.zero.xiachen.mall.admin.service.CartService;
import com.zero.xiachen.mall.admin.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * (Order)表控制层
 *
 * @author ZeroTower
 * @since 2020-09-28 18:55:02
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    /**
     * 服务对象
     */
    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    /**
     * 列出所有的数据
     *
     * @return 表中所有的结果
     */
    @RequestMapping("/list")
    public List<OrderEntity> listAll() {
        return orderService.list();
    }

    @RequestMapping("/list/info")
    @ResponseBody
    public List<OrderEntity> listInfo(){
       List<OrderEntity> list=orderService.list();
       if(list==null) return null;
       for(int i=0;i<list.toArray().length;i++){
           //补充更新商品的总价值
           String code=list.get(i).getOrderCode();
           if (code==null || code.length()<20) continue;
           //System.out.println("第"+(i+1)+"个数据: code="+code);
           //在这里使用过滤器不可行，暂不知道原因
           QueryWrapper<CartEntity> queryWrapper=new QueryWrapper<CartEntity>().eq("order_code",code);
           List<CartEntity> cartsList=cartService.list(queryWrapper);
           if(cartsList==null) return null;
           //原来商品订单价格计算有问题，这里再次补充，更新了了价格数据。
           double amount=0;
           for (CartEntity item: cartsList
                ) {
               item.setAmount(item.getProductNum()*item.getEachPrice());
               cartService.saveOrUpdate(item);
               amount+=item.getAmount();
           }
           list.get(i).setAmount(amount);
           list.get(i).setInfo(cartsList);
           //格式化时间
           SimpleDateFormat ft=new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
           list.get(i).setFormatTime(ft.format(list.get(i).getCreateTime()));
       }
       return list;
    }

    @RequestMapping("/api/list/info")
    @ResponseBody
    public OrderVo listInfoByPages(@RequestParam Integer current,
                                             @RequestParam Integer limit,
                                             HttpServletResponse response){
        IPage<OrderEntity> orderIpage=orderService.selectByPage(current,limit);
        //数据封装
        OrderVo orderVo=new OrderVo();
        orderVo.setCurrent(current);
        orderVo.setLimit(limit);
        orderVo.setPages(orderIpage.getPages());
        orderVo.setTotal(orderIpage.getTotal());
        orderVo.setData(orderIpage.getRecords());
        try{
        for(int i=0;i<orderVo.getData().toArray().length;i++){
            //System.out.println("第"+(i+1)+"条数据: "+orderVo.getData().get(i).toString());   //测试使用
            String code=orderVo.getData().get(i).getOrderCode();
            if (code==null || code.length()<20)
            {
                //System.out.println("跳过的"+orderVo.getData().get(i).toString());  //测试使用
                continue;
            }
            QueryWrapper<CartEntity> queryWrapper=new QueryWrapper<CartEntity>().eq("order_code",code);
            List<CartEntity> list=cartService.list(queryWrapper);
            List<CartEntity> cartsList=cartService.list(queryWrapper);
            //if(cartsList!=null) System.out.println(cartsList);
            orderVo.getData().get(i).setInfo(cartsList);
            //格式化时间
            SimpleDateFormat ft=new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
            orderVo.getData().get(i).setFormatTime(ft.format(orderVo.getData().get(i).getCreateTime()));
        }
        response.setStatus(200);
        return orderVo;}
        catch (Exception e){
            e.printStackTrace();
            response.setStatus(500);
            return  null;
        }
    }

    /**
     * 处理不合格的数据
     */
    @RequestMapping("/delete/bad/data")
    public void deleteWrongData(HttpServletResponse response){
        List<OrderEntity> originList=orderService.list();
        //用户名id都不能为空 订单编号长度20
        List<OrderEntity> filterList=originList.stream()
                .filter(orderEntity ->
                orderEntity.getUserName()==null || orderEntity.getUserId()==null
        || orderEntity.getOrderCode().length()!=20 || orderEntity.getCount()==null
        || orderEntity.getCount()==0 || orderEntity.getAmount()==null ||
                orderEntity.getSendAddr()==null ||!(orderEntity.getSendAddr().contains("@")))
                .collect(Collectors.toList());
        try {
            ArrayList<Serializable> idList=new ArrayList<>();
            filterList.forEach(orderEntity -> idList.add(orderEntity.getId()));
            orderService.removeByIds(idList);
            response.setStatus(200);
            return;
        } catch (Exception e){
            e.printStackTrace();
            response.setStatus(500);
            return;
        }
}
}

/* 能力有限，只给出了 list 方法 其余自己加入*/