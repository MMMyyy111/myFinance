package com.javasm.finance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class Area {
    private Integer areaCode;
    private String areaName;
    private Integer parentCode;
}
