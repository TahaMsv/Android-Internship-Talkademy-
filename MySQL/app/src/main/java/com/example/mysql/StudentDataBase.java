package com.example.mysql;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = StudentEntity.class, exportSchema = false, version = 1)
public abstract class StudentDataBase extends RoomDatabase {
    public static final String DB_NAME="student_db";
    public abstract StudentDao studentDao();
}
