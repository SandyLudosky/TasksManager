package com.example.sandyl.todoapp_materialdesign;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String TAG;
    RecyclerView recyclerView;
    List<Todo> todos;
    CustomAdapter adapter;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    public void AddTodoAction() {

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTodoActivity.class);
                startActivity(intent);
            }
        });

    }
}
