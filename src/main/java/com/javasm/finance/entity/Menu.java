package com.javasm.finance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class Menu {
    // 菜单编号
    private Integer menuId;
    // 菜单名称
    private String menuName;
    // 上级菜单编号
    private Integer parentId;
    // 上级菜单名称
    private String parentName;
    // 访问地址
    private String menuUrl;
    // 菜单图标
    private String glyphicon;
    // 版本编号
    private Integer versionId;

    // 二级菜单数据
    private List<Menu> subMenu;
}
