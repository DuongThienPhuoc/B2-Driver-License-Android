package com.fpt.phuocdt.b2driverslicense.database;

import java.io.Serializable;

public class UserAnswer implements Serializable {
    private String questionId;
    private boolean isCorrect;


    public UserAnswer(String questionId, boolean isCorrect) {
        this.questionId = questionId;
        this.isCorrect = isCorrect;

    }

    @Override
    public String toString() {
        return "UserAnswer{" +
                "questionId='" + questionId + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public String getQuestionId() {
        return questionId;
    }

    public boolean isCorrect() {
        return isCorrect;
    }




}

