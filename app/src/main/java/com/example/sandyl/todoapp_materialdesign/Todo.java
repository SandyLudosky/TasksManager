package com.example.sandyl.todoapp_materialdesign;

import java.util.Date;

/**
 * Created by sandyl on 2017-02-18.
 */

public class Todo {

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


    //id
    public int getId() {
        return this._id;
    }
    public void setId(int id) {
        this._id = id;
    }

    //Text
    public String getText() {
        return this.text;
    }
    public void setText(String text) {
        this.text = text;
    }

    //Status
    public Status getStatus() {
        return this.status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    //Priority
    public Priority getPriority() {
        return this.priority;
    }
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    //Date
    public Date getDate() {
        return this.date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

}
