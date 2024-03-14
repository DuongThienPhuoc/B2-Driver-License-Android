package com.fpt.phuocdt.b2driverslicense;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.fpt.phuocdt.b2driverslicense.adapter.ScreenSlidePagerAdapter;
import com.fpt.phuocdt.b2driverslicense.api.ApiServices;
import com.fpt.phuocdt.b2driverslicense.database.UserAnswer;
import com.fpt.phuocdt.b2driverslicense.database.UserAnswerManager;
import com.fpt.phuocdt.b2driverslicense.entity.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScreenSlideActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private ScreenSlidePagerAdapter pagerAdapter;
    private List<Question> questionList;
    private UserAnswerManager userAnswerManager;
    private TextView tvKiemTra;
    private TextView tvTimer;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 1320000;
    private int correctAnswerCount = 0;
    private void bindingView() {
        tvKiemTra = findViewById(R.id.tvKiemTra);
        tvTimer = findViewById(R.id.tvTimer);
    }
    private void bindingAction() {
        tvKiemTra.setOnClickListener(this::onKiemTraClick);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        questionList = new ArrayList<>();
        userAnswerManager = UserAnswerManager.getInstance();
        bindingView();
        bindingAction();
        OnBackPressedCallback callBack = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                checkCorrectAnswersInDatabase();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callBack);
        Intent intent = getIntent();
        int selectedTopicPosition = intent.getIntExtra("selected_topic_position", 1);
        String topicID = String.valueOf(selectedTopicPosition);
        Call<List<Question>> call = ApiServices.getPostApiEnpoint().getQuestionByTopic(topicID);
        call.enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                if (response.isSuccessful()) {
                    questionList = response.body();
                    displayQuestions(questionList);
                } else {
                    Toast.makeText(ScreenSlideActivity.this, "Failed to get questions!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
                Toast.makeText(ScreenSlideActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                tvTimer.setText("00:00");
                checkScore();
                showScoreDialog(correctAnswerCount);
            }
        }.start();
    }
    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        tvTimer.setText(timeLeftFormatted);
    }
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
    public void onKiemTraClick(View v) {
        checkCorrectAnswersInDatabase();
    }
    private void checkScore() {
        List<UserAnswer> userAnswers = userAnswerManager.getUserAnswers();
        for (UserAnswer userAnswer : userAnswers) {
            if (userAnswer.isCorrect()) {
                correctAnswerCount++;
            }
        }
    }
    private void checkCorrectAnswersInDatabase() {
        checkScore();
        Log.d("CorrectAnswers", "Number of correct answers: " + correctAnswerCount);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Kết quả kiểm tra");
        builder.setMessage("Bạn có muốn kết thúc bài kiểm tra không ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showScoreDialog(correctAnswerCount);
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void showScoreDialog(int correctAnswerCount) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Điểm của bạn");
        String message;
        boolean passed = correctAnswerCount >= 32;
        if (passed) {
            message = "Chúc mừng bạn đã vượt qua!!!!";
        } else {
            message = "Bạn đã thi trượt!";
        }
        message += "\nBạn đã trả lời đúng " + correctAnswerCount + " câu.";

        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                navigateToQuizList();
                userAnswerManager.resetUserAnswers();
            }
        });
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void navigateToQuizList() {
        Intent intent = new Intent(ScreenSlideActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void displayQuestions(List<Question> questions) {
        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), getLifecycle(), questions);
        viewPager.setAdapter(pagerAdapter);
    }
}
