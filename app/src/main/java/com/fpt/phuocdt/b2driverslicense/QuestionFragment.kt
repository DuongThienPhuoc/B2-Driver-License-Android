package com.fpt.phuocdt.b2driverslicense

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.fpt.phuocdt.b2driverslicense.database.UserChoice
import com.fpt.phuocdt.b2driverslicense.database.UserChoiceDAO
import com.fpt.phuocdt.b2driverslicense.database.UserDatabase
import com.fpt.phuocdt.b2driverslicense.database.UserDatabaseInstance
import com.fpt.phuocdt.b2driverslicense.entity.Question
import kotlinx.coroutines.launch

class QuestionFragment(private val question: Question) : Fragment() {
    private lateinit var llRadioGroupContainer: LinearLayout
    private lateinit var tvQuestion: TextView
    private lateinit var questionImageView: ImageView
    private var isCorrect: Boolean? = null
    private var userChoiceId: String? = null
    private lateinit var userChoiceDAO: UserChoiceDAO

    private fun bindView(view: View) {
        llRadioGroupContainer = view.findViewById(R.id.llRadioGroupContainer)
        tvQuestion = view.findViewById(R.id.questionTv)
        questionImageView = view.findViewById(R.id.imgViewQuestion)
    }

    private fun handleCheckAnswer() {
        llRadioGroupContainer.removeAllViews()
        if (isCorrect == true) {
            for (answer in question.answers) {
                if (answer.isCorrect) {
                    val answerLayout = layoutInflater.inflate(
                        R.layout.answer_layout,
                        llRadioGroupContainer,
                        false
                    )
                    val answerTextView: TextView =
                        answerLayout.findViewById(R.id.answerTextView)
                    val iconStatus: ImageView =
                        answerLayout.findViewById(R.id.iconAnswerStatusIV)
                    iconStatus.setImageResource(R.drawable.true_icon)
                    answerLayout.background = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.question_slider_item_backgroud,
                        null
                    )
                    answerTextView.text = answer.text
                    llRadioGroupContainer.addView(answerLayout)
                } else {
                    val answerLayout = layoutInflater.inflate(
                        R.layout.answer_layout,
                        llRadioGroupContainer,
                        false
                    )
                    val answerTextView: TextView =
                        answerLayout.findViewById(R.id.answerTextView)
                    val iconStatus: ImageView =
                        answerLayout.findViewById(R.id.iconAnswerStatusIV)
                    iconStatus.setImageResource(R.drawable.unchecked_icon)
                    answerTextView.text = answer.text
                    llRadioGroupContainer.addView(answerLayout)
                }
            }
        } else {
            for (answer in question.answers) {
                if (answer._id == userChoiceId) {
                    val answerLayout = layoutInflater.inflate(
                        R.layout.answer_layout,
                        llRadioGroupContainer,
                        false
                    )
                    val answerTextView: TextView =
                        answerLayout.findViewById(R.id.answerTextView)
                    val iconStatus: ImageView =
                        answerLayout.findViewById(R.id.iconAnswerStatusIV)
                    answerTextView.text = answer.text
                    iconStatus.setImageResource(R.drawable.false_icon)
                    answerLayout.background = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.false_answer_backgroud,
                        null
                    )
                    llRadioGroupContainer.addView(answerLayout)
                } else if (answer.isCorrect) {
                    val answerLayout = layoutInflater.inflate(
                        R.layout.answer_layout,
                        llRadioGroupContainer,
                        false
                    )
                    val answerTextView: TextView =
                        answerLayout.findViewById(R.id.answerTextView)
                    val iconStatus: ImageView =
                        answerLayout.findViewById(R.id.iconAnswerStatusIV)
                    answerTextView.text = answer.text
                    iconStatus.setImageResource(R.drawable.true_icon)
                    llRadioGroupContainer.addView(answerLayout)
                } else {
                    val answerLayout = layoutInflater.inflate(
                        R.layout.answer_layout,
                        llRadioGroupContainer,
                        false
                    )
                    val answerTextView: TextView =
                        answerLayout.findViewById(R.id.answerTextView)
                    answerTextView.text = answer.text
                    llRadioGroupContainer.addView(answerLayout)
                }
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_question, container, false)
        val db = UserDatabaseInstance.getInstance(requireContext())
        userChoiceDAO = db.userChoiceDao()

        bindView(view)
        cacheData()
        bindDataToAnswersView()

        return view
    }

    private fun cacheData() {
        lifecycleScope.launch {
            try {
                val userChoice: UserChoice? = userChoiceDAO.getUserChoiceById(question._id)
                userChoice?.let {
                    isCorrect = question.answers.find {
                        it._id == userChoice.answerId
                    }!!.isCorrect
                    userChoiceId = userChoice.answerId
                    handleCheckAnswer()
                }
            } catch (e: Exception) {
                Log.e("QuestionFragment", e.message.toString())
            }
        }
    }


    private fun bindDataToAnswersView() {
        tvQuestion.text = question.question

        question.img?.let {
            questionImageView.isVisible = true
            Glide.with(this).load(it).placeholder(R.drawable.loading)
                .into(questionImageView)
        }

        for (answer in question.answers) {
            val answerLayout =
                layoutInflater.inflate(R.layout.answer_layout, llRadioGroupContainer, false)

            val answerTextView: TextView = answerLayout.findViewById(R.id.answerTextView)

            answerTextView.text = answer.text
            answerLayout.setOnClickListener {
                isCorrect = answer.isCorrect
                userChoiceId = answer._id
                lifecycleScope.launch {
                    try {
                        userChoiceDAO.addChoice(
                            UserChoice(
                                0,
                                question._id,
                                answer._id
                            )
                        )
                    } catch (e: Exception) {
                        Log.e("QuestionFragment", e.message.toString())
                    }
                }
                handleCheckAnswer()
            }
            llRadioGroupContainer.addView(answerLayout)
        }
    }

}