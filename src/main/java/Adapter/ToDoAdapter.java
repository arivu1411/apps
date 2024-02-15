package Adapter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.AddNewTask;
import com.example.todolist.MainActivity;
import com.example.todolist.R;

import java.util.List;

import DataBase.ToDoDataBaseHelper;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {
    public List<ToDoLists> toDoLists;
    public MainActivity activity;
    public ToDoDataBaseHelper database;

    public ToDoAdapter(MainActivity activity, ToDoDataBaseHelper database) {
        this.activity = activity;
        this.database = database;
    }


    @NonNull
    @Override
    public ToDoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoAdapter.ViewHolder holder, int position) {

        database.openDatabase();

        ToDoLists lists=toDoLists.get(position);
        holder.texts.setText(lists.getTask());
        holder.texts.setChecked(toBoolean(lists.getStatus()));
        holder.date.setText(lists.getDate());
        holder.time.setText(lists.getTime());

        holder.texts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    database.updateStatus(lists.getId(),1);
                }else {
                    database.updateStatus(lists.getId(),0);
                }
            }
        });

    }

    public boolean toBoolean(int n){
        return n!=0;
    }

    @Override
    public int getItemCount() {
        return toDoLists.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setTasks(List<ToDoLists> toDoLists){
        this.toDoLists=toDoLists;
        notifyDataSetChanged();
    }
       //delete item
    public void deleteItem(int position){
        ToDoLists item=toDoLists.get(position);
        database.deleteTask(item.getId());
        toDoLists.remove(position);
        notifyItemRemoved(position);
    }
    //edit item
    public void editItem(int position){
        ToDoLists item=toDoLists.get(position);
        Bundle bundle=new Bundle();
        bundle.putInt("id",item.getId());
        bundle.putString("task",item.getTask());
        AddNewTask fragment=new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(),AddNewTask.TAG);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox texts;
        TextView date,time;
        @SuppressLint("WrongViewCast")
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            texts=itemView.findViewById(R.id.checkbox);
            date=itemView.findViewById(R.id.dateTextView);
            time=itemView.findViewById(R.id.TimeTextView);
        }
    }
}
