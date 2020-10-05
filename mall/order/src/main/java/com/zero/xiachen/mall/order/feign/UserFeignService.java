package com.zero.xiachen.mall.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 *  服务调用，调用用户服务
 */
@FeignClient(value="xiachen-mall-user")
@RequestMapping("member")
public interface UserFeignService {

    @PostMapping("/get/userid")
    @ResponseBody
    public String getIdByUserName(@RequestBody String username);

    @RequestMapping("/get/user/email")
    @ResponseBody
    public String getUserEmail(@RequestParam String username);
}
