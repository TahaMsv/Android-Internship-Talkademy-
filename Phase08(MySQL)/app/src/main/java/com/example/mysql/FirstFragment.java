package com.example.mysql;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FirstFragment extends Fragment {


    public static final String ADD = "FirstFragment.Add";
    public static final String EDIT = "FirstFragment.Edit";
    public static final String STUDENT_CODE = "FirstFragment.student_code";
    public static final String ADD_OR_EDIT = "FirstFragment.AddOrEdit";

    public static FirstFragment newInstance() {
        return new FirstFragment();
    }

    protected Fragment createListFragment() {
        return ListFragment.newInstance();
    }

    protected Fragment createFormFragment() {
        return FormFragment.newInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button addStudentBtn = view.findViewById(R.id.add_student_btn);
        Button showStudentsBtn = view.findViewById(R.id.show_students_btn);

        addStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(createFormFragment(), true);
            }
        });

        showStudentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(createListFragment(), false);
            }
        });
    }

    private void startFragment(Fragment fragment, boolean isFormFragment) {
        Bundle bundle = new Bundle();
        if (isFormFragment) {
            bundle.putString(ADD_OR_EDIT, ADD);
        }
        fragment.setArguments(bundle);
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.my_placeHolder, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
