package com.njs.remind_todolist.adapter;

import android.view.View;

import com.njs.remind_todolist.model.ToDoList;

public interface OnTodoItemEventListener {

    void onItemClick(ToDoList toDoList);

    void onItemSwipe(ToDoList toDoList);

    void onItemMove(int fromId, int toId);
}
