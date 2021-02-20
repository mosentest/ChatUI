package com.rance.chatui.util;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.rance.chatui.adapter.EmotionGridViewAdapter;

/**
 * 作者：Rance on 2016/11/29 10:47
 * 邮箱：rance935@163.com
 * 点击表情的全局监听管理类
 */
public class GlobalOnItemClickManagerUtils implements LifecycleObserver {

    private static volatile GlobalOnItemClickManagerUtils instance;
    private static Context mContext;
    @Nullable
    private EditText mEditText;//输入框
    private Lifecycle mLifecycle;

    public static GlobalOnItemClickManagerUtils getInstance(Context context) {
        mContext = context.getApplicationContext();
        if (instance == null) {
            synchronized (GlobalOnItemClickManagerUtils.class) {
                if (instance == null) {
                    instance = new GlobalOnItemClickManagerUtils();
                }
            }
        }
        return instance;
    }

    public void attachToEditText(Lifecycle lifecycle, EditText editText) {
        this.mLifecycle = lifecycle;
        mLifecycle.addObserver(this);
        mEditText = editText;
    }

    public AdapterView.OnItemClickListener getOnItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object itemAdapter = parent.getAdapter();

                if (itemAdapter instanceof EmotionGridViewAdapter) {
                    // 点击的是表情
                    EmotionGridViewAdapter emotionGvAdapter = (EmotionGridViewAdapter) itemAdapter;

                    if (position == emotionGvAdapter.getCount() - 1) {
                        // 如果点击了最后一个回退按钮,则调用删除键事件
                        if (mEditText != null) {
                            mEditText.dispatchKeyEvent(new KeyEvent(
                                    KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                        }
                    } else {
                        // 如果点击了表情,则添加到输入框中
                        String emotionName = emotionGvAdapter.getItem(position);

                        // 获取当前光标位置,在指定位置上添加表情图片文本
                        int curPosition = 0;
                        if (mEditText != null) {
                            curPosition = mEditText.getSelectionStart();
                        }
                        StringBuilder sb = null;
                        if (mEditText != null) {
                            sb = new StringBuilder(mEditText.getText().toString());
                        }
                        if (sb != null) {
                            sb.insert(curPosition, emotionName);
                        }

                        // 特殊文字处理,将表情等转换一下
                        if (mEditText != null) {
                            mEditText.setText(Utils.getEmotionContent(mContext, mEditText, sb.toString()));
                        }

                        // 将光标设置到新增完表情的右侧
                        if (mEditText != null) {
                            mEditText.setSelection(curPosition + emotionName.length());
                        }
                    }

                }
            }
        };
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void destroy() {
        mEditText = null;
        mLifecycle.removeObserver(this);
        mLifecycle = null;
    }
}
