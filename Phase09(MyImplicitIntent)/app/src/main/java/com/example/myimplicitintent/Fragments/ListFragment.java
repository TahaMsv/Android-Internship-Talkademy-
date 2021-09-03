package com.example.myimplicitintent.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.example.myimplicitintent.Models.ContactModel;
import com.example.myimplicitintent.R;
import com.example.myimplicitintent.DataBase.ContactDBHelper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    public static final String MODEL = "ListFragment.model";
    public static final String IS_TWO_PANE = "ListFragment.isTwoPane";

    public static ListFragment newInstance(boolean isTwoPane) {
        ListFragment listFragment = new ListFragment();
        Bundle args = new Bundle();
        args.putBoolean(IS_TWO_PANE, isTwoPane);
        listFragment.setArguments(args);
        return listFragment;
    }


    private static final int REQUEST_CONTACT = 1;
    private boolean isTwoPane;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_list, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ContactDBHelper dbHelper = new ContactDBHelper(getContext());
        Button show_btn = view.findViewById(R.id.show_btn);
        Button load_btn = view.findViewById(R.id.load_btn);
        if (getArguments() != null) isTwoPane = getArguments().getBoolean(IS_TWO_PANE, false);
        load_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null && checkPermission()) {
                    List<ContactModel> contactList = getContacts(getActivity());
                    List<ContactModel> existingModels = dbHelper.getAllContacts();
                    for (int i = 0; i < contactList.size(); i++) {
                        boolean found = false;
                        for (int j = 0; j < existingModels.size(); j++) {
                            if (existingModels.get(j).getId().equals(contactList.get(i).getId())) {
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            dbHelper.addContact(contactList.get(i));
                        }
                    }
                }
            }
        });
        show_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission() && getActivity() != null) {
                    Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(intent, REQUEST_CONTACT);
                }
            }
        });

    }

    public List<ContactModel> getContacts(Context ctx) {
        List<ContactModel> list = new ArrayList<>();
        ContentResolver contentResolver = ctx.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor cursorInfo = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(ctx.getContentResolver(),
                            ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(id)));

                    Bitmap photo = null;
                    if (inputStream != null) {
                        photo = BitmapFactory.decodeStream(inputStream);
                    }
                    while (cursorInfo != null && cursorInfo.moveToNext()) {
                        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        String mobileNumber = cursorInfo.getString(cursorInfo.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        ContactModel info = new ContactModel(id, name, mobileNumber, photo);
                        list.add(info);

                    }

                    if (cursorInfo != null) cursorInfo.close();

                }
            }
            cursor.close();
        }
        return list;
    }//todo +++++++

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Activity context = getActivity();
        if (requestCode == REQUEST_CONTACT && resultCode == Activity.RESULT_OK && context != null) {
            pickContact(data, context);

        }
    }

    private void pickContact(Intent data, Activity context) {
        Bundle bundle = new Bundle();
        Uri contactData = data.getData();
        Cursor c = context.managedQuery(contactData, null, null, null, null);
        if (c.moveToFirst()) {
            String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
            String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
            String cNumber = "";
            if (hasPhone.equalsIgnoreCase("1")) {
                Cursor phones = context.getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                        null, null);
                if (phones != null) {
                    phones.moveToFirst();
                    cNumber = phones.getString(phones.getColumnIndex("data1"));
                }
            }
            String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(),
                    ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(id)));
            Bitmap photo = null;
            if (inputStream != null) {
                photo = BitmapFactory.decodeStream(inputStream);
            }

            ContactModel contactModel = new ContactModel(id, name, cNumber, photo);
            bundle.putSerializable(MODEL, contactModel);
            DetailsFragment detailsFragment = DetailsFragment.newInstance();

            detailsFragment.setArguments(bundle);
            if (getActivity() != null) {
                if (isTwoPane) {

                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame_layout, detailsFragment)
                            .commit();
                } else {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.fragment, detailsFragment).addToBackStack(null)
                            .commit();
                }
            }
        }

    }

    public boolean checkPermission() {
        if (getActivity() != null && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {

                    Toast.makeText(getActivity(), "Read contacts permission is required to function app correctly", Toast.LENGTH_LONG).show();
                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.READ_CONTACTS},
                            1);
                }

            }
        }
        return false;
    }
}
