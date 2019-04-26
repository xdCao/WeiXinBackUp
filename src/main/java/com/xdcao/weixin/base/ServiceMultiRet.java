package com.xdcao.weixin.base;

import java.util.List;

/**
 * @Author: buku.ch
 * @Date: 2019-03-28 17:17
 */


public class ServiceMultiRet<T> {

    private long total;

    private List<T> result;

    public ServiceMultiRet(long total, List<T> result) {
        this.total = total;
        this.result = result;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public int getResultSize() {
        if (result==null) {
            return 0;
        }
        return result.size();
    }
}
