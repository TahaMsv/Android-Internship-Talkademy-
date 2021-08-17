package com.example.mysql;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;
import java.util.Objects;

public class ListFragment extends Fragment {
    private List<StudentModel> students;
    private StudentDBHelper dbHelper;
    private MyAdapter myAdapter;

    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        dbHelper = new StudentDBHelper(getContext());
        students = dbHelper.getAllStudents();
        myAdapter = new MyAdapter(students);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myAdapter);

        Spinner dropdown = view.findViewById(R.id.spinner1);
        String[] items = new String[]{"All", StudentModel.KEY_GENDER_MAlE, StudentModel.KEY_GENDER_FEMALE, StudentModel.KEY_SCORE, StudentModel.KEY_NAME, StudentModel.KEY_LAST_NAME, StudentModel.KEY_GENDER};
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = (String) parentView.getItemAtPosition(position);
                List<StudentModel> temp;
                switch (position) {
                    case 0:
                        temp = dbHelper.getAllStudents();
                        break;
                    case 1:
                    case 2:
                        temp = dbHelper.getAllStudentsByGender(selectedItem);
                        break;
                    default:
                        temp = dbHelper.getAllByOrder(selectedItem);
                        break;
                }
                students.clear();
                students.addAll(temp);
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
        dropdown.setAdapter(adapter);
    }



}
