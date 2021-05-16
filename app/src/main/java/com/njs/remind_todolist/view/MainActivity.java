package com.njs.remind_todolist.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.njs.remind_todolist.R;
import com.njs.remind_todolist.adapter.ItemTouchHelperCallback;
import com.njs.remind_todolist.adapter.OnTodoItemEventListener;
import com.njs.remind_todolist.adapter.TodoListAdapter;
import com.njs.remind_todolist.databinding.ActivityMainBinding;
import com.njs.remind_todolist.model.SettingValue;
import com.njs.remind_todolist.model.ToDoList;
import com.njs.remind_todolist.viewmodel.ToDoListViewModel;

import java.util.List;

import static com.njs.remind_todolist.model.SettingValue.sharedPreferences;

public class MainActivity extends AppCompatActivity implements PermissionListener {
    private ActivityMainBinding binding;
    private ToDoListViewModel viewModel;
    private TodoListAdapter todoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setDefaultSettings();

        binding.settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });

        binding.addTodoListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTodoList();
            }
        });

        todoListAdapter.setOnItemClickListener(new OnTodoItemEventListener() {
            @Override
            public void onItemClick(ToDoList toDoList) {
                updateTodoList(toDoList);
            }

            @Override
            public void onItemSwipe(ToDoList toDoList) {
                deleteTodoList(toDoList);
            }

            @Override
            public void onItemMove(int fromId, int toId) {
                viewModel.updateTodoListId(fromId, -1);
                viewModel.updateTodoListId(toId, fromId);
                viewModel.updateTodoListId(-1, toId);
            }
        });

    }

    @SuppressLint("CommitPrefEdits")
    private void init() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SettingValue.spEditor = sharedPreferences.edit();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(ToDoListViewModel.class);
        viewModel.init(getApplication());
        todoListAdapter = new TodoListAdapter();
        binding.setViewModel(viewModel);
        binding.todoListRecyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.todoListRecyclerview.setAdapter(todoListAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(todoListAdapter));
        itemTouchHelper.attachToRecyclerView(binding.todoListRecyclerview);
    }

    private void setDefaultSettings() {
        if (SettingValue.getBackgroundValue() == null)
            SettingValue.setBackgroundValue("blue");
        if (SettingValue.getTextSizeValue() == 0)
            SettingValue.setTextSizeValue(2);
        if (SettingValue.getTextAlignmentValue() == 0)
            SettingValue.setTextAlignmentValue(2);

//        switch (SettingValue.getBackgroundValue()) {
//            case "light_brown":
//                binding.settingBtn.setBackgroundColor(getResources().getColor(R.color.light_brown));
//                binding.addTodoListBtn.setBackgroundColor(getResources().getColor(R.color.light_brown));
//                binding.todoListRecyclerview.setBackgroundColor(getResources().getColor(R.color.light_brown));
//                break;
//            case "dark_brown":
//                binding.settingBtn.setBackgroundColor(getResources().getColor(R.color.dark_brown));
//                binding.addTodoListBtn.setBackgroundColor(getResources().getColor(R.color.dark_brown));
//                binding.todoListRecyclerview.setBackgroundColor(getResources().getColor(R.color.dark_brown));
//                break;
//
//            case ""
//        }
    }

    private void addTodoList() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View dialogView = getLayoutInflater().inflate(R.layout.add_todo_dialog, null);
        builder.setView(dialogView);
        Button addTodoListBtn = dialogView.findViewById(R.id.add_todoList_btn2);
        AlertDialog alertDialog = builder.create();

        addTodoListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = dialogView.findViewById(R.id.todoList_editTv);

                if (editText.getText().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "할 일을 입력 해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    ToDoList toDoList = new ToDoList();
                    toDoList.setTodoList(String.valueOf(editText.getText()));
                    viewModel.insertTodoList(toDoList);

                    alertDialog.dismiss();
                }
            }
        });

        alertDialog.show();
    }

    private void updateTodoList(ToDoList toDoList) {
        todoListAdapter.isDrag = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View dialogView = getLayoutInflater().inflate(R.layout.add_todo_dialog, null);
        Button addTodoListBtn = dialogView.findViewById(R.id.add_todoList_btn2);
        TextView textView = dialogView.findViewById(R.id.todo_dialog_title);
        textView.setText("할 일 수정");
        addTodoListBtn.setText("수정");
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        addTodoListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = dialogView.findViewById(R.id.todoList_editTv);

                if (editText.getText().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "할 일을 입력 해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    toDoList.setTodoList(String.valueOf(editText.getText()));
                    viewModel.updateTodoList(toDoList);
                    alertDialog.dismiss();
                }
            }
        });

        alertDialog.show();
    }

    private void deleteTodoList(ToDoList toDoList) {
        todoListAdapter.isDrag = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("할일 삭제")
                .setMessage("삭제하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        viewModel.deleteTodoList(toDoList);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        todoListAdapter.notifyDataSetChanged();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void requestPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("권한 요청")
                .setMessage("스마트폰 화면을 킬 시 할 일 목록이 항상 위에 오게 하기 위해서 다른앱 위에 그리기 권한이 필수적으로 사용됩니다. \n 권한을 허용해주세요")
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setSystemAlertWindowPermission();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void setSystemAlertWindowPermission() {
        TedPermission.with(this)
                .setPermissionListener(this)
                .setPermissions(Manifest.permission.SYSTEM_ALERT_WINDOW)
                .check();
    }

    private void permissionCheck() {
        if (!TedPermission.isGranted(this, Manifest.permission.SYSTEM_ALERT_WINDOW)) {
            requestPermissionDialog();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                startForegroundService(new Intent(this, TodoLisViewService.class));
            else
                startService(new Intent(this, TodoLisViewService.class));
        }
    }


    @Override
    public void onPermissionGranted() {

    }

    @Override
    public void onPermissionDenied(List<String> deniedPermissions) {

    }

    @Override
    protected void onPause() {
        todoListAdapter.isDrag = false;
        super.onPause();
    }

    @Override
    protected void onStart() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissionCheck();
        }
        super.onStart();
    }
}