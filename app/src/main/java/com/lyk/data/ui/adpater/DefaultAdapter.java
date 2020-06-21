package com.lyk.data.ui.adpater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public abstract class DefaultAdapter<T> extends RecyclerView.Adapter<BaseHolder<T>> {
    protected List<T> mInfos;
    protected OnItemClickListener mOnItemClickListener = null;
    protected OnItemClickListener mOnItemLongClickListener = null;

    protected BaseHolder.OnViewClickListener mOnViewClickListener;
    protected BaseHolder.OnViewClickListener mOnViewLongClickListener;
    protected boolean editEnable = true;

    public void setEditEnable(boolean editEnable) {
        this.editEnable = editEnable;
    }

    public DefaultAdapter(List<T> infos) {
        super();
        setInfos(infos);
    }

    public void setInfos(List<T> infos) {
        if (this.mInfos == null) {
            this.mInfos = new ArrayList<>();
        }
        this.mInfos.clear();
        if (infos != null) {
            this.mInfos.addAll(infos);
        }
    }

    /**
     * 创建 {@link BaseHolder}
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public BaseHolder<T> onCreateViewHolder(ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(viewType), parent, false);
        BaseHolder holder = getHolder(view, viewType);
        if (mOnViewClickListener == null) {
            mOnViewClickListener = new BaseHolder.OnViewClickListener() {
                @Override
                public void onViewClick(View view, int parentPosition, int childPosition) {
                    if (mOnItemClickListener != null && editEnable) {
                        if (mInfos != null && parentPosition >= 0 && parentPosition < mInfos.size()) {
                            mOnItemClickListener.onItemClick(view, viewType, mInfos.get(parentPosition), parentPosition, childPosition);
                        } else {
                            mOnItemClickListener.onItemClick(view, viewType, null, parentPosition, childPosition);
                        }
                    }
                }//设置Item点击事件

            };
        }

        if (mOnViewLongClickListener == null) {
            mOnViewLongClickListener = new BaseHolder.OnViewClickListener() {
                @Override
                public void onViewClick(View view, int parentPosition, int childPosition) {
                    if (mOnItemLongClickListener != null) {
                        if (mInfos != null && parentPosition >= 0 && parentPosition < mInfos.size()) {
                            mOnItemLongClickListener.onItemClick(view, viewType, mInfos.get(parentPosition), parentPosition, childPosition);
                        } else {
                            mOnItemLongClickListener.onItemClick(view, viewType, null, parentPosition, childPosition);
                        }
                    }
                }
            };
        }
        holder.setOnItemClickListener(mOnViewClickListener);
        holder.setOnViewLongClickListener(mOnViewLongClickListener);
        return holder;
    }

    /**
     * 绑定数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(BaseHolder<T> holder, int position) {
        holder.setData(getItem(position), position);
    }


    /**
     * 返回数据的个数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mInfos == null ? 0 : mInfos.size();
    }


    public List<T> getInfos() {
        return mInfos;
    }

    /**
     * 获得某个 {@code position} 上的 item 的数据
     *
     * @param position
     * @return
     */
    public T getItem(int position) {
        if (mInfos == null) {
            return null;
        }
        return position < mInfos.size() ? mInfos.get(position) : null;
    }

    /**
     * 让子类实现用以提供 {@link BaseHolder}
     *
     * @param v
     * @param viewType
     * @return
     */
    public abstract BaseHolder<T> getHolder(View v, int viewType);

    /**
     * 提供用于 {@code item} 布局的 {@code layoutId}
     *
     * @param viewType
     * @return
     */
    public abstract int getLayoutId(int viewType);


    /**
     * 遍历所有{@link BaseHolder},释放他们需要释放的资源
     *
     * @param recyclerView
     */
    public static void releaseAllHolder(RecyclerView recyclerView) {
        if (recyclerView == null) return;
        for (int i = recyclerView.getChildCount() - 1; i >= 0; i--) {
            final View view = recyclerView.getChildAt(i);
            RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
            if (viewHolder != null && viewHolder instanceof BaseHolder) {
                ((BaseHolder) viewHolder).onRelease();
            }
        }
    }


    public interface OnItemClickListener<T> {
        void onItemClick(View view, int viewType, T data, int parentPosition, int childPosition);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }
}
