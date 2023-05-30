package com.javasm.finance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class Product {
    //产品编号
    private Integer productId;
    //产品名称
    private String productName;
    //产品系列编号
    private Integer productCategoryId;
    //产品系列名称
    private String productCategoryName;
    //产品子系列名称
    private String productSecCategory;
    //产品管理机构名称
    private String productCompanyName;
    //年化收益率
    private String annualizedRate;
    //货币类型
    private String fundCurrency;
    //开放日期
    private String opDate;
    //认购周期
    private String subscriptionCycle;
    //基金管理费率
    private String relativeManageCost;
    //认购费率
    private String subscriptionRate;
    //起投金额
    private Integer startAmount;
    //认购费用收取方式
    private String chargeMode;
    //赎回周期
    private String redemingCycle;
    //赎回起始金额
    private Integer redemingStartAmont;
    //赎回费率
    private String redemingRate;
    //锁定期(封闭期)
    private String redemingLockUp;
    //创建人
    private String createUser;
    //创建时间
    private String createTime;
    //最新修改时间
    private String lastUpdateTime;
    //审核状态 0:未通过 1:审核中 2:已通过
    private Integer auditStatus;
    //单位净值
    private String netWorth;
    //净值基准日
    private String netWorthDate;
    //累计增长率
    private String growthRate;
    //审核人编号
    private Integer checkUser;
    //审核人名称
    private String checkUserName;
    //版本号
    private Integer versionId;
}
