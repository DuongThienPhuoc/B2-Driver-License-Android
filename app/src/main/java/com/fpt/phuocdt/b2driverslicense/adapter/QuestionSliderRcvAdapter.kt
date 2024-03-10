package com.fpt.phuocdt.b2driverslicense.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.fpt.phuocdt.b2driverslicense.R
import androidx.constraintlayout.widget.ConstraintLayout

class QuestionSliderRcvAdapter(
    private val questions: List<Int>,
    private val setOnClickQuestion: (question: Int) -> Unit
) :
    RecyclerView.Adapter<QuestionSliderRcvAdapter.QuestionSliderRcvAdapterViewHolder>() {
    private var selectedQuestion = -1

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
        holder.questionButton.setOnClickListener {
            if (selectedQuestion != -1) {
                notifyItemChanged(selectedQuestion)
            }
            holder.questionButton.background = AppCompatResources.getDrawable(
                holder.questionButton.context,
                R.drawable.question_slider_selected
            )
            selectedQuestion = holder.adapterPosition
            setOnClickQuestion(question)
        }
        if (selectedQuestion != -1) {
            holder.questionButton.background = AppCompatResources.getDrawable(
                holder.questionButton.context,
                R.drawable.question_slider_item_backgroud
            )
        }
    }

    class QuestionSliderRcvAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val questionItem: TextView = view.findViewById(R.id.questionNumber)
        val questionButton: ConstraintLayout = view.findViewById(R.id.question)
    }
}