package com.rance.chatui.util;

import com.rance.chatui.BuildConfig;

import java.io.File;

/**
 * 作者：Rance on 2016/12/20 16:51
 * 邮箱：rance935@163.com
 */
public class Constants {
    public static final String TAG = "rance";

    public static final String AUTHORITY = "rance.chatui.ChatProvider";

    /**
     * 录音存储路径
     */
    public static final String PATH_RECORD = File.separator + "sdmp" + File.separator + "record";
    /**
     * 拍照的路径
     */
    public static final String PATH_PHOTO = File.separator + "sdmp" + File.separator + "photo";

    /**
     * 0x001-接受消息  0x002-发送消息
     **/
    public static final int CHAT_ITEM_TYPE_LEFT = 0x001;
    public static final int CHAT_ITEM_TYPE_RIGHT = 0x002;
    public static final int CHAT_ITEM_REFRESH = 0x003;
    /**
     * 0x003-发送中  0x004-发送失败  0x005-发送成功
     **/
    public static final int CHAT_ITEM_SENDING = 0x003;
    public static final int CHAT_ITEM_SEND_ERROR = 0x004;
    public static final int CHAT_ITEM_SEND_SUCCESS = 0x005;

    public static final String CHAT_FILE_TYPE_TEXT = "text";
    public static final String CHAT_FILE_TYPE_FILE = "file";
    public static final String CHAT_FILE_TYPE_IMAGE = "image";
    public static final String CHAT_FILE_TYPE_VOICE = "voice";
    public static final String CHAT_FILE_TYPE_CONTACT = "contact";
    public static final String CHAT_FILE_TYPE_LINK = "LINK";
}
