package com.fpt.phuocdt.b2driverslicense;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fpt.phuocdt.b2driverslicense.adapter.TopicAdapter;

import java.util.ArrayList;

public class QuizListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quiz_list);

        recyclerView = findViewById(R.id.recyclerView);

        ArrayList<String> topics = getIntent().getStringArrayListExtra("topics");
        int selectedTopicPosition = getIntent().getIntExtra("selected_topic_position", 1);

        TopicAdapter adapter = new TopicAdapter(topics);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }
}

