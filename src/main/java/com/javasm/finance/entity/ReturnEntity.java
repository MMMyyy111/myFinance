package com.javasm.finance.entity;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReturnEntity {
    // 状态码
    private Integer returnCode;
    // 描述信息
    private String returnMsg;
    // 核心数据
    private Object returnData;
}
