package com.example.mvvm_databinding;

import android.database.DatabaseUtils;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.mvvm_databinding.databinding.FragmentLeftBinding;

public class LeftFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentLeftBinding binding=DataBindingUtil.inflate(inflater,R.layout.fragment_left,container,false);
        binding.setViewModel(AppViewModel.getInstance());
        return binding.getRoot();
    }
}
