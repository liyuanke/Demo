package com.lyk.data.presenter;

import android.util.Log;

import com.lyk.data.base.BaseActivity;
import com.lyk.data.common.Configs;
import com.lyk.data.model.FlowEntity;
import com.lyk.data.model.FlowResult;
import com.lyk.data.view.FlowView;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@RunWith(Parameterized.class)
public class FlowPresenterTest {
    private int pageSize;
    private int offset;
    private String minYear;
    private String maxYear;
    private FlowPresenter presenter;

    public FlowPresenterTest(int pageSize, int offset, String minYear, String maxYear) {
        this.pageSize = pageSize;
        this.offset = offset;
        this.minYear = minYear;
        this.maxYear = maxYear;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> initData() {
        return Arrays.asList(new Object[][]{
                {20, 0, "2000", "2008"},
                {20, 0, "2000", "2018"},
                {20, 0, "2018", "2028"},

                {40, 0, "2000", "2008"},
                {40, 0, "2000", "2018"},
                {40, 0, "2018", "2028"},

                {20, 10, "2000", "2008"},
                {20, 10, "2000", "2018"},
                {20, 10, "2018", "2028"},

                {0, 10, "2000", "2008"},
                {0, 10, "2000", "2018"},
                {0, 10, "2018", "2028"},

                {100, 20, "2000", "2008"},
                {100, 20, "2000", "2018"},
                {100, 20, "2018", "2028"},

                {100, 200, "2000", "2008"},
                {100, 200, "2000", "2018"},
                {100, 200, "2018", "2028"},
        });
    }


    public void filterFlowData(FlowResult data) {
        List<FlowEntity> flow = presenter.getFilterFlowData(data, minYear, maxYear);
        Log.d(getClass().getSimpleName(), String.valueOf(flow.size()));
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (FlowEntity entity : flow) {
            if (sb.length() > 0) {
                sb.append(" ");
            }
            boolean result = entity.getYear().compareTo(minYear) >= 0 && entity.getYear().compareTo(maxYear) <= 0;
            sb.append(result);
            if (result) {
                count++;
            }
        }
        Assert.assertEquals(flow.size(), count);
    }

    @Test
    public void getFlows() {
        final CountDownLatch latch = new CountDownLatch(1);
        presenter = new FlowPresenter(new FlowView() {
            @Override
            public void loadData(FlowResult data) {
                filterFlowData(data);
            }

            @Override
            public void startLoadding() {

            }

            @Override
            public void finishLoadding() {
                latch.countDown();
            }

            @Override
            public BaseActivity getContext() {
                return null;
            }
        });
        presenter.getFlows(Configs.resource_id, offset, pageSize);
        try {
            //测试方法线程会在这里暂停, finishLoadding()方法执行完毕, 才会被唤醒继续执行
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}