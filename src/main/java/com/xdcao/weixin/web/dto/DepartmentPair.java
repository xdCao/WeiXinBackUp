package com.xdcao.weixin.web.dto;

/**
 * @Author: buku.ch
 * @Date: 2019-04-30 11:17
 */


public class DepartmentPair {

    private String name;

    private Integer value;

    public DepartmentPair(String name, Integer value) {
        this.name = name;
        this.value = value;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
