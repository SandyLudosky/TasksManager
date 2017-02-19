package com.example.sandyl.todoapp_materialdesign;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String TAG;
    private Toolbar mToolbar;
    RecyclerView recyclerView;
    List<Todo> todos;
    CustomAdapter adapter;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.rvItems);
        todos = new ArrayList<Todo>();
        todos = getData();
        adapter = new CustomAdapter(MainActivity.this, todos);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        AddTodoAction();
    }


    public List getData() {

        List<Todo> data;

        List<String> tasks = new ArrayList<String>();
        tasks.add("Task 1");
        tasks.add("Task 2");
        tasks.add("Task 3");
        tasks.add("Task 4");

        data =  new ArrayList<Todo>();


        for (int i = 0; i < tasks.size() ; i++) {

            Todo todo = new Todo();
            todo.text = tasks.get(i);
            todo.priority = Todo.Priority.HIGH;

            Log.d("TAG", todo.text);
            data.add(todo);
        }

        return data;
    }


    public void addTodo(String text, Date date, Todo.Status status, Todo.Priority priority) {

        Todo newTodo = new Todo();
        newTodo.text = text;
        newTodo.date = date;
        newTodo.status = status;
        newTodo.priority = priority;

        todos.add(newTodo);
        adapter.notifyDataSetChanged();

    }

    public void AddTodoAction() {

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTodoActivity.class);
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        String text = data.getStringExtra("task");
        String priority = data.getStringExtra("priority");

        //add todo to list on saving
        addTodo(text, new Date(), Todo.Status.ACTIVE, setPriority(priority));

    }

    public Todo.Priority setPriority(String priority) {

        //to initialize
        Todo.Priority priorityLevel = Todo.Priority.MEDIUM;

        switch (priority) {
            case "medium":
                priorityLevel = Todo.Priority.MEDIUM;
                break;

            case "high":
                priorityLevel = Todo.Priority.HIGH;
                break;

        }

        return  priorityLevel;
    }
}
