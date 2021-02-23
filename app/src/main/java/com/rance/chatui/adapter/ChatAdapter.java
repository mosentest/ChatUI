package com.rance.chatui.adapter;

import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rance.chatui.adapter.holder.BaseViewHolder;
import com.rance.chatui.adapter.holder.ChatAcceptViewHolder;
import com.rance.chatui.adapter.holder.ChatRefreshViewHolder;
import com.rance.chatui.adapter.holder.ChatSendViewHolder;
import com.rance.chatui.enity.MessageInfo;
import com.rance.chatui.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Rance on 2016/11/29 10:46
 * 邮箱：rance935@163.com
 */
public class ChatAdapter extends RecyclerView.Adapter<BaseViewHolder<MessageInfo>> {


    /**
     * 当前加载状态，默认为加载完成
     */
    public int loadState = 2;
    /**
     * 正在加载
     */
    public static final int LOADING = 1;
    /**
     * 加载完成
     */
    public static final int LOADING_COMPLETE = 2;
    /**
     * 加载到没有
     */
    public static final int LOADING_END = 3;
    /**
     * 加载失败
     */
    public static final int LOADING_FAIL = 4;

    /**
     * 标记是否正在向上滑动
     */
    boolean isSlidingDown = false;

    private RecyclerView mRecyclerView;
    private OnRefreshListener mOnRefreshListener;

    private onItemClickListener onItemClickListener;
    public Handler handler;
    private List<MessageInfo> messageInfoList;


    public ChatAdapter(List<MessageInfo> messageInfoList) {
        handler = new Handler();
        this.messageInfoList = messageInfoList;
    }

    public void setRefreshListener(OnRefreshListener listener) {
        this.mOnRefreshListener = listener;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        if (mRecyclerView != null) {
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager instanceof LinearLayoutManager) {
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            //当状态是不滑动的时候
                            int lastItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                            if (lastItemPosition == 1 && isSlidingDown) {
                                if (mOnRefreshListener != null
                                        && loadState != ChatAdapter.LOADING
                                        && loadState != ChatAdapter.LOADING_END) {
                                    setLoadState(ChatAdapter.LOADING);
                                    mOnRefreshListener.onRefresh();
                                }
                            }
                        }
                    }
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    // 大于0表示正在向上滑动，小于等于0表示停止或向下滑动
                    isSlidingDown = dy < 0;
                }
            });
        }
    }

    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }

    public void addItemClickListener(onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public BaseViewHolder<MessageInfo> onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = null;
        switch (viewType) {
            case Constants.CHAT_ITEM_TYPE_LEFT:
                viewHolder = new ChatAcceptViewHolder(parent, onItemClickListener, handler);
                break;
            case Constants.CHAT_ITEM_TYPE_RIGHT:
                viewHolder = new ChatSendViewHolder(parent, onItemClickListener, handler);
                break;
            case Constants.CHAT_ITEM_REFRESH:
                viewHolder = new ChatRefreshViewHolder(parent, mOnRefreshListener);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<MessageInfo> holder, int position) {
        if (holder instanceof ChatRefreshViewHolder) {
            ((ChatRefreshViewHolder) holder).setState(loadState);
        } else {
            holder.itemView.setTag(position - 1);
            holder.setData(messageInfoList.get(position - 1));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return Constants.CHAT_ITEM_REFRESH;
        } else {
            return messageInfoList.get(position - 1).getType();
        }
    }

    @Override
    public int getItemCount() {
        if (messageInfoList == null) {
            return 0;
        } else {
            return messageInfoList.size() + 1;
        }
    }

    public void addAll(List<MessageInfo> messageInfos) {
        if (messageInfoList == null) {
            messageInfoList = messageInfos;
        } else {
            messageInfoList.addAll(messageInfos);
        }
        notifyDataSetChanged();
    }

    public void setNewData(List<MessageInfo> messageInfos) {
        if (messageInfoList == null) {
            messageInfoList = messageInfos;
        } else {
            messageInfoList.clear();
            messageInfoList.addAll(messageInfos);
        }
        notifyDataSetChanged();
    }

    public void add(MessageInfo messageInfo) {
        if (messageInfoList == null) {
            messageInfoList = new ArrayList<>();
        }
        messageInfoList.add(messageInfo);
        notifyDataSetChanged();
    }

    public List<MessageInfo> getMessageInfoList() {
        return messageInfoList;
    }

    public interface onItemClickListener {
        void onHeaderClick(int position);

        void onImageClick(View view, int position);

        void onVoiceClick(ImageView imageView, int position);

        void onFileClick(View view, int position);

        void onLinkClick(View view, int position);

        void onLongClickImage(View view, int position);

        void onLongClickText(View view, int position);

        void onLongClickItem(View view, int position);

        void onLongClickFile(View view, int position);

        void onLongClickLink(View view, int position);
    }
}
