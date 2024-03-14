package com.fpt.phuocdt.b2driverslicense.adapter;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpt.phuocdt.b2driverslicense.R;

public class TopicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView topicName;

    private void bindingView() {
        topicName = itemView.findViewById(R.id.topicName);
    }

    private void bindingAction() {
        itemView.setOnClickListener(this);
    }

    private void onItemClick(View view) {
        String data = topicName.getText().toString();
        Toast.makeText(itemView.getContext(), data, Toast.LENGTH_SHORT).show();
    }

    public TopicViewHolder(@NonNull View itemView) {
        super(itemView);
        bindingView();
        bindingAction();
    }

    public void bindData(String topic) {
        topicName.setText(topic);
    }

    @Override
    public void onClick(View v) {
        onItemClick(v);

    }
}
