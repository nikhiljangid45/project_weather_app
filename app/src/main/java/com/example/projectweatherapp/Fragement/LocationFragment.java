package com.example.projectweatherapp.Fragement;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectweatherapp.R;
import com.example.projectweatherapp.RoomDatabase.Note;
import com.example.projectweatherapp.RoomDatabase.NoteDatabase;
import com.example.projectweatherapp.RoomDatabase.RoomAdapter;
import com.example.projectweatherapp.adapter.InteFaceItemClick;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class LocationFragment extends Fragment {

    RoomAdapter roomAdapter;
    ArrayList<Note> arrayList;
    RecyclerView recyclerView;
    NoteDatabase noteDatabase;
    Note note;
    Executor executor;
    LocationFragment locationFragment;

    Executor executors;
    InteFaceItemClick inteFaceItemClick;
    public LocationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location, container, false);


        executors = Executors.newSingleThreadExecutor();
        inteFaceItemClick = (InteFaceItemClick) requireActivity();

        recyclerView = view.findViewById(R.id.fRecycleView);
        arrayList = new ArrayList<>();
        noteDatabase = NoteDatabase.getInstance(getContext());
        executor =  Executors.newSingleThreadExecutor();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        noteDatabase.noteDao().getAllData().observe((LifecycleOwner) getContext(), new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                arrayList = (ArrayList<Note>) notes;
                recyclerView.setAdapter(new RoomAdapter(getContext(),arrayList,inteFaceItemClick));
            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                if (direction == ItemTouchHelper.RIGHT){
                    Note note = arrayList.get(viewHolder.getAdapterPosition());
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            noteDatabase.noteDao().delete(note);

                        }
                    });
                }
            }
        }).attachToRecyclerView(recyclerView);

        return view;
    }
}