package com.xdcao.weixin.base;

/**
 * @Author: buku.ch
 * @Date: 2019-05-07 10:44
 */


public class UserVoteExcelElement {

    private String userName;

    private String department;

    private Integer index;

    public UserVoteExcelElement() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
