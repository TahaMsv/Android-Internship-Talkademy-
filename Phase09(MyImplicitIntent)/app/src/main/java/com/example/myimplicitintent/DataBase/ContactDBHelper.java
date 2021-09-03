package com.example.myimplicitintent.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myimplicitintent.Models.ContactModel;

import java.util.ArrayList;
import java.util.List;

public class ContactDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "contact-db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_CONTACTS = "tb_contacts";
    private String CMD = "CREATE TABLE IF NOT EXISTS '" + TABLE_CONTACTS + "'( '" +
            ContactModel.KEY_NAME + "' TEXT, '" +
            ContactModel.KEY_ID + "' TEXT, '" +
            ContactModel.KEY_MOBILE_NUMBER + "' TEXT" +
            ")";


    public ContactDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CMD);
        Log.i("database", "table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        Log.i("database", "table dropped");
        onCreate(db);
    }

    public boolean addContact(ContactModel contactModel) {
        ContentValues cv = new ContentValues();
        initContentValue(contactModel, cv);
        SQLiteDatabase db = getWritableDatabase();
        long result = db.insert(TABLE_CONTACTS, null, cv);
        if (db.isOpen()) db.close();
        return result != -1;
    }

    public List<ContactModel> getAllContacts() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM '" + TABLE_CONTACTS + "'";
        Cursor cursor = db.rawQuery(query, null);
        List<ContactModel> returnedList = generateContactList(cursor, cursor.moveToFirst());
        cursor.close();
        if (db.isOpen()) db.close();
        return returnedList;
    }

    public void deleteContact(String id) {
        SQLiteDatabase db = getWritableDatabase();
        String query = ContactModel.KEY_ID + " = ?";
        int count = db.delete(TABLE_CONTACTS, query, new String[]{id});
        if (db.isOpen()) db.close();
    }

    public void updateContact(ContactModel ContactModel) {
        String id = ContactModel.getId();
        String query = ContactModel.KEY_ID + " = " + id;
        ContentValues cv = new ContentValues();
        initContentValue(ContactModel, cv);
        SQLiteDatabase db = getWritableDatabase();
        int count = db.update(TABLE_CONTACTS, cv, query, null);
        if (db.isOpen()) db.close();
    }

    private void initContentValue(ContactModel ContactModel, ContentValues cv) {
        cv.put(ContactModel.KEY_NAME, ContactModel.getName());
        cv.put(ContactModel.KEY_ID, ContactModel.getId());
        cv.put(ContactModel.KEY_MOBILE_NUMBER, ContactModel.getMobileNumber());
    }


    private List<ContactModel> generateContactList(Cursor cursor, boolean b) {
        List<ContactModel> returnedList = new ArrayList<>();
        if (b) {
            do {
                ContactModel contactModel = new ContactModel(
                        cursor.getString(cursor.getColumnIndex(ContactModel.KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(ContactModel.KEY_NAME)),
                        cursor.getString(cursor.getColumnIndex(ContactModel.KEY_MOBILE_NUMBER)), null

                );
                returnedList.add(contactModel);
            } while (cursor.moveToNext());
        }
        return returnedList;
    }


}
