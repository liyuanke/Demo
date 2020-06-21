package com.lyk.data.subscribers;

import android.util.Log;
import android.widget.Toast;

import com.lyk.data.listener.ProgressCancelListener;
import com.lyk.data.view.IView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * 用于在Http请求开始时，自动回调开始请求
 * 在Http请求结束是，自动回调请求结束
 * 调用者自己对请求数据进行处理
 */
public abstract class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {
    private IView iView;

    public ProgressSubscriber(IView view) {
        this.iView = view;
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        if (iView != null) {
            iView.startLoadding();
        }
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {
        if (iView != null) {
            iView.finishLoadding();
        }
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException && iView != null) {
            Log.e(getClass().getSimpleName(),"网络中断，请检查您的网络状态");
            Toast.makeText(iView.getContext(), "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Log.e(getClass().getSimpleName(),"网络中断，请检查您的网络状态");
            Toast.makeText(iView.getContext(), "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else {
            Log.e(getClass().getSimpleName(),e.getMessage());
            Toast.makeText(iView.getContext(), "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}