package com.javasm.finance.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class PageInfo {
    // 当前页
    private Integer page;
    // 每页显示的数条数
    private Integer pageSize;
    // 数据的总条数
    private long total;
}
