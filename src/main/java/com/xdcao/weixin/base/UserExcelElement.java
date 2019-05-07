package com.xdcao.weixin.base;

/**
 * @Author: buku.ch
 * @Date: 2019-04-30 10:58
 */


public class UserExcelElement {

    private String userName;

    private Integer score;

    private String department;

    public UserExcelElement() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
