package com.example.myimplicitintent.Fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.myimplicitintent.Models.ContactModel;
import com.example.myimplicitintent.R;

public class DetailsFragment extends Fragment {

    public static DetailsFragment newInstance() {
        return new DetailsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tv_name = view.findViewById(R.id.textView_name);
        TextView tv_mobile = view.findViewById(R.id.textView_mobile);
        TextView tv_id = view.findViewById(R.id.textView_id);
        final ImageView imageView = view.findViewById(R.id.image_view);
        ContactModel contactModel = new ContactModel();

        if (getArguments() != null) {
            contactModel = (ContactModel) getArguments().getSerializable(ListFragment.MODEL);
        }
        if (contactModel != null) {
            String name = contactModel.getName();
            String id = contactModel.getId();
            String mobile = contactModel.getMobileNumber();
            final Bitmap photo = contactModel.getPhoto();
            tv_name.setText(name);
            tv_mobile.setText(mobile);
            tv_id.setText(id);
            if (photo != null && getContext() != null) {
                Glide.with(getContext()).load(photo)
                        .into(imageView);//todo check ****
            }
        }

    }

}
