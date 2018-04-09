package com.sanqiu.loro.applocktest.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.sanqiu.loro.applocktest.R;
import com.sanqiu.loro.applocktest.app.ThreadManager;
import com.sanqiu.loro.applocktest.utils.ConnectionUtil;
import com.sanqiu.loro.applocktest.utils.RegularUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by loro on 2018/4/8.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    public final static int BASE_MSG_RESOLOVE_COMPLETE = 200;
    public final static String TAG = "MainActivity";

    protected ImageView mIvLogo;
    protected Button mBtnResolve;
    protected EditText mEtInputWeb;

    public static void goMainActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTitle();
        initViews();
        initEvents();


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        BitmapDrawable drawable = (BitmapDrawable) mIvLogo.getDrawable();
        if (null != drawable) {
            Bitmap bitmap = drawable.getBitmap();
            if (null != bitmap && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }

    private void initTitle() {
        setBaseTitle(getString(R.string.list_title_title_text), getString(R.string.main_title_title_text), "");
        mTvTitleLeft.setOnClickListener(this);
    }

    private void initViews() {
        mEtInputWeb = findViewById(R.id.main_et_input_web);
        mBtnResolve = findViewById(R.id.main_btn_resolve);
        mIvLogo = findViewById(R.id.main_iv_logo);
    }

    private void initEvents() {
        mBtnResolve.setOnClickListener(this);
    }

    @Override
    protected Boolean processHandlerMessage(Message msg) {
        super.processHandlerMessage(msg);
        switch (msg.what) {
            case BASE_MSG_RESOLOVE_COMPLETE:
                Bitmap bitmap = (Bitmap) msg.obj;
                if (null != bitmap) {
                    mIvLogo.setImageBitmap(bitmap);
                } else {
                    showToast(R.string.main_toast_resolve_error);
                }
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_tv_left:
                ListActivity.goListActivity(this);
                break;
            case R.id.main_btn_resolve:
//                resolveNow2();
                resolveNow();
                break;
        }
    }

    private void resolveNow2() {
        final String web = mEtInputWeb.getText().toString();
        ThreadManager.executeOnNetWorkThread(new Runnable() {
            @Override
            public void run() {
                Document doc = null;
                try {
                    doc = Jsoup.connect(web).post();

                    Log.d(TAG, "resolveNow2-start-time：" + System.currentTimeMillis());

                    Elements elements = doc.select("head");
                    elements = elements.select("link");

                    for (Element element : elements) {
                        if (element.attr("rel").indexOf("apple-touch-icon") != -1) {
                            String path = element.attr("href");
                            if (path.indexOf("//") == 0 && path.indexOf("http") == -1) {
                                path = "http:" + path;
                            }
                            Bitmap bitmap = ConnectionUtil.getURLimage(path);
                            Log.d(TAG, "resolveNow2-start-end：" + System.currentTimeMillis());

                            sendMsg(mBaseHandler, BASE_MSG_RESOLOVE_COMPLETE, bitmap);
                            break;
                        }
                    }

                    Log.i(TAG, elements.attr("href"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void resolveNow() {
        final String web = mEtInputWeb.getText().toString();
        if (!checkInput(web)) {
            return;
        }
        ThreadManager.executeOnNetWorkThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "connection-start-time：" + System.currentTimeMillis());
                String json = ConnectionUtil.getContextByHttp(web);
                Log.d(TAG, "connection-end-time：" + System.currentTimeMillis());

                Log.d(TAG, "regex-start-time：" + System.currentTimeMillis());
                json = RegularUtil.getHtmlIcon(json);
                Log.d(TAG, "regex-start-end：" + System.currentTimeMillis());

                if (TextUtils.isEmpty(json)) {
                    sendMsg(mBaseHandler, BASE_MSG_RESOLOVE_COMPLETE, null);
                    return;
                }

                Log.d(TAG, "bitmap-start-time：" + System.currentTimeMillis());
                Bitmap bitmap = ConnectionUtil.getURLimage(json);
                Log.d(TAG, "bitmap-start-end：" + System.currentTimeMillis());

                sendMsg(mBaseHandler, BASE_MSG_RESOLOVE_COMPLETE, bitmap);

            }
        });

    }

    private boolean checkInput(String web) {
        if (TextUtils.isEmpty(web)) {
            showToast(R.string.main_toast_web_null);
            return false;
        }

        if (!RegularUtil.isWebPattern(web)) {
            showToast(R.string.main_toast_web_check_error);
            return false;
        }

        return true;
    }

}
