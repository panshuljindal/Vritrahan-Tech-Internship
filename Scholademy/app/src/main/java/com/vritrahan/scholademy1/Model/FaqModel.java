package com.vritrahan.scholademy1.Model;

public class FaqModel {
    String faqQuestion;
    String faqAnswer;

    public FaqModel(String faqQuestion, String faqAnswer) {
        this.faqQuestion = faqQuestion;
        this.faqAnswer = faqAnswer;
    }

    public String getFaqQuestion() {
        return faqQuestion;
    }

    public void setFaqQuestion(String faqQuestion) {
        this.faqQuestion = faqQuestion;
    }

    public String getFaqAnswer() {
        return faqAnswer;
    }

    public void setFaqAnswer(String faqAnswer) {
        this.faqAnswer = faqAnswer;
    }
}
