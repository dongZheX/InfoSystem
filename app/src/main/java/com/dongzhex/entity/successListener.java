package com.dongzhex.entity;

import java.util.List;

/**
 * Created by ASUS on 2018/5/24.
 */

public interface successListener {
    public void success(User x);
    public void success(String x);
    public void successInfo(List<Info> mlist);
    public void successUserX(List<UserX> mlist);
};
