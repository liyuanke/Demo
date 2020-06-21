package com.lyk.data.base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.lyk.data.listener.MyObserver;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void addObserver(MyObserver observer) {
        getLifecycle().addObserver(observer);
    }
}
