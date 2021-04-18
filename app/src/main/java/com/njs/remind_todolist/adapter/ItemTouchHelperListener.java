package com.njs.remind_todolist.adapter;

public interface ItemTouchHelperListener {
    boolean onItemMove(int fromPosition, int toPosition);
    void onItemSwipe(int position);
}
