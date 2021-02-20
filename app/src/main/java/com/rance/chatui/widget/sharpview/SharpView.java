package com.rance.chatui.widget.sharpview;

public interface SharpView {

    SharpViewRenderProxy getRenderProxy();

    enum ArrowDirection {
        LEFT, TOP, RIGHT, BOTTOM
    }
}