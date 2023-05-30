package com.javasm.finance.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum  CodeAndMsg {
    LOGIN_SUCCESS(2000, "登录成功"),
    LOGIN_FAILURED(4000, "登录失败，用户名或密码输入错误"),
    QUERY_SUCCESS(2001,"成功查询到数据"),
    QUERY_FAILURED(4001,"没有查询到数据"),
    OPERATE_SUCCESS(2002,"操作成功"),
    OPERATE_FAILURED(4002,"操作失败"),
    NO_LOGIN(4005,"没有登录"),
    ID_DUPLICATE(4006,"编号被占用"),
    DATA_ISUPDATE(4007,"请重新获取最新数据"),
    NO_AUTH(4008,"没有权限"),
    LOGOUT_SUCCESS(2003,"退出成功");

    // 状态码
    private Integer returnCode;
    // 描述信息
    private String returnMsg;
}
