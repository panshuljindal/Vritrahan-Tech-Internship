package com.vritrahan.scholademy1.Model;

public class NbsModel {
    String fatherName;
    String fatherProfession;
    String annualIncome;
    String incomeProof;

    public NbsModel(){

    }

    public NbsModel(String fatherName, String fatherProfession, String annualIncome, String incomeProof) {
        this.fatherName = fatherName;
        this.fatherProfession = fatherProfession;
        this.annualIncome = annualIncome;
        this.incomeProof = incomeProof;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFatherProfession() {
        return fatherProfession;
    }

    public void setFatherProfession(String fatherProfession) {
        this.fatherProfession = fatherProfession;
    }

    public String getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(String annualIncome) {
        this.annualIncome = annualIncome;
    }

    public String getIncomeProof() {
        return incomeProof;
    }

    public void setIncomeProof(String incomeProof) {
        this.incomeProof = incomeProof;
    }
}
