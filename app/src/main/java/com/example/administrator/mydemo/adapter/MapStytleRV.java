package com.example.administrator.mydemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.mydemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/7.
 */

public class MapStytleRV extends RecyclerView.Adapter<MapStytleRV.ViewHolder> {

    private Context mContext;
    private List<String> mList;
    private SelectItem mSelectItem;

    public MapStytleRV(Context context) {
        mContext = context;
        mList = new ArrayList<>();
        mList.add("典型道路地图");
        mList.add("卫星图(全)");
        mList.add("卫星图（不全）");
        mList.add("地形数据");
        mList.add("无图块");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = View.inflate(mContext, R.layout.item_map_style, null);
        parent.removeView(inflate);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindView(position, mList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_style_tv)
        TextView mItemStyleTv;
        private int mPosition;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectItem.onItemClick(mPosition);
                }
            });
        }

        public void bindView(int position, String values) {
            mPosition = position;
            mItemStyleTv.setText(mList.get(position));
        }
    }

    public interface SelectItem{
        public void onItemClick(int item);
    }

    public void setOnItemClick(SelectItem selectItem) {
        mSelectItem = selectItem;
    }
}
