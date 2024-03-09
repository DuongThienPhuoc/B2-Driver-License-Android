package com.fpt.phuocdt.b2driverslicense.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fpt.phuocdt.b2driverslicense.R

class QuestionSliderRcvAdapter(private val questions: List<Int>) :
    RecyclerView.Adapter<QuestionSliderRcvAdapter.QuestionSliderRcvAdapterViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuestionSliderRcvAdapterViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.question_item, parent, false)
        return QuestionSliderRcvAdapterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    override fun onBindViewHolder(holder: QuestionSliderRcvAdapterViewHolder, position: Int) {
        val question = questions[position]
        holder.questionItem.text = question.toString()
    }

    class QuestionSliderRcvAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val questionItem: TextView = view.findViewById(R.id.questionNumber)
    }
}