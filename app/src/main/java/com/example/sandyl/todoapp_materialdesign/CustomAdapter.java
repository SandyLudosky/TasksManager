package com.example.sandyl.todoapp_materialdesign;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
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
        holder.taskView.setText(currentTodo.text);
        holder.priorityTextView.setText(currentTodoPriority);
        holder.priorityTextView.setTextColor(Color.parseColor(setColor(currentTodoPriority)));

    }

    @Override
    public int getItemCount() {
        return todos.size();
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView taskView;
        public TextView priorityTextView;


        public ItemViewHolder(View itemView) {
            super(itemView);

            taskView = (TextView) itemView.findViewById(R.id.taskTextView);
            priorityTextView = (TextView) itemView.findViewById(R.id.priorityTextView);
        }
    }


    public String setColor(String priority) {

        String priorityColor = "";

        switch (priority) {

            case "Medium":
                priorityColor = "#FFD700";
                break;

            case "High":
                priorityColor = "#FF0000";
                break;

            default:
                System.out.println("");
                break;
        }
        return  priorityColor;
    }

}
