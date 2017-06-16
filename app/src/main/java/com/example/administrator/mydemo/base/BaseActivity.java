package com.example.administrator.mydemo.base;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.util.NetUtils;

/**
 * Created by Administrator on 2017/6/8.
 */

public class BaseActivity extends AppCompatActivity {
    private Handler mHandler = new Handler();

    public void showToast(final String text) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //实现ConnectionListener接口--监听
    public class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
        }

        @Override
        public void onDisconnected(final int error) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (error == EMError.USER_REMOVED) {
                        // 显示帐号已经被移除
                    } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        // 显示帐号在其他设备登录
                    } else {
                        if (NetUtils.hasNetwork(BaseActivity.this)) {
                            //连接不到聊天服务器
                        } else{
                            //当前网络不可用，请检查网络设置

                        }
                    }
                }
            });
        }
    }
}
