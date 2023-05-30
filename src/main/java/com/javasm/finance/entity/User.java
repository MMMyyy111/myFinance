package com.javasm.finance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class User {
     //用户编号
     private Integer userId;
     //用户名
     private String userName;
     //用户密码
     private String userPwd;
     //用户角色 角色id 1管理员 2普通用户
     private Integer roleId;
     //注册时间
     private String regTime;
     //登录时间
     private String loginTime;
     //用户状态 0在职 1休假 2离职
     private Integer isValid;
     //头像地址
     private String headImg;
     //版本编号
     private Integer versionId;


     public User(String userName, Integer roleId, String regTime, Integer isValid, String headImg) {
          this.userName = userName;
          this.roleId = roleId;
          this.regTime = regTime;
          this.isValid = isValid;
          this.headImg = headImg;
     }
}
