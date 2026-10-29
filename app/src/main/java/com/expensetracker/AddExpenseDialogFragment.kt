package com.expensetracker

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.expensetracker.databinding.DialogAddExpenseBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AddExpenseDialogFragment : DialogFragment() {

    private var _binding: DialogAddExpenseBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ExpenseViewModel
    private var selectedCategory: Expense.Category = Expense.Category.FOOD
    private var expenseToEdit: Expense? = null

    companion object {
        private const val ARG_EXPENSE = "arg_expense"
        fun newInstance(expense: Expense? = null): AddExpenseDialogFragment {
            val fragment = AddExpenseDialogFragment()
            val args = Bundle()
            if (expense != null) {
                args.putSerializable(ARG_EXPENSE, expense)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        viewModel = ViewModelProvider(requireActivity())[ExpenseViewModel::class.java]

        val inflater = requireActivity().layoutInflater
        _binding = DialogAddExpenseBinding.inflate(inflater)

        val builder = MaterialAlertDialogBuilder(requireContext())
            .setView(binding.root)
            .setTitle(if (expenseToEdit != null) "Edit Expense" else getString(R.string.add_expense))
            .setPositiveButton(if (expenseToEdit != null) "Update" else getString(R.string.save)) { _, _ ->
                saveExpense()
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.dismiss() }

        val dialog = builder.create()

        setupCategorySpinner()
        loadExpenseIfEditing()

        // Enable/disable save button based on input
        binding.expenseName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateSaveButtonState()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.expenseAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateSaveButtonState()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        return dialog
    }

    private fun setupCategorySpinner() {
        val categories = Expense.Category.values()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories.map { it.displayName })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.expenseCategory.adapter = adapter

        binding.expenseCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedCategory = categories[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedCategory = Expense.Category.FOOD
            }
        }
    }

    private fun loadExpenseIfEditing() {
        arguments?.getSerializable(ARG_EXPENSE)?.let { expense ->
            expenseToEdit = expense as Expense
            binding.expenseName.setText(expense.name)
            binding.expenseAmount.setText(expense.amount.toString())
            val categoryIndex = Expense.Category.values().indexOf(expense.category)
            binding.expenseCategory.setSelection(categoryIndex)
            selectedCategory = expense.category
        }
        updateSaveButtonState()
    }

    private fun updateSaveButtonState() {
        val dialog = dialog as? AlertDialog
        val positiveButton = dialog?.getButton(AlertDialog.BUTTON_POSITIVE)
        val name = binding.expenseName.text.toString().trim()
        val amount = binding.expenseAmount.text.toString().trim()
        positiveButton?.isEnabled = name.isNotEmpty() && amount.isNotEmpty() && amount.toDoubleOrNull() != null
    }

    private fun saveExpense() {
        val name = binding.expenseName.text.toString().trim()
        val amountText = binding.expenseAmount.text.toString().trim()

        if (name.isEmpty()) {
            Toast.makeText(requireContext(), R.string.enter_expense_name, Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountText.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            Toast.makeText(requireContext(), R.string.enter_valid_amount, Toast.LENGTH_SHORT).show()
            return
        }

        val expense = expenseToEdit?.copy(
            name = name,
            amount = amount,
            category = selectedCategory,
            date = System.currentTimeMillis()
        ) ?: Expense(
            name = name,
            amount = amount,
            category = selectedCategory
        )

        if (expenseToEdit != null) {
            viewModel.insert(expense) // Room will update since ID is same
            Toast.makeText(requireContext(), "Expense updated", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.insert(expense)
            Toast.makeText(requireContext(), R.string.expense_added, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}