package com.sanqiu.loro.applocktest.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.sanqiu.loro.applocktest.interfaces.CallbackItemTouch;


/**
 * Created by loro on 2018/4/9.
 */
public class RateItemTouchHelperCallback extends ItemTouchHelper.Callback {

    CallbackItemTouch callbackItemTouch; // interface

    public RateItemTouchHelperCallback(CallbackItemTouch callbackItemTouch){
        this.callbackItemTouch = callbackItemTouch;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false; // swiped disabled
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN; // movements drag
        return makeFlag( ItemTouchHelper.ACTION_STATE_DRAG , dragFlags); // as parameter, action drag and flags drag
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        callbackItemTouch.itemTouchOnMove(viewHolder.getAdapterPosition(),target.getAdapterPosition()); // information to the interface
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        // swiped disabled
    }
}
