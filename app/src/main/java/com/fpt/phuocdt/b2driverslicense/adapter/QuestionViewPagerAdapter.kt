package com.fpt.phuocdt.b2driverslicense.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fpt.phuocdt.b2driverslicense.QuestionFragment
import com.fpt.phuocdt.b2driverslicense.entity.Question

class QuestionViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private var questions: List<Question>
) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return questions.size
    }

    override fun createFragment(position: Int): Fragment {
        val question = questions[position]
        return QuestionFragment(question)
    }
}