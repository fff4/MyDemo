package com.example.administrator.mydemo.ui.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.administrator.mydemo.R;
import com.example.administrator.mydemo.adapter.IMFriendAdapter;
import com.example.administrator.mydemo.base.BaseActivity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatRoomActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.user_frind)
    RecyclerView mUserFrind;
    private IMFriendAdapter mAdapter;
    private List<String> mUsernames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        ButterKnife.bind(this);

        initToolbar();
        initRV();
        initDate();
    }

    private void initDate() {
        try {
            //获取所有好友的username
            mUsernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
            mAdapter.setDate(mUsernames);
        } catch (HyphenateException e) {
            e.printStackTrace();
            showToast("获取好友信息失败！");
            return;
        }
    }

    private void initRV() {
        mUserFrind.setHasFixedSize(true);
        mUserFrind.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new IMFriendAdapter(this);
        mUserFrind.setAdapter(mAdapter);

        mAdapter.setItemClick(new IMFriendAdapter.ItemClick() {
            @Override
            public void onItemClick(String username) {
                Intent intent = new Intent(ChatRoomActivity.this, SendMessageActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(mToolBar);
        ActionBar bar = getSupportActionBar();
        bar.setHomeButtonEnabled(true); //设置返回键可用
        bar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);//在菜单中找到对应控件的item
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(final String query) {
                showToast(query);

                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ChatRoomActivity.this);
                builder.setTitle("添加好友");
                builder.setMessage("是否添加‘"+query+"’为您的好友");
                builder.setNegativeButton("取消", null);
                final String username = query;
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //参数为要添加的好友的username和添加理由
                        try {
                            EMClient.getInstance().contactManager().addContact(username, null);
                            mUsernames.add(username);
                            mAdapter.setDate(mUsernames);
                            return;
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                            showToast("添加好友失败");
                        }
                    }
                });
                builder.show();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                showToast(newText);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
//            case R.id.action_scan:
//                //点击了添加好友
//
//                break;
//            case R.id.action_add:
//                showToast("点击了添加");
//                break;
            case R.id.action_item1:
                showToast("点击了菜单1");
                break;
            case R.id.action_item2:
                showToast("点击了菜单2");
                break;
            default:
                break;
        }
        return true;

    }
}
