package com.njs.remind_todolist.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;

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


        binding.addTodoListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTodoList();
            }
        });
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
}