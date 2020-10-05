/* 实体类 */


package com.zero.xiachen.mall.admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * (Member)实体类
 *
 * @author zerotower
 * @since 2020-09-28 18:54:37
 */


@Data
@TableName("member")
public class MemberEntity implements Serializable {
    private static final long serialVersionUID = 132544159126475408L;
    @TableId
    /**
     * 用户自增id
     */
    private Long id;
    /**
     * 用户名，最大长度不得超过20，且其具有唯一性
     */
    private String userName;
    /**
     * 用户账号id,唯一身份标识符,前八位是创建账号的日期，后四位是随机数
     */
    private String userId;
    /**
     * 密码最大长度不得超过16位,且必须包含大小写字母、数字中的两种
     */
    private String password;
    /**
     * 用户的邮箱，用于验证信息
     */
    private String userEmail;
    /**
     * 账号状态,0是未登录，1是登录状态，2是异常，3是账号违规中
     */
    private Integer status;
    /**
     * 账号激活态，0是注册未激活，1是已经激活
     */
    private Integer active;
    /**
     * 用户头像的url
     */
    private String avatar;


}