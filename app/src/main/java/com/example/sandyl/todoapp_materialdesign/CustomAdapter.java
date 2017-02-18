package com.example.sandyl.todoapp_materialdesign;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by sandyl on 2017-02-18.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ItemViewHolder> {


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

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
            priorityTextView = (TextView) itemView.findViewById(R.id.priorityTextView)
        }
    }

}
