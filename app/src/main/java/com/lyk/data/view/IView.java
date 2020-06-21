package com.lyk.data.view;

import com.lyk.data.base.BaseActivity;

public interface IView {
    void startLoadding();//开始加载

    void finishLoadding();//结束加载

    BaseActivity getContext();
}
