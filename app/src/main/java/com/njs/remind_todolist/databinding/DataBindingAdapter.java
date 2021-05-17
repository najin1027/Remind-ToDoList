package com.njs.remind_todolist.databinding;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.njs.remind_todolist.adapter.TodoListAdapter;
import com.njs.remind_todolist.model.ToDoList;

import java.util.List;

public class DataBindingAdapter {

    @BindingAdapter("bind_todoList")
    public static void bindTodoListItem(RecyclerView recyclerView, List<ToDoList> toDoLists) {
        TodoListAdapter todoListAdapter = (TodoListAdapter) recyclerView.getAdapter();

        if (todoListAdapter != null) {
            todoListAdapter.setData(toDoLists);
            if (!todoListAdapter.isDrag)
                todoListAdapter.notifyDataSetChanged();
        }
    }
}
