package com.example.administrator.mydemo.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.mydemo.R;
import com.example.administrator.mydemo.adapter.IMMsgAdapter;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.offset;

public class SendMessageActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.msg_toolbar)
    Toolbar mMsgToolbar;
    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    @BindView(R.id.et)
    EditText mEt;
    @BindView(R.id.tvSend)
    TextView mTvSend;
    private IMMsgAdapter mImMsgAdapter;
    private EMMessageListener mMsgListener;
    private List<EMMessage> mMessages;
    private String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        ButterKnife.bind(this);

        initRV();
        initDate();
        initMsgListener();
        initSend();
    }

    private void initSend() {
        mTvSend.setOnClickListener(this);
    }


    private void initDate() {

        //获取聊天信息
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(mUsername);
        if (conversation == null) {
            return;
        }
        //获取此会话的所有消息
        mMessages = conversation.getAllMessages();

        if (mMessages == null) {
            return;
        }
        mImMsgAdapter.setMessages(mMessages);
        mRecylerView.smoothScrollToPosition(offset);
    }

    private void initRV() {
        mUsername = getIntent().getStringExtra("username");
        //固定布局
        mRecylerView.setHasFixedSize(true);
        //设置rv的布局
        mRecylerView.setLayoutManager(new LinearLayoutManager(this));
        mImMsgAdapter = new IMMsgAdapter(this, mUsername);
        mRecylerView.setAdapter(mImMsgAdapter);
    }

    private void initMsgListener() {

        //收到消息
        //收到透传消息
        //收到已读回执
        //收到已送达回执
        //消息状态变动
        mMsgListener = new EMMessageListener() {

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                EMMessage emMessage = messages.get(messages.size() - 1);
                //收到消息
                mImMsgAdapter.addMessage(emMessage);
                mRecylerView.smoothScrollToPosition(offset);
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                //收到透传消息
            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {
                //收到已读回执
            }

            @Override
            public void onMessageDelivered(List<EMMessage> message) {
                //收到已送达回执
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                //消息状态变动
            }
        };

        EMClient.getInstance().chatManager().addMessageListener(mMsgListener);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //记得在不需要的时候移除listener，如在activity的onDestroy()时
        EMClient.getInstance().chatManager().removeMessageListener(mMsgListener);
    }

    @Override
    public void onClick(View v) {
        String msg = mEt.getText().toString().trim();
        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        EMMessage message = EMMessage.createTxtSendMessage(msg, mUsername);
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);
        mImMsgAdapter.addMessage(message);
        mRecylerView.smoothScrollToPosition(offset);
        mEt.setText(null);
    }
}
