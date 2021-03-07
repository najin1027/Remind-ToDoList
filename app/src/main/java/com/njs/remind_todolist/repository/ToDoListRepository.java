package com.njs.remind_todolist.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.njs.remind_todolist.db.Database;
import com.njs.remind_todolist.model.ToDoList;
import com.njs.remind_todolist.model.ToDoListDao;

import java.util.List;

public class ToDoListRepository {


    private ToDoListDao toDoListDao;
    private Database database;
    private LiveData<List<ToDoList>> todoLists;

    public ToDoListRepository(Application application) {
      database = Database.getDatabase(application);
      toDoListDao = database.toDoListDao();
      todoLists = toDoListDao.getTodoList();
    }


    public LiveData<List<ToDoList>> getTodoLists(){
        return toDoListDao.getTodoList();
    }

    public void insertToDoList(final ToDoList toDoList){
            new AsyncTask<Void , Void , Void>(){

                @Override
                protected Void doInBackground(Void... voids) {
                    database.toDoListDao().insertTodoList(toDoList);
                    return null;
                }
            }.execute();
    }
}
