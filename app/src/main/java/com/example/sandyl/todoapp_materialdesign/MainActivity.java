package com.example.sandyl.todoapp_materialdesign;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.sandyl.todoapp_materialdesign.Todo.Status.ACTIVE;

public class MainActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener {

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
        setRecyclerViewClickListener();
    }

    public void setRecyclerViewClickListener() {
        recyclerView = (RecyclerView) findViewById(R.id.rvItems);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(MainActivity.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever

                        Todo todoSelected  = todos.get(position);
                        Intent intent = new Intent(MainActivity.this, EditTodoActivity.class);
                        intent.putExtra("task", todoSelected.text);
                        intent.putExtra("priority",   putPriority(todoSelected.priority));
                        intent.putExtra("status", putStatus(todoSelected.status));
                        intent.putExtra("date",getDateStr(todoSelected.date));
                        intent.putExtra("position", position);
                        startActivityForResult(intent, 2);

                        Log.d("TAG", todoSelected.text);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

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
            todo.date = new Date();
            todo.priority = Todo.Priority.HIGH;
            todo.status = Todo.Status.ACTIVE;

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
        String date = data.getStringExtra("date");
        String status = data.getStringExtra("status");
        int position = data.getIntExtra("position", -1);

        if (resultCode == 1) {
            addTodo(text, getDate(date), setStatus(status), setPriority(priority));
        }

        if (resultCode == 2) {
            todos.remove(position);
            addTodo(text, getDate(date), setStatus(status), setPriority(priority));
        }


        //add todo to list on saving
        addTodo(text, getDate(date), setStatus(status), setPriority(priority));

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

    public String putPriority(Todo.Priority  priority) {

        //to initialize
        String priorityLevel = "medium";

        switch (priority) {
            case MEDIUM:
                priorityLevel = "medium";
                break;

            case HIGH:
                priorityLevel = "high";
                break;

        }
        return  priorityLevel;
    }




    public Todo.Status setStatus(String status) {

        //to initialize
        Todo.Status todoStatus = ACTIVE;

        switch (status) {
            case "medium":
                todoStatus= ACTIVE;
                break;

            case "high":
                todoStatus = Todo.Status.DONE;
                break;

        }

        return  todoStatus;
    }

    public String putStatus(Todo.Status  status) {

        //to initialize
        String todoStatus = "active";

        switch (status) {
            case ACTIVE:
                todoStatus = "active";
                break;

            case DONE:
                todoStatus= "done";
                break;

        }
        return  todoStatus;
    }


    public Date getDate(String dateStr) {

        DateFormat dateFormat ;
        Date date = new Date();
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        try
        {
            date = (Date) dateFormat.parse(dateStr);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return date;

    }

    public String getDateStr(Date date) {

        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        String stringDate = formatter.format(date);

        return  stringDate;
    }




}
