package com.example.administrator.mydemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.mydemo.R;
import com.example.administrator.mydemo.utils.SPUtils;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 显示聊天消息的adaper
 */

public class IMMsgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final String mFriend;
    private Context mContext;
    private static final int TYPE_FRIEND = 0;
    private static final int TYPE_MY = 1;

    public IMMsgAdapter(Context context,  String friend) {
        mContext = context;
        mFriend = friend;
    }

    private List<EMMessage> mMessages = new ArrayList<>();

    public void setMessages(List<EMMessage> messages) {
        mMessages = messages;
        notifyDataSetChanged();
    }

    public void addMessage(EMMessage message) {
        mMessages.add(message);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FRIEND) {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_msg_friend, parent, false);
            FriendHolder friendHolder = new FriendHolder(inflate);
            return friendHolder;
        } else {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_msg_my, parent, false);
            MyHolder myHolder = new MyHolder(inflate);
            return myHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        EMMessage emMessage = mMessages.get(position);
        if (getItemViewType(position) == TYPE_MY) {
            MyHolder myHolder = (MyHolder) holder;
            myHolder.bindView(emMessage);
        } else if (getItemViewType(position) == TYPE_FRIEND) {
            FriendHolder friendHolder = (FriendHolder) holder;
            friendHolder.bindView(emMessage);
        }
    }

    @Override
    public int getItemCount() {
        if (mMessages != null) {
            return mMessages.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {

        EMMessage emMessage = mMessages.get(position);
        String userName = emMessage.getFrom();

        if (TextUtils.isEmpty(userName)) {
            return 0;
        }

        SPUtils spUtils = new SPUtils(mContext);
        if (mFriend.equals(userName)) {
            return TYPE_FRIEND;
        }else {
            return TYPE_MY;
        }
    }

    class FriendHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.msg_friend_iv)
        ImageView mMsgFriendIv;
        @BindView(R.id.msg_friend_tv)
        TextView mMsgFriendTv;

        FriendHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(EMMessage emMessage) {
            EMMessageBody body = emMessage.getBody();
            String text = body.toString();
            mMsgFriendTv.setText(text.substring(5, text.length() - 1));
        }

    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.msg_friend_tv)
        TextView mMsgFriendTv;
        @BindView(R.id.msg_friend_iv)
        ImageView mMsgFriendIv;

        MyHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(EMMessage emMessage) {
            EMMessageBody body = emMessage.getBody();
            String text = body.toString();
            mMsgFriendTv.setText(text.substring(5, text.length() - 1));
        }
    }
}
