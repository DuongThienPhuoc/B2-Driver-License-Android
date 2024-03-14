package com.fpt.phuocdt.b2driverslicense

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.fpt.phuocdt.b2driverslicense.adapter.QuestionSliderRcvAdapter
import com.fpt.phuocdt.b2driverslicense.adapter.QuestionViewPagerAdapter
import com.fpt.phuocdt.b2driverslicense.api.CategoryServiceAPI
import com.fpt.phuocdt.b2driverslicense.database.UserChoiceDAO
import com.fpt.phuocdt.b2driverslicense.database.UserDatabaseInstance
import com.fpt.phuocdt.b2driverslicense.entity.Category
import com.fpt.phuocdt.b2driverslicense.entity.Question
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryActivity : AppCompatActivity() {
    private lateinit var categoryQuestionRcv: RecyclerView;
    private lateinit var questionSliderRcvAdapter: QuestionSliderRcvAdapter
    private lateinit var progressCategory: ProgressBar
    private lateinit var viewPager: ViewPager2
    private lateinit var userChoiceDAO: UserChoiceDAO

    private fun bindView() {
        categoryQuestionRcv = findViewById(R.id.categoryQuestionRcv)
        progressCategory = findViewById(R.id.categoryProgressBar)
        viewPager = findViewById(R.id.categoryViewPager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_category)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        userChoiceDAO = UserDatabaseInstance.getInstance(this).userChoiceDao()
        bindView()

        val category: Category? = intent.getSerializableExtra("category") as? Category

        category?.let {
            progressCategory.isVisible = true
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val deferredQuestions =
                        async { CategoryServiceAPI.getQuestionsByCategoryId(it._id) }

                    val questions = deferredQuestions.await()
                    if (questions.isSuccessful && questions.body() != null) {
                        val questionList = questions.body() as List<Question>
                        val questionCount = (1..questionList.size).toList()
                        withContext(Dispatchers.Main) {
                            progressCategory.isVisible = false
                            setAdapter(questionCount, questionList)
                        }
                    }
                } catch (e: Exception) {
                    progressCategory.isVisible = false
                    Log.e("CategoryActivity", e.message.toString())
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        GlobalScope.launch(Dispatchers.IO) {
            userChoiceDAO.deleteAll()
        }
    }

    private fun setAdapter(int: List<Int>, question: List<Question>) {
        questionSliderRcvAdapter = QuestionSliderRcvAdapter(int) {
            viewPager.currentItem = it - 1
        }
        categoryQuestionRcv.adapter = questionSliderRcvAdapter
        categoryQuestionRcv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        viewPager.adapter = QuestionViewPagerAdapter(this@CategoryActivity, question)
    }
}