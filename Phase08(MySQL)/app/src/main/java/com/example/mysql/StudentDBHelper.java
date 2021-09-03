package com.example.mysql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class StudentDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "student-db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_STUDENTS = "tb_students";
    private String CMD = "CREATE TABLE IF NOT EXISTS '" + TABLE_STUDENTS + "'( '" +
            StudentModel.KEY_NAME + "' TEXT, '" +
            StudentModel.KEY_LAST_NAME + "' TEXT, '" +
            StudentModel.KEY_CODE + "' TEXT PRIMARY KEY, '" +
            StudentModel.KEY_SCORE + "' NUMERIC, '" +
            StudentModel.KEY_GENDER + "' TEXT" +
            ")";


    public StudentDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CMD);
        Log.i("database", "table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        Log.i("database", "table dropped");
        onCreate(db);
    }

    public boolean addStudent(StudentModel studentModel) {
        ContentValues cv = new ContentValues();
        initContentValue(studentModel, cv);
        SQLiteDatabase db = getWritableDatabase();
        long result = db.insert(TABLE_STUDENTS, null, cv);
        if (db.isOpen()) db.close();
        return result != -1;
    }

    public List<StudentModel> getAllStudents() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM '" + TABLE_STUDENTS + "'";
        Cursor cursor = db.rawQuery(query, null);
        List<StudentModel> returnedList = generateStudentList(cursor, cursor.moveToFirst());
        cursor.close();
        if (db.isOpen()) db.close();
        return returnedList;
    }

    public void deleteStudent(String code) {
        SQLiteDatabase db = getWritableDatabase();
        String query = StudentModel.KEY_CODE + " = ?";
        int count = db.delete(TABLE_STUDENTS, query, new String[]{code});
        if (db.isOpen()) db.close();
    }

    public void updateStudent(StudentModel studentModel) {
        String code = studentModel.getCode();
        String query = StudentModel.KEY_CODE + " = " + code;
        ContentValues cv = new ContentValues();
        initContentValue(studentModel, cv);
        SQLiteDatabase db = getWritableDatabase();
        int count = db.update(TABLE_STUDENTS, cv, query, null);
        if (db.isOpen()) db.close();
    }

    private void initContentValue(StudentModel studentModel, ContentValues cv) {
        cv.put(StudentModel.KEY_NAME, studentModel.getName());
        cv.put(StudentModel.KEY_LAST_NAME, studentModel.getLastName());
        cv.put(StudentModel.KEY_CODE, studentModel.getCode());
        cv.put(StudentModel.KEY_SCORE, studentModel.getScore());
        cv.put(StudentModel.KEY_GENDER, studentModel.getGender());
    }

    public boolean findStudentByCode(String code) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_STUDENTS + " WHERE " + StudentModel.KEY_CODE + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{code});
        boolean isExist = cursor.moveToFirst();
        cursor.close();
        if (db.isOpen()) db.close();
        return isExist;
    }

    public StudentModel getStudentByCode(String code) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_STUDENTS + " WHERE " + StudentModel.KEY_CODE + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{code});
        List<StudentModel> returnedList = generateStudentList(cursor, cursor.moveToFirst());
        cursor.close();
        if (db.isOpen()) db.close();
        return returnedList.get(0);
    }

    public List<StudentModel> getAllStudentsByGender(String gender) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_STUDENTS + " WHERE " + StudentModel.KEY_GENDER + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{gender});
        List<StudentModel> returnedList = generateStudentList(cursor, cursor.moveToFirst());
        cursor.close();
        if (db.isOpen()) db.close();
        return returnedList;
    }

    public List<StudentModel> getAllByOrder(String orderType) {
        SQLiteDatabase db = getReadableDatabase();
        String query;
        Cursor cursor = null;
        switch (orderType) {
            case StudentModel.KEY_SCORE:
                query = "SELECT * FROM " + TABLE_STUDENTS + " ORDER BY " + StudentModel.KEY_SCORE + " DESC";
                cursor = db.rawQuery(query, new String[]{});
                break;
            case StudentModel.KEY_NAME:
                query = "SELECT * FROM " + TABLE_STUDENTS + " ORDER BY " + StudentModel.KEY_NAME + " ASC";
                cursor = db.rawQuery(query, new String[]{});
                break;
            case StudentModel.KEY_LAST_NAME:
                query = "SELECT * FROM " + TABLE_STUDENTS + " ORDER BY " + StudentModel.KEY_LAST_NAME + " ASC";
                cursor = db.rawQuery(query, new String[]{});
                break;
            case StudentModel.KEY_GENDER:
                List<StudentModel> females = getAllStudentsByGender(StudentModel.KEY_GENDER_FEMALE);
                List<StudentModel> males = getAllStudentsByGender(StudentModel.KEY_GENDER_MAlE);
                List<StudentModel> returnedList = new ArrayList<>();
                returnedList.addAll(females);
                returnedList.addAll(males);
                return returnedList;
        }

        List<StudentModel> returnedList = new ArrayList<>();
        if (cursor != null) {
            returnedList = generateStudentList(cursor, cursor.moveToFirst());
            cursor.close();
        }
        if (db.isOpen()) db.close();
        return returnedList;
    }

    private List<StudentModel> generateStudentList(Cursor cursor, boolean b) {
        List<StudentModel> returnedList = new ArrayList<>();
        if (b) {
            do {
                StudentModel studentModel = new StudentModel(
                        cursor.getString(cursor.getColumnIndex(StudentModel.KEY_NAME)),
                        cursor.getString(cursor.getColumnIndex(StudentModel.KEY_LAST_NAME)),
                        cursor.getString(cursor.getColumnIndex(StudentModel.KEY_CODE)),
                        cursor.getDouble(cursor.getColumnIndex(StudentModel.KEY_SCORE)),
                        cursor.getString(cursor.getColumnIndex(StudentModel.KEY_GENDER))
                );
                returnedList.add(studentModel);
            } while (cursor.moveToNext());
        }
        return returnedList;
    }



}
