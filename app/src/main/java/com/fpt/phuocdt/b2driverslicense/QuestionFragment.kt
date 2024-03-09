package com.fpt.phuocdt.b2driverslicense

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.fpt.phuocdt.b2driverslicense.entity.Question

class QuestionFragment(val question: Question) : Fragment() {
    private lateinit var questionTv: TextView
    private lateinit var answers: RadioGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_question, container, false)
        questionTv = view.findViewById(R.id.questionTv)
        answers = view.findViewById(R.id.answers)
        answers.removeAllViews();
        questionTv.text = question.question

        for (answer in question.answers) {
            val radioButton = RadioButton(context);
            radioButton.text = answer.text
            radioButton.id = View.generateViewId()
            answers.addView(radioButton)
        }
        return view
    }
}