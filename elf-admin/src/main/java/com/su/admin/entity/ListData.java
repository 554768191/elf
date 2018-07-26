package com.su.admin.entity;

import java.util.List;

/**
 * @Desc
 * @author surongyao
 * @date 2018/7/25 下午6:25
 * @version
 */
public class ListData<T> {
    int count;
    List<T> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
