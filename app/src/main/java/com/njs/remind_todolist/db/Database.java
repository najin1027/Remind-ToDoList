package com.njs.remind_todolist.db;

import android.app.Application;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.njs.remind_todolist.model.ToDoList;
import com.njs.remind_todolist.model.ToDoListDao;


@androidx.room.Database(entities = {ToDoList.class} , version = 1 , exportSchema = false)
public abstract class Database extends RoomDatabase {


    public abstract ToDoListDao toDoListDao();
    public static volatile Database INSTANCE;


    public static Database getDatabase(Application application) {
        if(INSTANCE == null) {
            synchronized (Database.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(application, Database.class, "todoList.db").build();
                }
            }
        }

        return INSTANCE;
    }

}
