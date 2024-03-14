package com.fpt.phuocdt.b2driverslicense

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fpt.phuocdt.b2driverslicense.adapter.HomeCategoryRcvAdapter
import com.fpt.phuocdt.b2driverslicense.api.CategoryServiceAPI
import com.fpt.phuocdt.b2driverslicense.entity.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val tag = "MainActivity"
    private lateinit var homeCategoryRcv: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var homeCategoryRcvAdapter: HomeCategoryRcvAdapter


    private fun bindView() {
        homeCategoryRcv = findViewById(R.id.homeCategoryRcv)
        progressBar = findViewById(R.id.homeProgressBar)
    }

    private fun bindAction() {
        progressBar.isVisible = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        bindView()
        bindAction()
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val deferredCategories = async { CategoryServiceAPI.getCategory() }
                val categoriesResponse = deferredCategories.await()

                if (categoriesResponse.isSuccessful && categoriesResponse.body() != null) {
                    withContext(Dispatchers.Main) {
                        progressBar.isVisible = false
                        val categories: List<Category> = categoriesResponse.body()!!
                        homeCategoryRcvAdapter = HomeCategoryRcvAdapter(categories) {
                            val intent = Intent(this@MainActivity, CategoryActivity::class.java)
                            intent.putExtra("category", it)
                            startActivity(intent)
                        }
                        setAdapter()
                    }
                }
            } catch (e: Exception) {
                Log.e(tag, e.message.toString())
                return@launch
            }

        }
    }
    fun onImageView1Click(view: View?) {
        val topics = ArrayList<String>()
        topics.add("ĐỀ 1")
        topics.add("ĐỀ 2")
        topics.add("ĐỀ 3")
        topics.add("ĐỀ 4")
        topics.add("ĐỀ 5")
        topics.add("ĐỀ 6")
        topics.add("ĐỀ 7")
        topics.add("ĐỀ 8")
        topics.add("ĐỀ 9")
        topics.add("ĐỀ 10")
        topics.add("ĐỀ 11")
        topics.add("ĐỀ 12")
        topics.add("ĐỀ 13")
        topics.add("ĐỀ 14")
        topics.add("ĐỀ 15")
        topics.add("ĐỀ 16")
        topics.add("ĐỀ 17")
        topics.add("ĐỀ 18")
        val intent = Intent(this, QuizListActivity::class.java)
        intent.putStringArrayListExtra("topics", topics)
        val selectedTopicPosition = 1
        intent.putExtra("selected_topic_position", selectedTopicPosition)

        startActivity(intent)
    }


    private fun setAdapter() {
        homeCategoryRcv.layoutManager = LinearLayoutManager(this)
        homeCategoryRcv.adapter = homeCategoryRcvAdapter
    }
}