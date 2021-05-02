package com.panshul.scholademy.Model;

public class Profile {
    String name;
    String email;
    String phone;
    String age;
    String uid;
    String exams;
    String type;
    String nameOfCoaching;
    String courseName;
    String fees;
    String city;
    String registerNcst;
    public Profile(){

    }

    public Profile(String name, String email, String phone, String age, String uid, String exams, String type, String nameOfCoaching, String courseName, String fees, String city, String registerNcst) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.uid = uid;
        this.exams = exams;
        this.type = type;
        this.nameOfCoaching = nameOfCoaching;
        this.courseName = courseName;
        this.fees = fees;
        this.city = city;
        this.registerNcst = registerNcst;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getExams() {
        return exams;
    }

    public void setExams(String exams) {
        this.exams = exams;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNameOfCoaching() {
        return nameOfCoaching;
    }

    public void setNameOfCoaching(String nameOfCoaching) {
        this.nameOfCoaching = nameOfCoaching;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegisterNcst() {
        return registerNcst;
    }

    public void setRegisterNcst(String registerNcst) {
        this.registerNcst = registerNcst;
    }
}
