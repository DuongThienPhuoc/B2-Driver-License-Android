package com.fpt.phuocdt.b2driverslicense.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpt.phuocdt.b2driverslicense.R;
import com.fpt.phuocdt.b2driverslicense.ScreenSlideActivity;

import java.util.ArrayList;

public class TopicAdapter extends RecyclerView.Adapter<TopicViewHolder> {

    private ArrayList<String> topics;

    public TopicAdapter(ArrayList<String> topics) {
        this.topics = topics;
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic, parent, false);
        return new TopicViewHolder(view);
    }

    @Override

    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        String topic = topics.get(position);
        holder.bindData(topic);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedTopicPosition = holder.getAdapterPosition() + 1; // Get the current adapter position and add 1
                Intent intent = new Intent(v.getContext(), ScreenSlideActivity.class);
                intent.putExtra("selected_topic_position", selectedTopicPosition);
                v.getContext().startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return topics.size();
    }
}
