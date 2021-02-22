package com.rance.chatui.adapter.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rance.chatui.R;
import com.rance.chatui.adapter.ChatAdapter;
import com.rance.chatui.adapter.OnRefreshListener;

/**
 * Copyright (C), 2016-2021
 * Author: 超人迪加
 * Date: 2/22/21 10:03 AM
 */
public class ChatRefreshViewHolder extends BaseViewHolder {

    ProgressBar progressBar;

    TextView textView;

    private OnRefreshListener mListener;

    public ChatRefreshViewHolder(ViewGroup parent, OnRefreshListener listener) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_refresh, parent, false));
        this.mListener = listener;
        findViewByIds(itemView);
    }


    private void findViewByIds(View itemView) {
        progressBar = itemView.findViewById(R.id.chat_progress);
        textView = itemView.findViewById(R.id.chat_tip);
    }


    public void setState(int state) {
        progressBar.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
        switch (state) {
            case ChatAdapter.LOADING:
                progressBar.setVisibility(View.VISIBLE);
                break;
            case ChatAdapter.LOADING_COMPLETE:
                //加载完成不需要处理什么
                break;
            case ChatAdapter.LOADING_END:
                textView.setVisibility(View.VISIBLE);
                textView.setText("没有更多历史记录");
                textView.setOnClickListener(null);
                break;
            case ChatAdapter.LOADING_FAIL:
                textView.setVisibility(View.VISIBLE);
                textView.setText("加载失败，点我重试");
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.onRefresh();
                            setState(ChatAdapter.LOADING);
                        }
                    }
                });
                break;

        }
    }

}
