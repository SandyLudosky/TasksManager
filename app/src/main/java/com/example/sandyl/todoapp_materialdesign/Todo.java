package com.example.sandyl.todoapp_materialdesign;

import java.util.Date;

/**
 * Created by sandyl on 2017-02-18.
 */

public class Todo {

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Priority getPriority() {
        return priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    //enums
    public enum Priority {
        LOW,
        MEDIUM,
        HIGH
    }

    public enum Status {
        ACTIVE,
        DONE
    }

    //properties
    public int _id;
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

            case LOW:
                todoPriority = "low";
                break;

            case MEDIUM:
                todoPriority = "medium";
                break;

            case HIGH:
                todoPriority = "high";
                break;

            default:
                todoPriority = "";
                break;
        }

        return  todoPriority;
    }
}
