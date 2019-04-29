package com.xdcao.weixin.base;

/**
 * @Author: buku.ch
 * @Date: 2019-04-29 21:20
 */


public class DepartmentExcelElement {

    private Integer totalScore;

    private double avaScore;

    private Integer userNum;

    private String departName;

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public double getAvaScore() {
        return avaScore;
    }

    public void setAvaScore(double avaScore) {
        this.avaScore = avaScore;
    }

    public Integer getUserNum() {
        return userNum;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getDepartName() {
        return departName;
    }
}
