package com.example.fragmentrecyclerview;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyFragment extends Fragment {

    EditText editText;
    CheckBox checkBox;
    RecyclerView recyclerView;
    List<MyItem> list = new ArrayList<>();
    MyAdapter myAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_my, container, false);
        editText = viewRoot.findViewById(R.id.editText);
        checkBox = viewRoot.findViewById(R.id.checkBox);
        recyclerView = viewRoot.findViewById(R.id.recyclerView);
        myAdapter = new MyAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myAdapter);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (checkBox.isChecked()) {
                    String title = editText.getText().toString().trim();
                    if ("talkademy".equals(title)) {
                        list.add(new MyItem(title));
                        System.out.println(title);
                        myAdapter.notifyItemInserted(list.size() - 1);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return viewRoot;


    }
}
