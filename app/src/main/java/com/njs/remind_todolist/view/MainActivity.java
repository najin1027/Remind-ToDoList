package com.njs.remind_todolist.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.njs.remind_todolist.model.SettingValue.sharedPreferences;

public class MainActivity extends AppCompatActivity implements PermissionListener {
    private ActivityMainBinding binding;
    private ToDoListViewModel viewModel;
    private TodoListAdapter todoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setBackgroundColor();

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

    @SuppressLint({"CommitPrefEdits", "UseCompatLoadingForDrawables"})
    private void init() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SettingValue.spEditor = sharedPreferences.edit();
        if (SettingValue.getBackgroundValue() == null)
            SettingValue.setBackgroundValue("blue");
        if (SettingValue.getTextSizeValue() == 0)
            SettingValue.setTextSizeValue(2);
        if (SettingValue.getTextAlignmentValue() == 0)
            SettingValue.setTextAlignmentValue(2);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(ToDoListViewModel.class);
        viewModel.init(getApplication());
        todoListAdapter = new TodoListAdapter();
        binding.setViewModel(viewModel);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL);
        if (SettingValue.getBackgroundValue().equalsIgnoreCase("white")) {
            dividerItemDecoration.setDrawable(getApplicationContext().getResources().getDrawable(R.drawable.recyclerview_divider_black));
            binding.settingBtn.setImageResource(R.drawable.ic_baseline_settings_24_black);
            binding.addTodoListBtn.setImageResource(R.drawable.ic_baseline_add_circle_24_black);
            binding.headerLineView.setBackgroundColor(ContextCompat.getColor(this, R.color.black));
        } else
            dividerItemDecoration.setDrawable(getApplicationContext().getResources().getDrawable(R.drawable.recyclerview_divider_white));
        binding.todoListRecyclerview.addItemDecoration(dividerItemDecoration);
        binding.todoListRecyclerview.setAdapter(todoListAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(todoListAdapter));
        itemTouchHelper.attachToRecyclerView(binding.todoListRecyclerview);
    }

    private void setBackgroundColor() {
        // background Color Settings
        HashMap<String, Integer> settingValuesMap = new HashMap<String, Integer>() {{
            put("light_brown", R.color.light_brown);
            put("dark_brown", R.color.dark_brown);
            put("reddish_brown", R.color.reddish_brown);
            put("green", R.color.green);
            put("blue", R.color.blue);
            put("violet", R.color.violet);
            put("charcoal", R.color.charcoal);
            put("black", R.color.black);
            put("white", R.color.white);
            put("light_pink", R.color.light_pink);
            put("light_yellow", R.color.light_yellow);
        }};
        for (Map.Entry<String, Integer> entry : settingValuesMap.entrySet()) {
            if (SettingValue.getBackgroundValue().equalsIgnoreCase(entry.getKey())) {
                binding.settingBtn.setBackgroundColor(ContextCompat.getColor(this, entry.getValue()));
                binding.addTodoListBtn.setBackgroundColor(ContextCompat.getColor(this, entry.getValue()));
                binding.todoListRecyclerview.setBackgroundColor(ContextCompat.getColor(this, entry.getValue()));
                setStatusBarColor(entry.getValue());
            }
        }
    }

    private void setStatusBarColor(int color) {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, color));
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
                    Toast.makeText(getApplicationContext(), R.string.dialog_add_todo_list_toast_message, Toast.LENGTH_SHORT).show();
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
        textView.setText(R.string.dialog_update_todo_list_title);
        addTodoListBtn.setText(R.string.dialog_check);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        addTodoListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = dialogView.findViewById(R.id.todoList_editTv);

                if (editText.getText().length() <= 0) {
                    Toast.makeText(getApplicationContext(), R.string.dialog_add_todo_list_toast_message, Toast.LENGTH_SHORT).show();
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
                .setTitle(R.string.dialog_delete_todo_list_title)
                .setMessage(R.string.dialog_delete_todo_seeking_consent_to_delete)
                .setCancelable(false)
                .setPositiveButton(R.string.dialog_agree, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        viewModel.deleteTodoList(toDoList);
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
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
                .setTitle(R.string.dialog_request_permission_title)
                .setMessage(R.string.dialog_request_permission_message)
                .setCancelable(false)
                .setPositiveButton(R.string.dialog_check, new DialogInterface.OnClickListener() {
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