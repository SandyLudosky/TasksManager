package com.example.sandyl.todoapp_materialdesign;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.sandyl.todoapp_materialdesign.Todo.Status.ACTIVE;
import static com.example.sandyl.todoapp_materialdesign.Todo.Status.DONE;

/**
 * Created by sandyl on 2017-02-19.
 */

public class DatabaseHandler extends SQLiteOpenHelper {


    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "todosManager";

    // Contacts table name
    private static final String TABLE_TODOS = "todos";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TEXT = "text";
    private static final String KEY_STATUS = "status";
    private static final String KEY_PRIORITY = "priority";
    private static final String KEY_DATE = "date";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TODOS_TABLE = "CREATE TABLE " + TABLE_TODOS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TEXT + " TEXT)";
        db.execSQL(CREATE_TODOS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOS);

        // Create tables again
        onCreate(db);
    }



    //All CRUD Operations
    // Adding new contact
    public void addTodo(Todo todo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TEXT, todo.getText()); // Todo text
        values.put(KEY_STATUS, putStatus(todo.getStatus())); // Todo status
        values.put(KEY_PRIORITY, putPriority(todo.getPriority())); // Todo priority
        values.put(KEY_DATE, getDateStr(todo.getDate())); // Todo date

        // Inserting Row
        db.insert(TABLE_TODOS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    public Todo getTodo(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TODOS, new String[] { KEY_ID,
                        KEY_TEXT, KEY_STATUS, KEY_PRIORITY, KEY_DATE}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Todo todo = new Todo();
        todo._id = Integer.parseInt(cursor.getString(0));
        todo.text =  cursor.getString(1);
        todo.status = setTodoStatus(cursor.getString(2));
        todo.priority = setTodoPriority(cursor.getString(3));
        todo.date = getDate(cursor.getString(4));

        // return todo
        return todo;
    }

    // Getting All Contacts
    public List<Todo> getAllContacts() {

        List<Todo> todos = new ArrayList<Todo>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TODOS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Todo todo = new Todo();
                todo.setId(Integer.parseInt(cursor.getString(0)));
                todo.setText(cursor.getString(1));
                todo.setStatus(setTodoStatus(cursor.getString(2)));
                todo.setPriority(setTodoPriority(cursor.getString(3)));
                todo.setDate(getDate(cursor.getString(4)));

                // Adding todo to list
                todos.add(todo);
            } while (cursor.moveToNext());
        }

        // return todos
        return todos;
    }

    // Getting todos Count
    public int getTodosCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TODOS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Updating single todo
    public int updateTodo(Todo todo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TEXT, todo.getText()); // Todo text
        values.put(KEY_STATUS, putStatus(todo.getStatus())); // Todo status
        values.put(KEY_PRIORITY, putPriority(todo.getPriority())); // Todo priority
        values.put(KEY_DATE, getDateStr(todo.getDate())); // Todo date

        // updating row
        return db.update(TABLE_TODOS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(todo.getId()) });
    }

    // Deleting single todo
    public void deleteTodo(Todo todo) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_TODOS, KEY_ID + " = ?",
                new String[] { String.valueOf(todo.getId()) });
        db.close();
    }


    public void clearTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TODOS, null, null);
    }

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

}

