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
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    public static final String DELETE = "Delete";
    public static final String EDIT = "Edit";

    private List<StudentModel> myList;
    private List<StudentEntity> myList_room;

    public MyAdapter(List<StudentModel> items, List<StudentEntity> items_room) {
        if (MainActivity.SQLITE_OR_ROOM.equals(MainActivity.SQLITE)) {
            if (items == null) this.myList = new ArrayList<>();
            else this.myList = items;
        } else {
            if (items == null) this.myList_room = new ArrayList<>();
            else this.myList_room = items_room;
        }
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
                                if (MainActivity.SQLITE_OR_ROOM.equals(MainActivity.SQLITE)) {
                                    StudentDBHelper dbHelper = new StudentDBHelper(holder.itemView.getContext());
                                    dbHelper.deleteStudent(myList.get(position).getCode());
                                } else {
                                    final StudentDataBase db = Room.databaseBuilder(holder.itemView.getContext(),
                                            StudentDataBase.class, StudentDataBase.DB_NAME).allowMainThreadQueries().build();
                                    db.studentDao().deleteStudent(myList_room.get(position));
                                }

                                if (MainActivity.SQLITE_OR_ROOM.equals(MainActivity.SQLITE)) {
                                    myList.remove(position);
                                    notifyItemRangeRemoved(0,myList.size());
                                } else {
                                    myList_room.remove(position);
                                    notifyItemRangeRemoved(0,myList_room.size());
                                }


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
                                if (MainActivity.SQLITE_OR_ROOM.equals(MainActivity.SQLITE)) {
                                    bundle.putSerializable(FirstFragment.STUDENT_CODE, myList.get(position).getCode());
                                } else {
                                    bundle.putSerializable(FirstFragment.STUDENT_CODE, myList_room.get(position).getCode());
                                }
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
        if(MainActivity.SQLITE_OR_ROOM.equals(MainActivity.SQLITE)) {
            holder.bind(myList.get(position));
        }else {
            holder.bind(myList_room.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (MainActivity.SQLITE_OR_ROOM.equals(MainActivity.SQLITE)) {
            return myList.size();
        }
        return myList_room.size();
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

        public void bind(Object myItem) {
            if (myItem instanceof StudentModel) {
                System.out.println("here");
                StudentModel studentModel = (StudentModel) myItem;
                title_name.setText(studentModel.getName());
                title_lastName.setText(studentModel.getLastName());
                title_code.setText(studentModel.getCode());
                title_score.setText(String.valueOf(studentModel.getScore()));
                title_gender.setText(String.valueOf(studentModel.getGender()));
            } else {
                System.out.println("here1");
                StudentEntity studentEntity = (StudentEntity) myItem;
                title_name.setText(studentEntity.getName());
                title_lastName.setText(studentEntity.getLastName());
                title_code.setText(studentEntity.getCode());
                title_score.setText(String.valueOf(studentEntity.getScore()));
                title_gender.setText(String.valueOf(studentEntity.getGender()));
            }
        }

    }


}


