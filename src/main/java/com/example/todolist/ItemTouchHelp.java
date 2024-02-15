package com.example.todolist;


import android.annotation.SuppressLint;
import android.app.AlertDialog;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import Adapter.ToDoAdapter;

public class ItemTouchHelp extends ItemTouchHelper.SimpleCallback {
    private final ToDoAdapter adapter;
    public ItemTouchHelp(@NonNull ToDoAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter=adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
          final int position=viewHolder.getAdapterPosition();
          if (direction==ItemTouchHelper.LEFT){
              AlertDialog.Builder builder=new AlertDialog.Builder(adapter.activity.getApplicationContext());
              builder.setTitle("Delete Task");
              builder.setMessage("Are sure you want to delete this Task?");
              builder.setPositiveButton("Confirm", (dialog, which) -> adapter.deleteItem(position));
              builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> adapter.notifyDataSetChanged());
              AlertDialog dialog=builder.create();
              dialog.show();
         }else {
              adapter.editItem(position);
          }
    }
}
