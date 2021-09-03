package com.example.mysql;

//enum Gender {
//    MALE, FEMALE
//}

public class StudentModel {

    public static final String KEY_NAME = "name";
    public static final String KEY_LAST_NAME = "lastName";
    public static final String KEY_CODE = "code";
    public static final String KEY_SCORE = "score";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_GENDER_MAlE = "Male";
    public static final String KEY_GENDER_FEMALE = "Female";
    private String name;
    private String lastName;
    private String code;
    private double score;
    private String gender;

    public StudentModel(String name, String lastName, String code, double score, String gender) {
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
