package com.example.mysql;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    public static final String DELETE = "Delete";
    public static final String EDIT = "Edit";
    
    private List<StudentModel> myList;

    public MyAdapter(List<StudentModel> items) {
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
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(holder.itemView.getContext()).create();
                alertDialog.setMessage("What do you want to do?");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, DELETE,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                StudentDBHelper dbHelper = new StudentDBHelper(holder.itemView.getContext());
                                dbHelper.deleteStudent(myList.get(position).getCode());
                                myList.remove(position);
                                notifyItemChanged(position);
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, EDIT,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                AppCompatActivity activity = (AppCompatActivity) holder.itemView.getContext();
                                FormFragment formFragment = new FormFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString(FirstFragment.ADD_OR_EDIT, FirstFragment.EDIT);
                                bundle.putSerializable(FirstFragment.STUDENT_CODE, myList.get(position).getCode());
                                formFragment.setArguments(bundle);
                                activity.getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.my_placeHolder, formFragment)
                                        .addToBackStack(null)
                                        .commit();
                                dialog.dismiss();
                            }
                        });

                alertDialog.show();
                return true;
            }
        });
        holder.bind(myList.get(position));
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView title_name;
        AppCompatTextView title_lastName;
        AppCompatTextView title_code;
        AppCompatTextView title_score;
        AppCompatTextView title_gender;


        MyViewHolder(View itemView) {
            super(itemView);
            title_name = itemView.findViewById(R.id.textView_name);
            title_lastName = itemView.findViewById(R.id.textView_last_name);
            title_code = itemView.findViewById(R.id.textView_code);
            title_score = itemView.findViewById(R.id.textView_score);
            title_gender = itemView.findViewById(R.id.textView_gender);
        }

        public void bind(StudentModel myItem) {
            title_name.setText(myItem.getName());
            title_lastName.setText(myItem.getLastName());
            title_code.setText(myItem.getCode());
            title_score.setText(String.valueOf(myItem.getScore()));
            title_gender.setText(String.valueOf(myItem.getGender()));
        }

    }


}


