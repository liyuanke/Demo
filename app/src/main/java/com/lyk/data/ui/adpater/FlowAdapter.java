package com.lyk.data.ui.adpater;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lyk.data.R;
import com.lyk.data.model.FlowEntity;
import com.lyk.data.utils.ViewUtils;

import java.util.List;

import butterknife.BindView;

public class FlowAdapter extends DefaultAdapter<FlowEntity> {
    public FlowAdapter(List<FlowEntity> infos) {
        super(infos);
    }

    @Override
    public ViewHolder getHolder(View v, int viewType) {
        return new ViewHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.layout_flow_item;
    }

    static class ViewHolder extends BaseHolder<FlowEntity> {

        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.img_flag)
        ImageView imgFlag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void setData(FlowEntity data, int position) {
            if (data.isExistDecline()) {
                ViewUtils.visible(imgFlag);
                imgFlag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnViewClickListener.onViewClick(v, position, 2);
                    }
                });
            } else {
                ViewUtils.invisible(imgFlag);
            }
            tvContent.setText(String.format("%s年 总流量:%s", data.getYear(), data.getTotal()));
        }
    }
}
