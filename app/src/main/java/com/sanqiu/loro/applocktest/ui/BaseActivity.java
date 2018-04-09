package com.sanqiu.loro.applocktest.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sanqiu.loro.applocktest.R;

/**
 * Created by loro on 2018/4/8.
 */

public class BaseActivity extends FragmentActivity {
    protected Handler mBaseHandler;

    protected Toast mToast;
    protected View mView;

    protected TextView mTvTitleLeft;
    protected TextView mTvTitle;
    protected TextView mTvTitleRight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBaseHandler = new Handler(callback);
    }

    protected Boolean mProcessHandlerComplete = false;
    private Handler.Callback callback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                default:
                    break;
            }
            mProcessHandlerComplete = false;
            if (mProcessHandlerComplete == false) {
                if (msg != null)
                    mProcessHandlerComplete = processHandlerMessage(msg);
            }
            return true;
        }
    };

    /**
     * 處理除基礎消息之外的消息
     */
    protected Boolean processHandlerMessage(Message msg) {
        return false;
    }

    protected static void sendMsg(Handler hander, int what, Object obj) {
        Message hintMsg = new Message();
        hintMsg.what = what;
        hintMsg.obj = obj;
        hander.sendMessage(hintMsg);
    }

    protected void setBaseTitle(String left, String title, String right) {
        mTvTitleLeft = findViewById(R.id.title_tv_left);
        mTvTitle = findViewById(R.id.title_tv_title);
        mTvTitleRight = findViewById(R.id.title_tv_right);

        if (!TextUtils.isEmpty(left)) {
            mTvTitleLeft.setVisibility(View.VISIBLE);
            mTvTitleLeft.setText(left);
        } else {
            mTvTitleLeft.setVisibility(View.INVISIBLE);
        }

        if (!TextUtils.isEmpty(title)) {
            mTvTitle.setVisibility(View.VISIBLE);
            mTvTitle.setText(title);
        } else {
            mTvTitle.setVisibility(View.INVISIBLE);
        }

        if (!TextUtils.isEmpty(right)) {
            mTvTitleRight.setVisibility(View.VISIBLE);
            mTvTitleRight.setText(right);
        } else {
            mTvTitleRight.setVisibility(View.INVISIBLE);
        }
    }

    protected void showToast(int code) {
        if (code <= 0) {
            return;
        }
        showToast(getString(code));
    }

    protected void showToast(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }

        if (mToast == null) {
            if (mView == null) {
                mView = Toast.makeText(this, str, Toast.LENGTH_SHORT).getView();
            }
            mToast = new Toast(this);
            mToast.setView(mView);
        } else {
            mToast.setText(str);
        }
        mToast.show();
    }
}
