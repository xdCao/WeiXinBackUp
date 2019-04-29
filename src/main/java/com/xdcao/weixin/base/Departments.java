package com.xdcao.weixin.base;

/**
 * @Author: buku.ch
 * @Date: 2019-04-27 12:11
 */


public enum Departments {

    NEI_KE(0,"内科"),WAI_KE(1,"外科"),ER_BI_HOU_KE(2,"耳鼻喉科"),OTHERS(10000,"没有此科室");

    private int value;

    private String name;

    Departments(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static Departments of(int value) {
        for (Departments departments : Departments.values()) {
            if (departments.getValue() == value) {
                return departments;
            }
        }
        return OTHERS;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
