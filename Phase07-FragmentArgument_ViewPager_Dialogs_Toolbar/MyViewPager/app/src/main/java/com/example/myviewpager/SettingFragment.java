package com.example.myviewpager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

public class SettingFragment extends Fragment {

    DataModel dataModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataModel = DataModel.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_setting, container, false);


        return viewRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CheckBox asiaCheckBox = view.findViewById(R.id.Asia_checkBox);
        CheckBox africaCheckBox = view.findViewById(R.id.Africa_checkBox);
        CheckBox americaCheckBox = view.findViewById(R.id.America_checkBox);
        CheckBox europeCheckBox = view.findViewById(R.id.Europe_checkBox);
        CheckBox australiaCheckBox = view.findViewById(R.id.Australia_checkBox);

        asiaCheckBox.setChecked(DataModel.isIsAsianAdded());
        africaCheckBox.setChecked(DataModel.isIsAfricanAdded());
        americaCheckBox.setChecked(DataModel.isIsAmericanAdded());
        europeCheckBox.setChecked(DataModel.isIsEuropeanAdded());
        australiaCheckBox.setChecked(DataModel.isIsAustralianAdded());

        asiaCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    DataModel.addAsians();
                } else {
                    DataModel.removeAsians();
                }
                DataModel.setIsAsianAdded(isChecked);
            }
        });

        africaCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    DataModel.addAfricans();
                } else {
                    DataModel.removeAfricans();
                }
                DataModel.setIsAfricanAdded(isChecked);
            }
        });

        americaCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    DataModel.addAmericans();
                } else {
                    DataModel.removeAmericans();
                }
                DataModel.setIsAmericanAdded(isChecked);
            }
        });

        europeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    DataModel.addEuropeans();
                } else {
                    DataModel.removeEuropeans();
                }
                DataModel.setIsEuropeanAdded(isChecked);
            }
        });

        australiaCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    DataModel.addAustralians();
                } else {
                    DataModel.removeAustralians();
                }
                DataModel.setIsAustralianAdded(isChecked);
            }
        });


    }
}
