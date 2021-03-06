package com.example.sandyl.todoapp_materialdesign;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener {

    private String TAG;
    private Toolbar mToolbar;
    RecyclerView recyclerView;
    List<Todo> todos;
    public CustomAdapter adapter;
    private FloatingActionButton fab;
    TextView dateTextView;

    //database helper
    TodoDatabaseAdapter todoDatabase;
    TodoManager todoManager = new TodoManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //database initialization
        todoDatabase = new TodoDatabaseAdapter(this);
        // SQLiteDatabase db =  todoDatabase.todoDatabaseHelper.getWritableDatabase();


        todos = new ArrayList<Todo>();
        todos = queryAllTodos();

        recyclerView = (RecyclerView) findViewById(R.id.rvItems);

        adapter = new CustomAdapter(MainActivity.this, todos);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));

        addToolbar();;
        AddTodoActionWithFloatingButton();
        setRecyclerViewClickListener();

    }

    //Toolbar
    public void addToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    public void setRecyclerViewClickListener() {
        recyclerView = (RecyclerView) findViewById(R.id.rvItems);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(MainActivity.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever

                        Todo todoSelected  = todos.get(position);
                        Intent intent = new Intent(MainActivity.this, EditTodoActivity.class);
                        intent.putExtra("uid", todoSelected.getId());
                        intent.putExtra("task", todoSelected.getText());
                        intent.putExtra("priority", todoManager.putTodoPriority(todoSelected.getPriority()));
                        intent.putExtra("status", todoManager.putTodoStatus(todoSelected.getStatus()));
                        intent.putExtra("date",todoManager.getDateStr(todoSelected.getDate()));
                        intent.putExtra("position", position);
                        startActivityForResult(intent, 2);

                        getTodo(todoSelected);
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

    public void addTodo(Todo todo) {

        Todo newTodo = new Todo();
        newTodo.text = todo.text;
        newTodo.date = todo.date;
        newTodo.status = todo.status;
        newTodo.priority = todo.priority;

        todos.add(newTodo);
        todoDatabase.insertTodo(newTodo);
    }


    public void updateTodo(Todo todo) {
        todoDatabase.updateTodo(todo);
    }

    public void deleteTodo(Todo todo) {
        todoDatabase.deleteTodo(todo);
    }


    public List queryAllTodos() {
        todos.addAll(todoDatabase.getAllData());
        return  todos;
    }

    public void getTodo(Todo todo) {
        Todo aTodo = new Todo();
        aTodo = todoDatabase.getTodo(todo);
    }

    public void AddTodoActionWithFloatingButton() {

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.turquoise)));

        int color = ContextCompat.getColor(this, R.color.textColorPrimary);
        fab.getDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);

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

        Todo todo = new Todo();
        todo.setText(text);
        todo.setDate(todoManager.getDate(date));
        todo.setStatus(todoManager.setTodoStatus(status));
        todo.setPriority(todoManager.setTodoPriority(priority));

        if (resultCode == 1) {
            Log.d("TAG", "save");
            int uid = todos.size()+1;
            todo.setId(uid);
            addTodo(todo);
        }

        if (resultCode == 2) {
            Log.d("TAG", "edit");
            todos.remove(position);
            todos.add(position, todo);
            int uid = data.getIntExtra("uid", -1);

            todo.setId(uid);
            updateTodo(todo);
        }

        if (resultCode == 3){

            todos.remove(position);

            int uid = data.getIntExtra("uid", -1);
            todo.setId(uid);
            deleteTodo(todo);
        }
        
        adapter.notifyDataSetChanged();
    }
}
