package com.rance.chatui.widget.sharpview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

//import com.rey.material.widget.EditText;

/**
 * Created by 曾宪梓 on 2017/12/28.
 */

public class SharpEditText extends EditText implements SharpView{

    public SharpEditText(Context context) {
        super(context);
        init(context,null,0);
    }

    public SharpEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs,0);
    }

    public SharpEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr){
        mSharpViewRenderProxy = new SharpViewRenderProxy(this, context, attrs, defStyleAttr);
    }

    private SharpViewRenderProxy mSharpViewRenderProxy;

    @Override
    public SharpViewRenderProxy getRenderProxy() {
        return mSharpViewRenderProxy;
    }
}