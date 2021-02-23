package com.rance.chatui.base;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.DisplayMetrics;

import com.rance.chatui.dao.DaoMaster;
import com.rance.chatui.dao.DaoSession;

/**
 * 作者：Rance on 2016/12/20 16:49
 * 邮箱：rance935@163.com
 */
public class ImApplication extends Application {

    private static ImApplication mInstance;

    /**
     * 屏幕宽度
     */
    public static int screenWidth;
    /**
     * 屏幕高度
     */
    public static int screenHeight;
    /**
     * 屏幕密度
     */
    public static float screenDensity;

    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initScreenSize();
        setDatabase();
    }

    public static ImApplication getInstance() {
        return mInstance;
    }

    /**
     * 进行greenDao的初始化
     */
    private void setDatabase() {
        DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(this, "db_im_chat.db", null);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        DaoMaster mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    /**
     * 初始化当前设备屏幕宽高
     */
    private void initScreenSize() {
        DisplayMetrics curMetrics = getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = curMetrics.widthPixels;
        screenHeight = curMetrics.heightPixels;
        screenDensity = curMetrics.density;
    }
}
