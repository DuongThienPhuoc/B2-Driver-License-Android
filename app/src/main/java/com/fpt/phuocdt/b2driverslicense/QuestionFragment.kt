package com.fpt.phuocdt.b2driverslicense

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.fpt.phuocdt.b2driverslicense.entity.Question

class QuestionFragment(private val question: Question) : Fragment() {
    private lateinit var llRadioGroupContainer: LinearLayout
    private lateinit var tvQuestion: TextView
    private lateinit var btnAnswer: Button
    private lateinit var questionImageView: ImageView
    private var isCorrect: Boolean? = null
    private fun bindView(view: View) {
        llRadioGroupContainer = view.findViewById(R.id.llRadioGroupContainer)
        tvQuestion = view.findViewById(R.id.questionTv)
        btnAnswer = view.findViewById(R.id.btnCheckAnswer)
        questionImageView = view.findViewById(R.id.imgViewQuestion)
    }

    private fun handleCheckAnswer() {
        btnAnswer.setOnClickListener {
            llRadioGroupContainer.removeAllViews()
            for (answer in question.answers) {
                val answerLayout =
                    layoutInflater.inflate(
                        R.layout.true_answer_layout,
                        llRadioGroupContainer,
                        false
                    )

                val trueAnswerLayoutTv: TextView =
                    answerLayout.findViewById(R.id.trueAnswerLayoutTv)
                val trueAnswerLayoutImgView: ImageView =
                    answerLayout.findViewById(R.id.trueAnswerLayoutImgView)

                trueAnswerLayoutTv.text = answer.text

                if (answer.isCorrect) {
                    trueAnswerLayoutTv.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.true_answer
                        )
                    )
                } else {
                    trueAnswerLayoutTv.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.false_answer
                        )
                    )
                    trueAnswerLayoutImgView.setImageDrawable(
                        AppCompatResources.getDrawable(
                            requireContext(),
                            R.drawable.false_icon
                        )
                    )
                }
                llRadioGroupContainer.addView(answerLayout)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_question, container, false)
        bindView(view)

        val radioGroup = RadioGroup(context)
        radioGroup.orientation = LinearLayout.VERTICAL

        tvQuestion.text = question.question

        question.img?.let {
            questionImageView.isVisible = true
            Glide.with(this).load(it).placeholder(R.drawable.loading)
                .into(questionImageView)
        }

        var layoutParams: RadioGroup.LayoutParams
        for (answer in question.answers) {
            val radioButton = RadioButton(context)
            radioButton.text = answer.text
            radioButton.setPadding(10, 0, 0, 0)
            radioButton.setOnClickListener {
                btnAnswer.isVisible = true
                isCorrect = answer.isCorrect
                handleCheckAnswer()
            }
            layoutParams = RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.MATCH_PARENT,
                RadioGroup.LayoutParams.MATCH_PARENT
            )
            layoutParams.setMargins(0, 10, 0, 10)
            radioGroup.addView(radioButton, layoutParams)
        }
        llRadioGroupContainer.addView(radioGroup)
        return view
    }
}