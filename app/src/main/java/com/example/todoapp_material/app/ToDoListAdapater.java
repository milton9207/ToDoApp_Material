package com.example.todoapp_material.app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Milton on 5/6/2015.
 */
public class ToDoListAdapater extends RecyclerView.Adapter<ToDoListAdapater.MyViewHolder> {

    private LayoutInflater inflater;
    private List<ToDoItem> data;

    public ToDoListAdapater(Context context, List<ToDoItem> data)
    {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public ToDoListAdapater(Context context)
    {
        inflater = LayoutInflater.from(context);
        data = new ArrayList<ToDoItem>();

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = inflater.inflate(R.layout.todo_item_row,viewGroup,false);
        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {

        ToDoItem item = data.get(i);
        viewHolder.titleView.setText(item.getTitle());
        viewHolder.priorityView.setText(item.getPriority().toString());
        viewHolder.dataView.setText(item.FORMAT.format(item.getDate()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void add(ToDoItem item)
    {
        data.add(item);
        //actualiza en tiempo real el resultado
        notifyDataSetChanged();

    }

    public ToDoItem getItem(int idx) {
        return data.get(idx);
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titleView;
        CheckBox statusCheckBox;
        TextView priorityView;
        TextView dataView;

        public MyViewHolder(View itemView) {
            super(itemView);

            titleView = (TextView) itemView.findViewById(R.id.titleView);
            statusCheckBox = (CheckBox) itemView.findViewById(R.id.statusCheckBox);
            priorityView = (TextView) itemView.findViewById(R.id.priorityView);
            dataView = (TextView) itemView.findViewById(R.id.dateView);

        }
    }
}
