package com.sanqiu.loro.applocktest.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.sanqiu.loro.applocktest.R;
import com.sanqiu.loro.applocktest.interfaces.IChangeText;
import com.sanqiu.loro.applocktest.model.Rate;

import java.util.List;


/**
 * Created by loro on 2018/4/9.
 */
public class RateAdapterRecyclerView extends RecyclerView.Adapter<RateAdapterRecyclerView.MyViewHolder> {

    private List<Rate> mList;
    private String select = "";
    private Context context;
    private IChangeText changeText;
    private View.OnClickListener onClickListener;

    public RateAdapterRecyclerView(Context context, List<Rate> mList, IChangeText changeText, View.OnClickListener onClickListener) {
        this.mList = mList;
        this.context = context;
        this.changeText = changeText;
        this.onClickListener = onClickListener;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_rate, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Rate item = mList.get(position); // Object Item
        holder.setName(item.getRate_name()); // Name
        holder.setNum(item.getRate_exchange()); // Description
        holder.etNum.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        if (select.equals(item.getRate_name())) {
            holder.etNum.setTextColor(context.getResources().getColor(R.color.common_red));
            holder.tvName.setTextColor(context.getResources().getColor(R.color.common_red));
        } else {
            holder.etNum.setTextColor(context.getResources().getColor(R.color.common_text_gray));
            holder.tvName.setTextColor(context.getResources().getColor(R.color.common_text_gray));
        }

        holder.etNum.setTag(item);
//        holder.etNum.addTextChangedListener(new MyTextWatch(changeText, holder.etNum));
        holder.etNum.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    if (null != changeText) {
                        changeText.onChange(textView);
                    }
                }
                return false;
            }
        });

        holder.clLayout.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        } else {
            return mList.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, etNum;
        ConstraintLayout clLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.item_rate_tv_name);
            etNum = itemView.findViewById(R.id.item_rate_et_num);
            clLayout = itemView.findViewById(R.id.item_rate_layout);
        }

        public void setName(String name) {
            tvName.setText(name);
        }

        public void setNum(float num) {
            etNum.setText(num + "");
        }


    }

}
