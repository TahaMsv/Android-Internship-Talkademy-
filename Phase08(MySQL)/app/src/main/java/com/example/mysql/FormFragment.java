package com.example.mysql;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

public class FormFragment extends Fragment {
    public static FormFragment newInstance() {
        FormFragment fragment = new FormFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final EditText nameET = view.findViewById(R.id.editTextName);
        final EditText lastNameET = view.findViewById(R.id.editTextLastName);
        final EditText codeET = view.findViewById(R.id.editTextCode);
        final EditText scoreET = view.findViewById(R.id.editTextScore);
        final RadioGroup radioGroup = view.findViewById(R.id.radio_group);
        final StudentDBHelper dbHelper = new StudentDBHelper(getContext());
        final StudentDataBase db = Room.databaseBuilder(getContext(),
                StudentDataBase.class, StudentDataBase.DB_NAME).allowMainThreadQueries().build();

        String editOrAdd = "";
        String student_code = "";
        if (getArguments() != null) {
            editOrAdd = getArguments().getString(FirstFragment.ADD_OR_EDIT);
            student_code = getArguments().getString(FirstFragment.STUDENT_CODE);
        }
        if (FirstFragment.EDIT.equals(editOrAdd)) {
            String name = "";
            String lastName = "";
            String code = "";
            double score = 0;
            String gender = "";

            if (MainActivity.SQLITE_OR_ROOM.equals(MainActivity.SQLITE)) {
                StudentModel studentModel = dbHelper.getStudentByCode(student_code);
                name = studentModel.getName();
                lastName = studentModel.getLastName();
                code = studentModel.getCode();
                score = studentModel.getScore();
                gender = studentModel.getGender();
            } else {
                StudentEntity students = db.studentDao().getStudentByCode(student_code);
                name = students.getName();
                lastName = students.getLastName();
                code = students.getCode();
                score = students.getScore();
                gender = students.getGender();
            }
            nameET.setText(name);
            lastNameET.setText(lastName);
            codeET.setText(code);
            codeET.setEnabled(false);
            scoreET.setText(String.valueOf(score));
            if (StudentModel.KEY_GENDER_MAlE.equals(gender)) {
                RadioButton male_radio = view.findViewById(R.id.radioButton_male);
                male_radio.setChecked(true);
            } else {
                RadioButton female_radio = view.findViewById(R.id.radioButton_female);
                female_radio.setChecked(true);
            }

        }

        Button saveBtn = view.findViewById(R.id.save_button);
        final String finalEditOrAdd = editOrAdd;
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameET.getText().toString();
                String lastName = lastNameET.getText().toString();
                String code = codeET.getText().toString();
                String score = scoreET.getText().toString();
                String gender;
                int radioBtnId = radioGroup.getCheckedRadioButtonId();
                RadioButton rb = view.findViewById(radioBtnId);
                gender = StudentModel.KEY_GENDER_MAlE.equals(rb.getText().toString()) ? StudentModel.KEY_GENDER_MAlE : StudentModel.KEY_GENDER_FEMALE;
                StudentModel studentModel = new StudentModel(name, lastName, code, Double.parseDouble(score), gender);
                StudentEntity studentEntity = new StudentEntity(name, lastName, code, Double.parseDouble(score), gender);
                boolean isExisted = false;
                if (MainActivity.SQLITE_OR_ROOM.equals(MainActivity.SQLITE)) {
                    isExisted = dbHelper.findStudentByCode(code);/////Todo
                } else {
                    StudentEntity student = db.studentDao().findStudentByCode(code);
                    isExisted = student != null;
                }

                if (FirstFragment.EDIT.equals(finalEditOrAdd)) {
                    if (MainActivity.SQLITE_OR_ROOM.equals(MainActivity.SQLITE)) {
                        dbHelper.updateStudent(studentModel);
                    } else {
                        db.studentDao().updateStudent(studentEntity);
                    }

                } else {
                    if (isExisted) {
                        Toast.makeText(getContext(), "Student already exist", Toast.LENGTH_LONG).show();
                    } else {
                        if (MainActivity.SQLITE_OR_ROOM.equals(MainActivity.SQLITE)) {
                            dbHelper.addStudent(studentModel);
                        } else {

                            db.studentDao().insertStudent(studentEntity);
                        }

                    }
                }

            }
        });
    }
}
