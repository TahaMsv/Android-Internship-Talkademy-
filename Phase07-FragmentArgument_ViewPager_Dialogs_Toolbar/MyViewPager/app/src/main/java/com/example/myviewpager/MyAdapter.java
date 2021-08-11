package com.example.myviewpager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<CountryModel> myList;


    public MyAdapter(List<CountryModel> items) {
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

    class MyViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView title;

        MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAbsoluteAdapterPosition();
                    Intent intent = new Intent(v.getContext(), SecondActivity.class);
                    intent.putExtra(MainActivity.COUNTRY_ID, myList.get(position).getId());
                    v.getContext().startActivity(intent);
                }
            });

        }

        public void bind(CountryModel myItem) {
            title.setText(myItem.getName());
        }

    }


}

