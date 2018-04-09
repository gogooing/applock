package com.sanqiu.loro.applocktest.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.sanqiu.loro.applocktest.R;
import com.sanqiu.loro.applocktest.adapter.RateAdapterRecyclerView;
import com.sanqiu.loro.applocktest.adapter.RateItemTouchHelperCallback;
import com.sanqiu.loro.applocktest.app.ThreadManager;
import com.sanqiu.loro.applocktest.interfaces.CallbackItemTouch;
import com.sanqiu.loro.applocktest.interfaces.IChangeText;
import com.sanqiu.loro.applocktest.model.Rate;
import com.sanqiu.loro.applocktest.model.RateBase;
import com.sanqiu.loro.applocktest.utils.ConnectionUtil;
import com.sanqiu.loro.applocktest.utils.SharePrefUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by loro on 2018/4/8.
 */

public class ListActivity extends BaseActivity implements CallbackItemTouch, View.OnClickListener, IChangeText {
    private final static String API_PATG = "https://api.fixer.io/latest";
    private final static String SHAREPRE_KEY_CACHE_RATE = "key_rate";
    public final static int BASE_MSG_GETDATA_COMPLETE = 200;

    protected RecyclerView mRecyclerView;
    protected List<Rate> mList;
    protected RateAdapterRecyclerView mAdapter; //The Adapter for RecyclerVIew

    protected String baseCache = "EUR";
    protected String amountCache = "1";
    protected Rate nowRate;

    public static void goListActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);

        String nowRateJson = SharePrefUtil.getString(SHAREPRE_KEY_CACHE_RATE, "");//默认为EUR

        if (!TextUtils.isEmpty(nowRateJson)) {
            nowRate = JSON.parseObject(nowRateJson, Rate.class);
            baseCache = nowRate.getRate_name();
            amountCache = String.valueOf(nowRate.getRate_exchange());
        } else {
            //因为请求返回的json不会带回请求的币种，需要自己添加
            nowRate = new Rate();
            nowRate.setRate_name(baseCache);
            nowRate.setRate_exchange(Float.parseFloat(amountCache));
            nowRate.setRate_index(0);
        }

        initTitle();
        initViews();
        initEvents();
        initList();

        getDataByAPI();

    }

    private void initTitle() {
        setBaseTitle(getString(R.string.main_title_title_text), getString(R.string.list_title_title_text), getString(R.string.list_title_right_text));
        mTvTitleLeft.setOnClickListener(this);
        mTvTitleRight.setOnClickListener(this);
    }

    private void initViews() {
        mRecyclerView = findViewById(R.id.list_rl_recycler);
    }

    private void initEvents() {

    }

    private void initList() {
        mList = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RateAdapterRecyclerView(this, mList, this, this);
        mRecyclerView.setAdapter(mAdapter);
        ItemTouchHelper.Callback callback = new RateItemTouchHelperCallback(this);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRecyclerView);
    }

    /**
     * 从网络获取数据
     */
    private void getDataByAPI() {
        ThreadManager.executeOnNetWorkThread(new Runnable() {
            @Override
            public void run() {
                try {
                    String json = ConnectionUtil.getContextByHttpJson(getApiPath());
                    if (!TextUtils.isEmpty(json)) {
                        RateBase rateBase = JSON.parseObject(json, RateBase.class);
                        if (null != rateBase) {
                            sendMsg(mBaseHandler, BASE_MSG_GETDATA_COMPLETE, rateBase);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    sendMsg(mBaseHandler, BASE_MSG_GETDATA_COMPLETE, null);
                }
            }
        });
    }

    private String getApiPath() {
        StringBuffer sb = new StringBuffer(API_PATG);
        if (!TextUtils.isEmpty(baseCache)) {
            sb.append("?base=" + baseCache);
        }
        if (!TextUtils.isEmpty(amountCache)) {
            sb.append("&amount=" + amountCache);
        }
        return sb.toString();
    }

    @Override
    public void itemTouchOnMove(int oldPosition, int newPosition) {
        mList.add(newPosition, mList.remove(oldPosition));
        Rate.chageOrderAndSave(mList);
        mAdapter.notifyItemMoved(oldPosition, newPosition);
    }

    @Override
    protected Boolean processHandlerMessage(Message msg) {
        super.processHandlerMessage(msg);
        switch (msg.what) {
            case BASE_MSG_GETDATA_COMPLETE:
                RateBase rateBase = (RateBase) msg.obj;
                if (null != rateBase) {
                    mTvTitle.setText(getString(R.string.list_title_title_text_name, rateBase.getBase()));
                    List<Rate> list = Rate.getListByMapOrder(rateBase.getRates(), nowRate);
                    mList.clear();
                    mList.addAll(list);
                    mAdapter.setSelect(rateBase.getBase());
                    mAdapter.notifyDataSetChanged();
                    showToast(R.string.main_toast_get_data_api_ok);
                } else {
                    showToast(R.string.main_toast_get_data_api_error);
                }
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_tv_left:
                MainActivity.goMainActivity(this);
                break;
            case R.id.title_tv_right:
                getDataByAPI();
                break;
        }
    }

    @Override
    public void onChange(View view) {
        if (null != view) {

            Rate rate = (Rate) view.getTag();
            baseCache = rate.getRate_name();
            amountCache = ((TextView) view).getText().toString();
            nowRate = rate;
            nowRate.setRate_name(baseCache);
            nowRate.setRate_exchange(Float.parseFloat(amountCache));

            SharePrefUtil.putString(SHAREPRE_KEY_CACHE_RATE, JSON.toJSONString(nowRate));

            getDataByAPI();

        }
    }
}
