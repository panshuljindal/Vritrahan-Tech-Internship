package com.panshul.scholademy.Model;

public class MstModel {
    String examName;
    String courseName;
    String courseFees;
    int logo;
    String scholoshipAmount;
    String schlorshipUpto;

    public MstModel(String examName, String courseName, String courseFees, int logo, String scholoshipAmount, String schlorshipUpto) {
        this.examName = examName;
        this.courseName = courseName;
        this.courseFees = courseFees;
        this.logo = logo;
        this.scholoshipAmount = scholoshipAmount;
        this.schlorshipUpto = schlorshipUpto;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseFees() {
        return courseFees;
    }

    public void setCourseFees(String courseFees) {
        this.courseFees = courseFees;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public String getScholoshipAmount() {
        return scholoshipAmount;
    }

    public void setScholoshipAmount(String scholoshipAmount) {
        this.scholoshipAmount = scholoshipAmount;
    }

    public String getSchlorshipUpto() {
        return schlorshipUpto;
    }

    public void setSchlorshipUpto(String schlorshipUpto) {
        this.schlorshipUpto = schlorshipUpto;
    }
}
