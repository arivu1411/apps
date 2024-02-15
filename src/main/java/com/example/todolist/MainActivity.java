package com.example.todolist;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;

import Adapter.ToDoAdapter;
import Adapter.ToDoLists;
import DataBase.ToDoDataBaseHelper;

public class MainActivity extends AppCompatActivity {

    public ToDoAdapter adapter;
    private ToDoDataBaseHelper  dataBaseHelper;
    public List<ToDoLists> toDoLists;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataBaseHelper = new ToDoDataBaseHelper(this);
        dataBaseHelper.openDatabase();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ToDoAdapter adapter = new ToDoAdapter(MainActivity.this, dataBaseHelper);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelp(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);


        FloatingActionButton addFloatButton = findViewById(R.id.floatingActionButton);


        toDoLists = dataBaseHelper.getAllTasks();
        Collections.reverse(toDoLists);
        adapter.setTasks(toDoLists);

        addFloatButton.setOnClickListener(v -> AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG));
    }

    @SuppressLint("NotifyDataSetChanged")
    public void handleDialogClose(DialogInterface dialog) {
        toDoLists=dataBaseHelper.getAllTasks();
        Collections.reverse(toDoLists);
        adapter.setTasks(toDoLists);
        adapter.notifyDataSetChanged();

    }
}