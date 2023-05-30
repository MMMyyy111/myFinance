package com.javasm.finance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class ProductRecommend {
    //产品编号
    private Integer productId;
    //产品名称
    private String productName;
    //产品系列编号
    private Integer productCategoryId;
    //推荐度
    private Integer recommendRate;
    //是否首发 0: 不首发  1: 首发
    private Integer isFirst;
    //是否开启线上申购 0: 不申购  1: 申购
    private Integer isOnline;
    //是否投顾端可见 0: 不可见  1: 可见
    private Integer isVisibleCustomer;
    //推荐理由
    private String recommendReason;
    //版本号
    private Integer versionId;
}
