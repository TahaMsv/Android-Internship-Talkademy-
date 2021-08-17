package com.example.mysql;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = StudentDBHelper.TABLE_STUDENTS)
public class StudentEntity {
    @PrimaryKey
    @NonNull
    private String code;
    @ColumnInfo(name = StudentModel.KEY_NAME)
    private String name;
    @ColumnInfo(name = StudentModel.KEY_LAST_NAME)
    private String lastName;

    @ColumnInfo(name = StudentModel.KEY_SCORE)
    private double score;
    @ColumnInfo(name = StudentModel.KEY_GENDER)
    private String gender;

    public StudentEntity(String name, String lastName, String code, double score, String gender) {
        this.name = name;
        this.lastName = lastName;
        this.code = code;
        this.score = score;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
