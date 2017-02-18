package com.example.sandyl.todoapp_materialdesign;

/**
 * Created by sandyl on 2017-02-18.
 */

public class Todo {

    //enum
    public enum Priority {
        MEDIUM,
        HIGH
    }

    //properties
    public String text;
    public Priority priority;


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
