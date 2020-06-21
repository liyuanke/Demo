package com.lyk.data.presenter;

import com.lyk.data.http.service.FlowService;
import com.lyk.data.model.FlowEntity;
import com.lyk.data.model.FlowResult;
import com.lyk.data.model.Quarter;
import com.lyk.data.model.Result;
import com.lyk.data.subscribers.ProgressSubscriber;
import com.lyk.data.view.FlowView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FlowPresenter extends BasePresenter<FlowService> {

    public FlowPresenter(FlowView view) {
        super(view);
    }

    /**
     * 过滤数据
     * @param result
     * @param minYear
     * @param maxYear
     * @return
     */
    public List<FlowEntity> getFilterFlowData(FlowResult result, String minYear, String maxYear) {
        List<FlowEntity> data = new ArrayList<>();
        if (result != null) {
            Map<String, FlowEntity> flowMap = new HashMap<>();
            for (FlowResult.RecordsBean bean : result.getRecords()) {
                Quarter quarter = bean.getQuarter();
                if (quarter.getYear().compareTo(minYear) >= 0 && quarter.getYear().compareTo(maxYear) <= 0) {
                    FlowEntity flowEntity = flowMap.get(quarter.getYear());
                    if (flowEntity == null) {
                        flowEntity = new FlowEntity(quarter.getYear());
                        flowMap.put(quarter.getYear(), flowEntity);
                        data.add(flowEntity);
                    }
                    flowEntity.setValue(quarter.getQShort(), bean.getVolume_of_mobile_data());
                }
            }
        }
        Collections.sort(data);
        return data;
    }

    /**
     * 获取数据量
     *
     * @param resourceId
     * @param offset
     * @param limit
     */
    public void getFlows(String resourceId, int offset, int limit) {
        ProgressSubscriber flowSubscriber = new ProgressSubscriber<Result<FlowResult>>(mView) {
            @Override
            public void onNext(Result<FlowResult> data) {
                if (mView != null) {
                    mView.loadData(data.getResult());
                }
            }
        };
        mService.getFlow(resourceId, offset, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(flowSubscriber);
        addSubscriber(flowSubscriber);
    }

}
