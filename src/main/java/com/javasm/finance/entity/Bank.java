package com.javasm.finance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class Bank {
    //银行编号
    private Integer bankId;
    //银行名称
    private String bankName;
    //银行swift码
    private String swiftCode;
    //银行代码
    private String bankCode;
    //人行系统支付号
    private String cnapsCode;
    //银行所在省
    private String bankProvince;
    //银行所在市
    private String bankCity;
    //银行所在区
    private String bankCounty;

}
