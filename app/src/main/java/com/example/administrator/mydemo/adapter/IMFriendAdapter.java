package com.example.administrator.mydemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.mydemo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/11.
 */

public class IMFriendAdapter extends RecyclerView.Adapter<IMFriendAdapter.ViewHolder> {

    private Context mContext;
    private List<String> mListData;
    private ItemClick mItemClick;

    public IMFriendAdapter(Context context) {
        mContext = context;
    }

    public void setDate(List<String> mData) {
        mListData = mData;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_user_name, null);
        ViewHolder hold = new ViewHolder(inflate);
        return hold;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindView(mListData.get(position), position);
    }

    @Override
    public int getItemCount() {
        if (mListData != null) {
            return mListData.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_tv_username)
        TextView mItemTvUsername;
        private String mText;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClick.onItemClick(mText);
                }
            });
        }

        public void bindView(String text, int position) {
            mText = text;
            mItemTvUsername.setText(text);
        }

    }

    public interface ItemClick {
        public void onItemClick(String username);
    }

    public void setItemClick(ItemClick itemClick) {
        mItemClick = itemClick;
    }

}
