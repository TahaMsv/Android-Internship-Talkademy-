package com.example.fragmentrecyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<MyItem> myList;

    public MyAdapter(List<MyItem> items) {
        if (items == null) {
            this.myList = new ArrayList<>();
        }
        this.myList = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(myList.get(position));

    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView title;

        MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView);

        }

        public void bind(MyItem myItem) {
            title.setText(myItem.getTitle());
        }

    }
}
