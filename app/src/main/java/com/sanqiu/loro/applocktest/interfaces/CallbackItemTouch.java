package com.sanqiu.loro.applocktest.interfaces;

/**
 * 在ItemTouchHelper.Callback中监听的接口
 * Created by loro on 2018/4/9.
 */
public interface CallbackItemTouch {

    /**
     * Called when an item has been dragged
     * @param oldPosition start position
     * @param newPosition end position
     */
    void itemTouchOnMove(int oldPosition, int newPosition);
}
