package com.example.sandyl.todoapp_materialdesign;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by sandyl on 2017-02-18.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ItemViewHolder> {


    private LayoutInflater inflater;
    List<Todo> todos = Collections.emptyList();




    public CustomAdapter(Context context, List<Todo> data) {
        inflater = LayoutInflater.from(context);
        this.todos = data;

    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_row, parent, false);
        ItemViewHolder itemsHolder = new ItemViewHolder(view);
        return itemsHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {


        Todo currentTodo = todos.get(position);
        String currentTodoPriority = currentTodo.setTodoPriority(currentTodo.priority);
        String dateView = calculateDueDate(currentTodo.date);

        holder.taskView.setText(currentTodo.text);
        holder.dateTextView.setText(dateView);
        holder.priorityTextView.setText(currentTodoPriority);
        holder.priorityTextView.setBackgroundColor(Color.parseColor(setColor(currentTodoPriority)));


    }

    @Override
    public int getItemCount() {
        return todos.size();
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView taskView;
        public TextView priorityTextView;
        public TextView dateTextView;


        public ItemViewHolder(View itemView) {
            super(itemView);

            taskView = (TextView) itemView.findViewById(R.id.taskTextView);
            priorityTextView = (TextView) itemView.findViewById(R.id.priorityTextView);
            dateTextView = (TextView) itemView.findViewById(R.id.dateTextView);
        }
    }


    public String setColor(String priority) {

        String priorityColor = "";

        switch (priority.toLowerCase()) {

            case "low":
                priorityColor = "#1abc9c";
                break;


            case "medium":
                priorityColor = "#f1c40f";
                break;

            case "high":
                priorityColor = "#e74c3c";
                break;

            default:
                System.out.println("");
                break;
        }
        return  priorityColor;
    }


    public String getDateStr(Date date) {

        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        String stringDate = formatter.format(date);

        return  stringDate;
    }

    public String calculateDueDate(Date todoDate) {

        Date today = new Date();
        String formattedCount;

        Calendar todoCal = Calendar.getInstance();
        Calendar currentCal  = Calendar.getInstance();

        todoCal.setTime(todoDate);
        currentCal.setTime(today);

        long diff = todoCal.getTimeInMillis() - currentCal.getTimeInMillis();

        float dayCount = (float) diff / (24 * 60 * 60 * 1000);

        if ((int)dayCount == 0) {
            formattedCount = "due Today \uD83D\uDCA1";
        } else {
            formattedCount = ("due in " + (int)dayCount + " days");
        }

        return formattedCount;
    }




}
