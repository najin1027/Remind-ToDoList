package com.njs.remind_todolist.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todoList")
public class ToDoList {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "todoList")
    private String todoList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTodoList() {
        return todoList;
    }

    public void setTodoList(String todoList) {
        this.todoList = todoList;
    }
}
