package com.lyk.data.listener;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public abstract class MyObserver implements LifecycleObserver {


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        unSubscribe();
        Log.d("onDestroy", "onDestroy");
    }

    public abstract void unSubscribe();
}
