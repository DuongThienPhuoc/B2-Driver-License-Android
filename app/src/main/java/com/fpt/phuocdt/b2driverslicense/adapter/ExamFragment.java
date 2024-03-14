package com.fpt.phuocdt.b2driverslicense.adapter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.fpt.phuocdt.b2driverslicense.R;
import com.fpt.phuocdt.b2driverslicense.database.UserAnswer;
import com.fpt.phuocdt.b2driverslicense.database.UserAnswerManager;
import com.fpt.phuocdt.b2driverslicense.entity.Answer;
import com.fpt.phuocdt.b2driverslicense.entity.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExamFragment extends Fragment {
    private Question question;
    private static int position;
    private List<Answer> answers = new ArrayList<>();
    private UserAnswerManager userAnswerManager;
    private static final String ARG_QUESTION_POSITION = "position";
    private static final String ARG_QUESTION_OBJECT = "question";

    @NonNull
    public static ExamFragment newInstance(Question question, int position) {
        ExamFragment fragment = new ExamFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_QUESTION_OBJECT, question);
        args.putInt(ARG_QUESTION_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            question = (Question) getArguments().getSerializable(ARG_QUESTION_OBJECT);
            position = getArguments().getInt(ARG_QUESTION_POSITION);
            answers = question.getAnswers();
            userAnswerManager = UserAnswerManager.getInstance();
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_slider, container, false);
        initializeViews(rootView);
        return rootView;
    }

    private void initializeViews(View rootView) {
        TextView tvNum = rootView.findViewById(R.id.tvNum);
        TextView tvQuestion = rootView.findViewById(R.id.tvQuestion);
        ImageView ivIcon = rootView.findViewById(R.id.ivIcon);
        RadioGroup radGroup = rootView.findViewById(R.id.radGroup);
        RadioButton radA = rootView.findViewById(R.id.radA);
        RadioButton radB = rootView.findViewById(R.id.radB);
        RadioButton radC = rootView.findViewById(R.id.radC);
        RadioButton radD = rootView.findViewById(R.id.radD);
        tvNum.setText("Câu " + (position + 1));
        tvQuestion.setText(question.getQuestion());
        String imageUrl = question.getImg();
        if (imageUrl != null && !imageUrl.isEmpty()) {

            ivIcon.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.loading)
                    .into(ivIcon);
        } else {

            ivIcon.setVisibility(View.GONE);
        }

        List<Answer> answers = question.getAnswers();
        if (answers != null && !answers.isEmpty()) {
            int answerCount = answers.size();
            if (answerCount >= 4) {
                radA.setText(answers.get(0).getText());
                radB.setText(answers.get(1).getText());
                radC.setText(answers.get(2).getText());
                radD.setText(answers.get(3).getText());
                radA.setVisibility(View.VISIBLE);
                radB.setVisibility(View.VISIBLE);
                radC.setVisibility(View.VISIBLE);
                radD.setVisibility(View.VISIBLE);
            } else if (answerCount == 3) {
                radA.setText(answers.get(0).getText());
                radB.setText(answers.get(1).getText());
                radC.setText(answers.get(2).getText());
                radA.setVisibility(View.VISIBLE);
                radB.setVisibility(View.VISIBLE);
                radC.setVisibility(View.VISIBLE);
                radD.setVisibility(View.GONE);
            } else {
                radA.setText(answers.get(0).getText());
                radB.setText(answers.get(1).getText());
                radA.setVisibility(View.VISIBLE);
                radB.setVisibility(View.VISIBLE);
                radC.setVisibility(View.GONE);
                radD.setVisibility(View.GONE);
            }
        }
        List<UserAnswer> userAnswers = new ArrayList<>();
        radGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = rootView.findViewById(checkedId);
                if (checkedRadioButton != null) {
                    int position = radGroup.indexOfChild(checkedRadioButton);
                    if (position >= 0 && position < answers.size()) {
                        String questionId = question.get_id();
                        boolean isCorrect = answers.get(position).isCorrect();
                        UserAnswer userAnswer = new UserAnswer(questionId, isCorrect);
                        if (userAnswerManager.isQuestionExists(questionId)) {
                            userAnswerManager.updateUserAnswer(questionId, isCorrect);
                        } else {
                            userAnswerManager.addUserAnswer(userAnswer);
                        }

                        Log.d("UserAnswers", "User answers: " + Arrays.toString(userAnswerManager.getUserAnswers().toArray()));

                    }
                }
            }
        });

    }
}

