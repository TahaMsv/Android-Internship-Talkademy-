package com.example.mysql;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface StudentDao {
    @Query("Select * from " + StudentDBHelper.TABLE_STUDENTS)
    List<StudentEntity> getStudentList();

    @Insert
    void insertStudent(StudentEntity student);

    @Update
    void updateStudent(StudentEntity student);

    @Delete
    void deleteStudent(StudentEntity student);

    @Query("SELECT * FROM " + StudentDBHelper.TABLE_STUDENTS + " where " + StudentModel.KEY_CODE + " = :code")
    StudentEntity getStudentByCode(String code);

    @Query("SELECT * FROM " + StudentDBHelper.TABLE_STUDENTS + " where " + StudentModel.KEY_GENDER + " = :gender")
    List<StudentEntity> getAllStudentsByGender(String gender);

    @Query("SELECT * FROM " + StudentDBHelper.TABLE_STUDENTS + " ORDER BY " + StudentModel.KEY_SCORE + " DESC")
    List<StudentEntity> getAllOrderByScore();

    @Query("SELECT * FROM " + StudentDBHelper.TABLE_STUDENTS + " ORDER BY " + StudentModel.KEY_NAME + " ASC")
    List<StudentEntity> getAllOrderByName();

    @Query("SELECT * FROM " + StudentDBHelper.TABLE_STUDENTS + " ORDER BY " + StudentModel.KEY_LAST_NAME + " ASC")
    List<StudentEntity> getAllOrderByLastName();

    @Query("SELECT * FROM " + StudentDBHelper.TABLE_STUDENTS + " WHERE " + StudentModel.KEY_CODE + "= :code")
    StudentEntity findStudentByCode(String code);

    //rawQuery
}

