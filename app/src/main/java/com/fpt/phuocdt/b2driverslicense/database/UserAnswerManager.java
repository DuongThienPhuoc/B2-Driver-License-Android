package com.fpt.phuocdt.b2driverslicense.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserAnswerManager implements Serializable {
    private static UserAnswerManager instance;
    private List<UserAnswer> userAnswers;

    private UserAnswerManager() {
        userAnswers = new ArrayList<>();
    }

    public static synchronized UserAnswerManager getInstance() {
        if (instance == null) {
            instance = new UserAnswerManager();
        }
        return instance;
    }

    public List<UserAnswer> getUserAnswers() {
        return userAnswers;
    }

    public void addUserAnswer(UserAnswer userAnswer) {
        userAnswers.add(userAnswer);
    }

    public boolean isQuestionExists(String questionId) {
        for (UserAnswer userAnswer : userAnswers) {
            if (userAnswer.getQuestionId().equals(questionId)) {
                return true;
            }
        }
        return false;
    }

    public void updateUserAnswer(String questionId, boolean isCoreect) {
        for (UserAnswer userAnswer : userAnswers) {
            if (userAnswer.getQuestionId().equals(questionId)) {
                userAnswer.setCorrect(isCoreect);
                break;
            }
        }
    }
    public void resetUserAnswers() {
        userAnswers.clear();
    }


}

