package com.expensetracker

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.expensetracker.databinding.ItemExpenseBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class ExpenseAdapter(
    private val onItemClick: (Expense) -> Unit,
    private val onItemLongClick: (Expense) -> Unit
) : ListAdapter<Expense, ExpenseAdapter.ExpenseViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val binding = ItemExpenseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExpenseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = getItem(position)
        holder.bind(expense)
    }

    inner class ExpenseViewHolder(private val binding: ItemExpenseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(expense: Expense) {
            binding.expenseNameText.text = expense.name
            binding.expenseCategoryText.text = expense.category.displayName
            binding.expenseAmountText.text = formatCurrency(expense.amount)
            binding.expenseDateText.text = formatDate(expense.date)
            binding.categoryIconImage.setImageResource(expense.category.iconRes)

            // Set category color tint
            val categoryColor = when (expense.category) {
                Expense.Category.FOOD -> R.color.green
                Expense.Category.TRANSPORT -> R.color.blue
                Expense.Category.SHOPPING -> R.color.orange
                Expense.Category.ENTERTAINMENT -> R.color.purple_500
                Expense.Category.HEALTH -> R.color.red
                else -> R.color.gray_medium
            }
            binding.categoryIconImage.setColorFilter(
                binding.root.context.getColor(categoryColor)
            )
            binding.categoryIcon.setCardBackgroundColor(
                Color.argb(30, Color.red(binding.root.context.getColor(categoryColor)), Color.green(binding.root.context.getColor(categoryColor)), Color.blue(binding.root.context.getColor(categoryColor)))
            )

            binding.root.setOnClickListener { onItemClick(expense) }
            binding.root.setOnLongClickListener {
                onItemLongClick(expense)
                true
            }
        }
    }

    private fun formatCurrency(amount: Double): String {
        return NumberFormat.getCurrencyInstance(Locale.getDefault()).format(amount)
    }

    private fun formatDate(timestamp: Long): String {
        return SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(timestamp)
    }

    class DiffCallback : DiffUtil.ItemCallback<Expense>() {
        override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem == newItem
        }
    }
}