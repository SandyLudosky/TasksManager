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
        String currentTodoPriority = currentTodo.setPriority(currentTodo.priority);
        String dateView = "due date: " + getDateStr(currentTodo.date);

        holder.taskView.setText(currentTodo.text);
        holder.dateTexView.setText(dateView);
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
        public TextView dateTexView;


        public ItemViewHolder(View itemView) {
            super(itemView);

            taskView = (TextView) itemView.findViewById(R.id.taskTextView);
            priorityTextView = (TextView) itemView.findViewById(R.id.priorityTextView);
            dateTexView = (TextView) itemView.findViewById(R.id.dateTextView);
        }
    }


    public String setColor(String priority) {

        String priorityColor = "";

        switch (priority.toLowerCase()) {

            case "low":
                priorityColor = "#00FF00";
                break;


            case "medium":
                priorityColor = "#FFD700";
                break;

            case "high":
                priorityColor = "#FF0000";
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

    public String calculateDueDate() {

    }


}
