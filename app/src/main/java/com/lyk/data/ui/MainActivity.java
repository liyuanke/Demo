package com.lyk.data.ui;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lyk.data.R;
import com.lyk.data.base.BaseActivity;
import com.lyk.data.common.Configs;
import com.lyk.data.model.FlowEntity;
import com.lyk.data.model.FlowResult;
import com.lyk.data.presenter.FlowPresenter;
import com.lyk.data.ui.adpater.CustomLoadingListItemCreator;
import com.lyk.data.ui.adpater.DefaultAdapter;
import com.lyk.data.ui.adpater.FlowAdapter;
import com.lyk.data.utils.ViewUtils;
import com.lyk.data.view.FlowView;
import com.paginate.Paginate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements FlowView {
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.fresh_view)
    SwipeRefreshLayout mReFreshView;
    private FlowPresenter mFlowPresenter;
    private int pageSize = 20;
    private int offset = 0;
    private List<FlowEntity> mItems;
    private FlowAdapter mAdapter;
    private Paginate mPaginate;
    private boolean isLoadingMore;
    private boolean hasLoadedAllItems;
    private String minYear = "2008";
    private String maxYear = "2018";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mItems = new ArrayList<>();
        mAdapter = new FlowAdapter(mItems);
        mRvList.setAdapter(mAdapter);
        mRvList.setLayoutManager(ViewUtils.provideLayoutManager(this));
        mFlowPresenter = new FlowPresenter(this);
        initPaginate();
        requestData();
        mReFreshView.setRefreshing(true);
        mReFreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                offset = 0;
                requestData();
            }
        });
        mAdapter.setOnItemClickListener(new DefaultAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int parentPosition, int childPosition) {
                if(childPosition  == 2){
                    ViewUtils.makeToast(MainActivity.this,"您点击了");
                }
            }
        });
    }

    public void requestData() {
        mFlowPresenter.getFlows(Configs.resource_id, offset, pageSize);
    }

    /**
     * 初始化Paginate,用于加载更多
     */
    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    isLoadingMore = true;
                    requestData();
                }

                @Override
                public boolean isLoading() {
                    return isLoadingMore;
                }

                @Override
                public boolean hasLoadedAllItems() {
                    return hasLoadedAllItems;
                }
            };

            mPaginate = Paginate.with(mRvList, callbacks)
                    .setLoadingTriggerThreshold(0)
                    .setLoadingListItemCreator(new CustomLoadingListItemCreator())
                    .build();
            mPaginate.setHasMoreDataToLoad(false);
        }
    }

    @Override
    public void loadData(FlowResult data) {
        if (offset == 0) {
            //刷新数据或者第一请求数据
            mItems.clear();
        } else {
            isLoadingMore = false;
        }
        if (data != null) {
            offset += data.getRecords().size();
            mItems.addAll(mFlowPresenter.getFilterFlowData(data, minYear, maxYear));
        }
        hasLoadedAllItems = (offset >= data.getTotal());
        mAdapter.setInfos(mItems);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void startLoadding() {

    }

    @Override
    public void finishLoadding() {
        mReFreshView.setRefreshing(false);
    }

    @Override
    public BaseActivity getContext() {
        return this;
    }
}
