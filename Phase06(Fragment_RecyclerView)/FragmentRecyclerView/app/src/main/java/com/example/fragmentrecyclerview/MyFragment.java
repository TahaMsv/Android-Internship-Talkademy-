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
    public static final String TALKADEMY = "talkademy";
    private List<MyItem> list = new ArrayList<>();
    private EditText editText;
    private MyAdapter myAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.editText);
        final CheckBox checkBox = view.findViewById(R.id.checkBox);
        setUpRecyclerView(view);
        editText.addTextChangedListener(getWatcher(checkBox));
    }

    private void setUpRecyclerView(@NonNull View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        myAdapter = new MyAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myAdapter);
    }

    private TextWatcher getWatcher(final CheckBox checkBox) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!checkBox.isChecked()) return;

                String title = editText.getText().toString().trim();
                if (TALKADEMY.equals(title)) {
                    list.add(new MyItem(title));
                    System.out.println(title);
                    myAdapter.notifyItemInserted(list.size() - 1);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }
}
