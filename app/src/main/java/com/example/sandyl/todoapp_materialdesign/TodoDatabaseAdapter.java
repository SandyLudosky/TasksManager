package com.example.sandyl.todoapp_materialdesign;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandyl on 2017-02-20.
 */

public class TodoDatabaseAdapter {


    private String TAG;
    TodoDatabaseHelper todoDatabaseHelper;
    TodoManager todoManager = new TodoManager();

    public TodoDatabaseAdapter (Context context) {
        todoDatabaseHelper = new TodoDatabaseHelper(context);
    }

    //CREATE
    public void insertTodo(Todo todo) {

        SQLiteDatabase db = todoDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(todoDatabaseHelper.KEY_NAME, todo.getText()); // Todo text
        values.put(todoDatabaseHelper.KEY_STATUS, todoManager.putTodoStatus(todo.getStatus())); // Todo status
        values.put(todoDatabaseHelper.KEY_PRIORITY, todoManager.putTodoPriority(todo.getPriority())); // Todo priority
        values.put(todoDatabaseHelper.KEY_DATE, todoManager.getDateStr(todo.getDate())); // Todo date
        values.put(todoDatabaseHelper.KEY_DATE_MIL, todoManager.convertDateToMilliseconds(todoManager.getDateStr(todo.getDate()))); // date in milliseconds

       db.insert(todoDatabaseHelper.TABLE_TODOS, null, values);
    }


    //READ - query all data
    public List<Todo> getAllData() {

        SQLiteDatabase db = todoDatabaseHelper.getWritableDatabase();
        List<Todo> todos = new ArrayList<Todo>();

        String [] cols = {todoDatabaseHelper.KEY_ID, todoDatabaseHelper.KEY_NAME, todoDatabaseHelper.KEY_STATUS, todoDatabaseHelper.KEY_PRIORITY, todoDatabaseHelper.KEY_DATE};
        String orderBy = todoDatabaseHelper.KEY_DATE_MIL  + " ASC, " +  todoDatabaseHelper.KEY_STATUS + " ASC";
        Cursor cursor = db.query(todoDatabaseHelper.TABLE_TODOS, cols, null, null,null, null, orderBy);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                int uid = cursor.getInt(getColIndex(cursor, todoDatabaseHelper.KEY_ID));
                String name =  cursor.getString(getColIndex(cursor, todoDatabaseHelper.KEY_NAME));
                String status = cursor.getString(getColIndex(cursor, todoDatabaseHelper.KEY_STATUS));
                String priority =cursor.getString(getColIndex(cursor, todoDatabaseHelper.KEY_PRIORITY));
                String date =cursor.getString(getColIndex(cursor, todoDatabaseHelper.KEY_DATE));

                Todo todo = new Todo();
                todo.setId(uid);
                todo.setText(name);
                todo.setStatus(todoManager.setTodoStatus(status));
                todo.setPriority(todoManager.setTodoPriority(priority));
                todo.setDate(todoManager.getDate(date));

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
        String selectionArgs =  todoDatabaseHelper.KEY_ID+ "= "+todo.getId()+"" ;

        Cursor cursor = db.query(todoDatabaseHelper.TABLE_TODOS, cols, selectionArgs, null,null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                int uid = cursor.getInt(getColIndex(cursor, todoDatabaseHelper.KEY_ID));
                String name =  cursor.getString(getColIndex(cursor, todoDatabaseHelper.KEY_NAME));
                String status = cursor.getString(getColIndex(cursor, todoDatabaseHelper.KEY_STATUS));
                String priority =cursor.getString(getColIndex(cursor, todoDatabaseHelper.KEY_PRIORITY));
                String date =cursor.getString(getColIndex(cursor, todoDatabaseHelper.KEY_DATE));

                todo._id = uid;
                todo.setId(uid);
                todo.setText(name);
                todo.setStatus(todoManager.setTodoStatus(status));
                todo.setPriority(todoManager.setTodoPriority(priority));
                todo.setDate(todoManager.getDate(date));

            } while (cursor.moveToNext());
        }
        return todo;
    }

    //UPDATE
    public void updateTodo(Todo todo) {

        SQLiteDatabase db = todoDatabaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(todoDatabaseHelper.KEY_NAME, todo.getText()); // Todo text
        values.put(todoDatabaseHelper.KEY_STATUS, todoManager.putTodoStatus(todo.getStatus())); // Todo status
        values.put(todoDatabaseHelper.KEY_PRIORITY, todoManager.putTodoPriority(todo.getPriority())); // Todo priority
        values.put(todoDatabaseHelper.KEY_DATE, todoManager.getDateStr(todo.getDate())); // Todo date
        values.put(todoDatabaseHelper.KEY_DATE_MIL, todoManager.convertDateToMilliseconds(todoManager.getDateStr(todo.getDate()))); // date in

        String whereClause = todoDatabaseHelper.KEY_ID +" =? ";
        String[] whereArgs = {String.valueOf(todo.getId())};

        db.update(todoDatabaseHelper.TABLE_TODOS, values, whereClause, whereArgs);
        db.close();
    }

    //DELETE
    public void deleteTodo(Todo todo) {
        SQLiteDatabase db = todoDatabaseHelper.getWritableDatabase();

        String whereClause = todoDatabaseHelper.KEY_ID +" =? ";
        String[] whereArgs = {String.valueOf(todo.getId())};

        db.delete(todoDatabaseHelper.TABLE_TODOS, whereClause, whereArgs);
        db.close();
    }


    //function to return column index - less chance of error
    public int getColIndex(Cursor cursor, String colName) {

        int index =  cursor.getColumnIndex(colName);
        return  index;
    }

    //get DATA count
    public int getTodosCount() {

        String countQuery = "SELECT  * FROM " + todoDatabaseHelper.TABLE_TODOS;
        SQLiteDatabase db = todoDatabaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;

    }

    class TodoDatabaseHelper extends SQLiteOpenHelper {

        // All Static variables
        // Database Version - to update after every change in db schema
        private static final int DATABASE_VERSION = 12;

        // Database Name
        private static final String DATABASE_NAME = "todosManager.db";

        // table name
        private static final String TABLE_TODOS = "TODOS";

        //Table Columns names
        private static final String KEY_ID = "id";
        private static final String KEY_NAME = "Name";
        private static final String KEY_STATUS = "Status";
        private static final String KEY_PRIORITY = "Priority";
        private static final String KEY_DATE = "Date";
        private static final String KEY_DATE_MIL = "DateInMil";

        private String TAG;
        private Context context;

        //string statement to create tables and columns
        private static final String CREATE_TABLE_TODOS = "CREATE TABLE IF NOT EXISTS "+TABLE_TODOS+
                "("+KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +KEY_NAME+" VARCHAR(255), "
                +KEY_STATUS +" VARCHAR(255),"
                +KEY_PRIORITY+" VARCHAR(255),"
                +KEY_DATE+" VARCHAR(255),"
                +KEY_DATE_MIL+" VARCHAR(255)"
                +" );";

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
