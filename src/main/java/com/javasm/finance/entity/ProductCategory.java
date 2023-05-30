package com.javasm.finance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class ProductCategory {
    //产品系列编号
    private Integer productCategoryId;
    //产品类型 0:基金 1:保险 2:证券
    private Integer productType;
    //产品渠道 0:香港资管 1:内地资管
    private Integer productChannel;
    //产品系列中文名称
    private String productCategoryName;
    //产品系列英文名称
    private String productEnCategoryName;
    //版本号
    private Integer versionId;
    //产品系列所属资管公司编号
    private String  companyName;

    public ProductCategory(Integer productType, Integer productChannel, String productCategoryName, String productEnCategoryName) {
        this.productType = productType;
        this.productChannel = productChannel;
        this.productCategoryName = productCategoryName;
        this.productEnCategoryName = productEnCategoryName;
    }
}
