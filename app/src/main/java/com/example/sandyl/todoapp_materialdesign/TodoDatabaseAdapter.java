package com.example.sandyl.todoapp_materialdesign;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.sandyl.todoapp_materialdesign.Todo.Status.ACTIVE;
import static com.example.sandyl.todoapp_materialdesign.Todo.Status.DONE;

/**
 * Created by sandyl on 2017-02-20.
 */

public class TodoDatabaseAdapter {



    TodoDatabaseHelper todoDatabaseHelper;

    public TodoDatabaseAdapter (Context context) {
        todoDatabaseHelper = new TodoDatabaseHelper(context);
    }


    //CREATE
    public long insertTodo(Todo todo) {

        SQLiteDatabase db = todoDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(todoDatabaseHelper.KEY_NAME, todo.getText()); // Todo text
        values.put(todoDatabaseHelper.KEY_STATUS, putStatus(todo.getStatus())); // Todo status
        values.put(todoDatabaseHelper.KEY_PRIORITY, putPriority(todo.getPriority())); // Todo priority
        values.put(todoDatabaseHelper.KEY_DATE, getDateStr(todo.getDate())); // Todo date


        //to check,m
        long id = db.insert(todoDatabaseHelper.TABLE_TODOS, null, values);

        return id;
    }


    //READ - query all data
    public List<Todo> getAllData() {

        SQLiteDatabase db = todoDatabaseHelper.getWritableDatabase();
        List<Todo> todos = new ArrayList<Todo>();

        String [] cols = {todoDatabaseHelper.KEY_ID, todoDatabaseHelper.KEY_NAME, todoDatabaseHelper.KEY_STATUS, todoDatabaseHelper.KEY_PRIORITY, todoDatabaseHelper.KEY_DATE};

        Cursor cursor = db.query(todoDatabaseHelper.TABLE_TODOS, cols, null, null,null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int uid = cursor.getInt(0);
                String name = cursor.getString(1);
                String status = cursor.getString(2);
                String priority = cursor.getString(3);
                String date = cursor.getString(4);

                Todo todo = new Todo();
                todo._id = uid;
                todo.text =  name;
                todo.status = setTodoStatus(status);
                todo.priority = setTodoPriority(priority);
                todo.date = getDate(date);

                todos.add(todo);
            } while (cursor.moveToNext());
        }

        return todos;

    }

    //READ - query 1 specific data
    public Todo getTodo(Todo todo) {

        SQLiteDatabase db = todoDatabaseHelper.getWritableDatabase();
        Todo aTodo = new Todo();

        String [] cols = {todoDatabaseHelper.KEY_ID, todoDatabaseHelper.KEY_NAME, todoDatabaseHelper.KEY_STATUS, todoDatabaseHelper.KEY_PRIORITY, todoDatabaseHelper.KEY_DATE};
        String arg =  todoDatabaseHelper.KEY_ID+ "= "+todo.getId()+"" ;

        Cursor cursor = db.query(todoDatabaseHelper.TABLE_TODOS, cols, arg, null,null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int uid = cursor.getInt(0);
                String name = cursor.getString(1);
                String status = cursor.getString(2);
                String priority = cursor.getString(3);
                String date = cursor.getString(4);

                todo._id = uid;
                todo.text =  name;
                todo.status = setTodoStatus(status);
                todo.priority = setTodoPriority(priority);
                todo.date = getDate(date);

            } while (cursor.moveToNext());
        }

        return todo;

    }

    //UPDATE


    //DELETE

    //Status
    public String putStatus(Todo.Status  status) {

        //to initialize
        String todoStatus = new String();

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


    public Todo.Status setTodoStatus(String status) {

        //to initialize
        Todo.Status todoStatus = ACTIVE;

        switch (status.toLowerCase()) {
            case "active":
                todoStatus = ACTIVE;
                break;

            case "done":
                todoStatus = DONE;
                break;

        }

        return  todoStatus;
    }

    //Priority
    public String putPriority(Todo.Priority  priority) {

        //to initialize
        String priorityLevel = "medium";

        switch (priority) {
            case LOW:
                priorityLevel = "low";
                break;

            case MEDIUM:
                priorityLevel = "medium";
                break;

            case HIGH:
                priorityLevel = "high";
                break;

        }
        return  priorityLevel;
    }


    public Todo.Priority setTodoPriority(String priority) {

        //to initialize
        Todo.Priority priorityLevel = Todo.Priority.LOW;

        switch (priority.toLowerCase()) {
            case "low":
                priorityLevel = Todo.Priority.LOW;
                break;

            case "medium":
                priorityLevel = Todo.Priority.MEDIUM;
                break;

            case "high":
                priorityLevel = Todo.Priority.HIGH;
                break;

        }

        return  priorityLevel;
    }


    //Date
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


    class TodoDatabaseHelper extends SQLiteOpenHelper {

        // All Static variables
        // Database Version - to update after every change in db schema
        private static final int DATABASE_VERSION = 6;

        // Database Name
        private static final String DATABASE_NAME = "todosManager.db";

        // table name
        private static final String TABLE_TODOS = "TODOS";

        //Table Columns names
        private static final String KEY_ID = "_id";
        private static final String KEY_NAME = "Name";
        private static final String KEY_STATUS = "Status";
        private static final String KEY_PRIORITY = "Priority";
        private static final String KEY_DATE = "Date";

        private String TAG;
        private Context context;

        //string statement to create tables and columns
        private static final String CREATE_TABLE_TODOS = "CREATE TABLE IF NOT EXISTS "+TABLE_TODOS+
                "("+KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +KEY_NAME+" VARCHAR(255), "
                +KEY_STATUS +" VARCHAR(255),"
                +KEY_PRIORITY+" VARCHAR(255),"
                +KEY_DATE+" VARCHAR(255)" +
                " );";

        private static final String DROP_TABLE_TODOS = "DROP TABLE IF EXISTS "+TABLE_TODOS;

        public TodoDatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE_TODOS);
                Toast.makeText(context,"Success: table "+ TABLE_TODOS+" successfully created", Toast.LENGTH_LONG).show();

            } catch (NumberFormatException e) {
                Toast.makeText(context, "Error: table creation failed" , Toast.LENGTH_LONG).show();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            try {
                db.execSQL(DROP_TABLE_TODOS);
                Toast.makeText(context,"Success: table "+ "Success: table "+ TABLE_TODOS+" successfully deleted", Toast.LENGTH_LONG).show();

            } catch (NumberFormatException e) {
                Toast.makeText(context, "Error: table drop failed" , Toast.LENGTH_LONG).show();
            }

        }
    }

}
