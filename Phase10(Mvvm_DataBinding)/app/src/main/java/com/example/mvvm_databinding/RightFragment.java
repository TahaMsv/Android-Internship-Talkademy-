package com.example.mvvm_databinding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.mvvm_databinding.databinding.RightFragmentBinding;

public class RightFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RightFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.right_fragment, container, false);
        binding.setViewModel(AppViewModel.getInstance());
        return binding.getRoot();
    }
}
