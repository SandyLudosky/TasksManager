package com.example.sandyl.todoapp_materialdesign;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by sandyl on 2017-02-20.
 */

public class TodoDatabaseHelper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version - to update after every change in db schema
    private static final int DATABASE_VERSION = 2;

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
    private static final String CREATE_TABLE_TODOS = "CREATE TABLE "+TABLE_TODOS+
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
