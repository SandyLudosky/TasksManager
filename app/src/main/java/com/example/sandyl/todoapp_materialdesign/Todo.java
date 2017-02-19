package com.example.sandyl.todoapp_materialdesign;

import java.util.Date;

/**
 * Created by sandyl on 2017-02-18.
 */

public class Todo {

    //enums
    public enum Priority {
        MEDIUM,
        HIGH
    }

    public enum Status {
        ACTIVE,
        DONE
    }

    //properties
    public String text;
    public Priority priority;
    public Status status;
    public Date date;



    //constructors
    public Todo() {}

    public Todo(String text) {
        this.text = text;
    }

    //methods
    public String setPriority(Priority priority) {

        String todoPriority = "";

        switch (priority) {
            case MEDIUM:
                todoPriority = "Medium";
                break;

            case HIGH:
                todoPriority = "High";
                break;

            default:
                todoPriority = "";
                break;
        }

        return  todoPriority;
    }
}
