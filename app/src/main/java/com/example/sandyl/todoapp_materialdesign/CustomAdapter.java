package com.example.sandyl.todoapp_materialdesign;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by sandyl on 2017-02-18.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ItemViewHolder> {

    private LayoutInflater inflater;


    public CustomAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_row, parent, false);
        ItemViewHolder itemsHolder = new ItemViewHolder(view);
        return itemsHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        Todo currentTodo = new Todo();
        holder.taskView.setText(currentTodo.text);
        holder.priorityTextView.setText(currentTodo.priority);

    }

    @Override
    public int getItemCount() {
        return 0;
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

}
