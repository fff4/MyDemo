package com.example.administrator.mydemo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.mydemo.R;
import com.example.administrator.mydemo.base.BaseActivity;
import com.example.administrator.mydemo.utils.SPUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IMActivity extends BaseActivity {

    @BindView(R.id.user_name)
    EditText mUserName;
    @BindView(R.id.user_pwd)
    EditText mUserPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im);
        ButterKnife.bind(this);
    }

    public void login(View view) {
        final String name = mUserName.getText().toString().trim();
        final String pwd = mUserPwd.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "账号密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        final SPUtils sp = new SPUtils(this);
        final Map<String, String> userAll = sp.selUser();


       new Thread(new Runnable() {
           @Override
           public void run() {

               if (TextUtils.isEmpty(userAll.get(name)) || !userAll.get(name).equals(pwd)) { //不存在就注册

                   //注册失败会抛出HyphenateException
                   try {
                       showToast("注册中···！");
                       EMClient.getInstance().createAccount(name, pwd);//同步方法
                       showToast("注册成功");
                       sp.saveUser(name, pwd);
                   } catch (Exception e) {
                       e.printStackTrace();
                       showToast("注册失败");
                   }
               }

               EMClient.getInstance().login(name, pwd, new EMCallBack() {//回调

                   @Override
                   public void onSuccess() {

                       EMClient.getInstance().groupManager().loadAllGroups();
                       EMClient.getInstance().chatManager().loadAllConversations();
                       Log.d("main", "登录聊天服务器成功！");
                       showToast("登录聊天服务器成功！");
                       Intent intent = new Intent(IMActivity.this, ChatRoomActivity.class);
                       startActivity(intent);
                   }

                   @Override
                   public void onProgress(int progress, String status) {

                   }

                   @Override
                   public void onError(int code, String message) {
                       Log.d("main", "登录聊天服务器失败:"+message);
                       showToast("登录聊天服务器失败！");
                   }
               });
           }
       }).start();

        //注册一个监听连接状态的listener
        EMClient.getInstance().addConnectionListener(new MyConnectionListener());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //先取消登录
        EMClient.getInstance().logout(true);
    }
}
