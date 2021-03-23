package com.njs.remind_todolist.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.njs.remind_todolist.R;
import com.njs.remind_todolist.adapter.TodoListAdapter;
import com.njs.remind_todolist.databinding.ActivityMainBinding;
import com.njs.remind_todolist.model.ToDoList;
import com.njs.remind_todolist.viewmodel.ToDoListViewModel;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ToDoListViewModel viewModel;
    private TodoListAdapter todoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(ToDoListViewModel.class);
        viewModel.init(getApplication());
        todoListAdapter = new TodoListAdapter();
        binding.setViewModel(viewModel);
        binding.todoListRecyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.todoListRecyclerview.setAdapter(todoListAdapter);

        setItemTouchHelper();
        binding.addTodoListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTodoList();
            }
        });
    }

    private void setItemTouchHelper() {

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                showDeleteDialog(viewHolder);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.todoListRecyclerview);
    }

    private void addTodoList(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View dialogView = getLayoutInflater().inflate(R.layout.add_todo_dialog , null);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        Button addTodoListBtn = dialogView.findViewById(R.id.add_todoList_btn2);

        addTodoListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = dialogView.findViewById(R.id.todoList_editTv);

                if(editText.getText().length() <= 0) {
                    Toast.makeText(getApplicationContext(),"할 일을 입력 해주세요." , Toast.LENGTH_SHORT).show();
                }
                else {
                    ToDoList toDoList = new ToDoList();
                    toDoList.setTodoList(String.valueOf(editText.getText()));

                    viewModel.insertTodoList(toDoList);

                    alertDialog.dismiss();
                }
            }
        });

        alertDialog.show();
    }

    private void showDeleteDialog(RecyclerView.ViewHolder viewHolder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this )
                .setTitle("할일 삭제")
                .setMessage("삭제하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        viewModel.deleteTodoList(viewHolder.getLayoutPosition());
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        todoListAdapter.notifyDataSetChanged();
                    }
                });
        AlertDialog deleteDialog = builder.create();
        builder.show();
    }
}