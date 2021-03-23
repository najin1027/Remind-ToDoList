package com.njs.remind_todolist.model;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ToDoListDao {

    @Insert
    void insertTodoList(ToDoList toDoList);

    @Query("SELECT * from todoList")
     LiveData<List<ToDoList>> getTodoList();

    @Delete
    void deleteTodoList(ToDoList toDoList);




}
