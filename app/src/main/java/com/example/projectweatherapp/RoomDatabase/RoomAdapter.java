package com.example.projectweatherapp.RoomDatabase;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.projectweatherapp.R;
import com.example.projectweatherapp.adapter.InteFaceItemClick;

import java.util.ArrayList;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    Context context;
    ArrayList<Note> noteArrayList;

    InteFaceItemClick inteFaceItemClick ;


    public RoomAdapter(Context context, ArrayList<Note> noteArrayList,InteFaceItemClick inteFaceItemClick) {
        this.context = context;
        this.noteArrayList = noteArrayList;
        this.inteFaceItemClick = inteFaceItemClick;
    }


    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.room_item,parent,false);

      return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {


        Note note = noteArrayList.get(position);
        holder.textView.setText(note.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inteFaceItemClick.clickTheItem(note.getName());
            }
        });


    }

    @Override
    public int getItemCount() {
        return noteArrayList.size();
    }



    public class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
