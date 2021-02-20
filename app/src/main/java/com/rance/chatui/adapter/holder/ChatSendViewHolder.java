package com.rance.chatui.adapter.holder;

import android.os.Handler;

import android.view.View;
import android.view.ViewGroup;

import com.rance.chatui.R;
import com.rance.chatui.adapter.ChatAdapter;

/**
 * 作者：Rance on 2016/11/29 10:47
 * 邮箱：rance935@163.com
 */
public class ChatSendViewHolder extends ChatBaseViewHolder {
    private static final String TAG = "ChatSendViewHolder";

    public ChatSendViewHolder(ViewGroup parent, ChatAdapter.onItemClickListener onItemClickListener, Handler handler) {
        super(parent, R.layout.item_chat_send, onItemClickListener, handler);
    }

    @Override
    void findViewByItemView(View itemView) {
        chatItemFail = itemView.findViewById(R.id.chat_item_fail);
        chatItemProgress = itemView.findViewById(R.id.chat_item_progress);
    }

}
