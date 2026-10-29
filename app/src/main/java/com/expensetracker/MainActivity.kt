package com.expensetracker

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.expensetracker.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ExpenseViewModel
    private lateinit var adapter: ExpenseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        viewModel = ViewModelProvider(this)[ExpenseViewModel::class.java]

        setupRecyclerView()
        observeData()
        setupFab()
    }

    private fun setupRecyclerView() {
        adapter = ExpenseAdapter(
            onItemClick = { expense ->
                AddExpenseDialogFragment.newInstance(expense)
                    .show(supportFragmentManager, "AddExpenseDialog")
            },
            onItemLongClick = { expense ->
                showDeleteConfirmation(expense)
            }
        )
        binding.expensesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.expensesRecyclerView.adapter = adapter
    }

    private fun observeData() {
        viewModel.allExpenses.observe(this) { expenses ->
            adapter.submitList(expenses)
            binding.emptyState.visibility = if (expenses.isEmpty()) View.VISIBLE else View.GONE
            binding.expensesRecyclerView.visibility = if (expenses.isEmpty()) View.GONE else View.VISIBLE
        }

        viewModel.totalExpenses.observe(this) { total ->
            val amount = total ?: 0.0
            binding.totalAmountText.text = formatCurrency(amount)
        }
    }

    private fun setupFab() {
        binding.fabAddExpense.setOnClickListener {
            AddExpenseDialogFragment.newInstance()
                .show(supportFragmentManager, "AddExpenseDialog")
        }
    }

    private fun showDeleteConfirmation(expense: Expense) {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.delete)
            .setMessage(getString(R.string.confirm_delete))
            .setPositiveButton(R.string.yes) { _, _ ->
                viewModel.delete(expense)
                Toast.makeText(this, R.string.expense_deleted, Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton(R.string.no, null)
            .show()
    }

    private fun showClearAllConfirmation() {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.delete)
            .setMessage("Are you sure you want to delete all expenses?")
            .setPositiveButton(R.string.yes) { _, _ ->
                viewModel.deleteAll()
                Toast.makeText(this, "All expenses cleared", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton(R.string.no, null)
            .show()
    }

    private fun formatCurrency(amount: Double): String = java.text.NumberFormat.getCurrencyInstance(java.util.Locale.getDefault()).format(amount)

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_clear_all -> {
                showClearAllConfirmation()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}