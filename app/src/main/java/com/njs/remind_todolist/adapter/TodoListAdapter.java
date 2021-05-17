package com.njs.remind_todolist.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.njs.remind_todolist.R;
import com.njs.remind_todolist.databinding.ItemTodolistBinding;
import com.njs.remind_todolist.model.SettingValue;
import com.njs.remind_todolist.model.ToDoList;

import java.util.List;


public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ToDoListViewHolder> implements ItemTouchHelperListener {

    private List<ToDoList> toDoLists;
    private OnTodoItemEventListener listener;
    public boolean isDrag;


    public void setData(List<ToDoList> toDoLists) {
        this.toDoLists = toDoLists;
    }

    public void setOnItemClickListener(OnTodoItemEventListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ToDoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemTodolistBinding itemTodolistBinding = ItemTodolistBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        ToDoListViewHolder toDoListViewHolder = new ToDoListViewHolder(itemTodolistBinding);
        return toDoListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoListViewHolder holder, int position) {
        ToDoList toDoList = toDoLists.get(position);
        holder.bind(toDoList);
    }

    @Override
    public int getItemCount() {
        return toDoLists != null ? toDoLists.size() : 0;
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        isDrag = true;
        int fromId = toDoLists.get(fromPosition).getId();
        int toId = toDoLists.get(toPosition).getId();
        notifyItemMoved(fromPosition, toPosition);
        listener.onItemMove(fromId, toId);
        return true;
    }

    @Override
    public void onItemSwipe(int position) {
        listener.onItemSwipe(toDoLists.get(position));
    }

    public class ToDoListViewHolder extends RecyclerView.ViewHolder {

        private ItemTodolistBinding itemTodolistBinding;

        public ToDoListViewHolder(@NonNull ItemTodolistBinding itemTodolistBinding) {
            super(itemTodolistBinding.getRoot());
            this.itemTodolistBinding = itemTodolistBinding;

            itemTodolistBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onItemClick(toDoLists.get(getAdapterPosition()));

                        Log.i("todoList_getID", String.valueOf(toDoLists.get(getAdapterPosition()).getId()));
                    }
                }
            });
            if (SettingValue.getBackgroundValue().equalsIgnoreCase("white")) {
                itemTodolistBinding.todoListTv.setTextColor(ContextCompat.getColor(itemTodolistBinding.getRoot().getContext(), R.color.black));
            }

        }

        public void bind(ToDoList toDoList) {
            itemTodolistBinding.setTodoLists(toDoList);
        }
    }
}
