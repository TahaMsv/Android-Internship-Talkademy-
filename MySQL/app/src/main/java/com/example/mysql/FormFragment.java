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


        String editOrAdd = "";
        String student_code = "";
        if (getArguments() != null) {
            editOrAdd = getArguments().getString(FirstFragment.ADD_OR_EDIT);
            student_code = getArguments().getString(FirstFragment.STUDENT_CODE);
        }
        if (FirstFragment.EDIT.equals(editOrAdd)) {
            StudentModel studentModel = dbHelper.getStudentByCode(student_code);
            nameET.setText(studentModel.getName());
            lastNameET.setText(studentModel.getLastName());
            codeET.setText(studentModel.getCode());
            codeET.setEnabled(false);
            scoreET.setText(String.valueOf(studentModel.getScore()));
            String gender = studentModel.getGender();
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
                boolean isExisted = dbHelper.findStudentByCode(code);
                if (FirstFragment.EDIT.equals(finalEditOrAdd)) {
                    dbHelper.updateStudent(studentModel);
                } else {
                    if (isExisted) {
                        Toast.makeText(getContext(), "Student already exist", Toast.LENGTH_LONG).show();
                    } else {
                        dbHelper.addStudent(studentModel);
                    }
                }

            }
        });
    }
}
