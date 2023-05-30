package com.javasm.finance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class Company {
    //公司编号
    private Integer companyId;
    //公司名称
    private String companyName;
    //银行编号
    private Integer BankId;
    //收款人账户
    private String receiptAccount;
    //收款人名称
    private String receiptName;
    //收款人地址
    private String receiptAddress;
}
