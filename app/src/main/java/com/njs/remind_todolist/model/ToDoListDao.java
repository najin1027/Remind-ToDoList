package com.njs.remind_todolist.model;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ToDoListDao {

    @Insert
    void insertTodoList(ToDoList toDoList);

    @Query("SELECT * from todoList")
     LiveData<List<ToDoList>> getTodoList();

    @Delete
    void deleteTodoList(ToDoList toDoList);

    @Update
    void updateTodoList(ToDoList toDoList);

    @Query("UPDATE todoList set id = :updateId WHERE id = :id")
    void updateId(int id, int updateId);

}
