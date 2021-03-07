package com.njs.remind_todolist.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.njs.remind_todolist.model.ToDoList;
import com.njs.remind_todolist.repository.ToDoListRepository;

import java.util.List;

public class ToDoListViewModel extends ViewModel {

    public LiveData<List<ToDoList>> todoLists;
    public ToDoListRepository toDoListRepository;

    public void init(Application application) {
            toDoListRepository = new ToDoListRepository(application);
            todoLists = toDoListRepository.getTodoLists();
    }


    public void insertTodoList(ToDoList toDoList){
        toDoListRepository.insertToDoList(toDoList);
    }


    public LiveData<List<ToDoList>> getTodoLists(){
        return todoLists;
    }


}
