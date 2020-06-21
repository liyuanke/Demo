package com.lyk.data.presenter;

import com.lyk.data.http.Http;
import com.lyk.data.listener.MyObserver;
import com.lyk.data.subscribers.ProgressSubscriber;
import com.lyk.data.view.FlowView;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BasePresenter<T> {
    protected FlowView mView;
    protected T mService;
    private List<ProgressSubscriber> mProgressSubscriber = new LinkedList<>();

    public BasePresenter(FlowView mView) {
        this.mView = mView;
        this.mService = Http.getInstance().create(deSerializable());
        if (this.mView != null &&  this.mView.getContext() != null) {
            this.mView.getContext().addObserver(new MyObserver() {
                @Override
                public void unSubscribe() {
                    removeAll();
                }
            });
        }
    }

    private Class<T> deSerializable() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            return (Class<T>) parameterizedType.getActualTypeArguments()[0];
        }
        throw new RuntimeException();
    }

    public void addSubscriber(ProgressSubscriber subscriber) {
        if (subscriber != null) {
            boolean addEnable = true;
            for (ProgressSubscriber sub : mProgressSubscriber) {
                if (sub == subscriber) {
                    addEnable = false;
                    break;
                }
            }
            if (addEnable) {
                mProgressSubscriber.add(subscriber);
            }
        }
    }

    public void remove(ProgressSubscriber subscriber) {
        if (subscriber != null) {
            subscriber.onCancelProgress();
            Iterator<ProgressSubscriber> it = mProgressSubscriber.iterator();
            while (it.hasNext()) {
                ProgressSubscriber sub = it.next();
                it.remove();
            }
        }
    }

    public void removeAll() {
        for (ProgressSubscriber sub : mProgressSubscriber) {
            sub.onCancelProgress();
        }
        mProgressSubscriber.clear();
    }
}
