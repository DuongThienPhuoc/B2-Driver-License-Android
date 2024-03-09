package com.fpt.phuocdt.b2driverslicense.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.fpt.phuocdt.b2driverslicense.R
import com.fpt.phuocdt.b2driverslicense.entity.Category

class HomeCategoryRcvAdapter(
    private val categories: List<Category>,
    private val setOnClickCategory: (category: Category) -> Unit
) :
    RecyclerView.Adapter<HomeCategoryRcvAdapter.HomeCategoryRcvAdapterViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeCategoryRcvAdapterViewHolder {
        val viewHolder =
            LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return HomeCategoryRcvAdapterViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: HomeCategoryRcvAdapterViewHolder, position: Int) {
        val category = categories[position]
        holder.categoryName.text = category.name
        holder.category.setOnClickListener {
            setOnClickCategory(category)
        }
    }


    class HomeCategoryRcvAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryName: TextView = view.findViewById(R.id.categoryName);
        val category: ConstraintLayout = view.findViewById(R.id.category);
    }
}