package com.lyk.data.ui.adpater;


import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import butterknife.ButterKnife;

public abstract class BaseHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    protected OnViewClickListener mOnViewClickListener = null;
    protected final String TAG = this.getClass().getSimpleName();
    protected OnViewClickListener mOnViewLongClickListener = null;

    public BaseHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);//点击事件
        itemView.setOnLongClickListener(this);//长按事件
        ButterKnife.bind(this, itemView);//绑定
    }


    /**
     * 设置数据
     *
     * @param data
     * @param position
     */
    public abstract void setData(T data, int position);


    /**
     * 在 Activity 的 onDestroy 中使用 {@link DefaultAdapter#releaseAllHolder(RecyclerView)} 方法 (super.onDestroy() 之前)
     * {@link BaseHolder#onRelease()} 才会被调用, 可以在此方法中释放一些资源
     */
    protected void onRelease() {

    }

    @Override
    public void onClick(View view) {
        if (mOnViewClickListener != null) {
            mOnViewClickListener.onViewClick(view, this.getLayoutPosition(), -1);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mOnViewLongClickListener != null) {
            mOnViewLongClickListener.onViewClick(v, this.getLayoutPosition(), -1);
        }
        return true;
    }

    public interface OnViewClickListener {
        void onViewClick(View view, int parentPosition, int childPosition);
    }

    public void setOnViewLongClickListener(OnViewClickListener onViewLongClickListener) {
        this.mOnViewLongClickListener = onViewLongClickListener;
    }

    public void setOnItemClickListener(OnViewClickListener listener) {
        this.mOnViewClickListener = listener;
    }
}
