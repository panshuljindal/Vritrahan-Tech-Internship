package com.panshul.scholademy.Model;

public class Answers {
    String qid;
    String choice;

    public Answers(String qid, String choice) {
        this.qid = qid;
        this.choice = choice;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }
}
