package com.expensetracker

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val amount: Double,
    val category: Category,
    val date: Long = System.currentTimeMillis()
) : Serializable {

    enum class Category(val displayName: String, val iconRes: Int) {
        FOOD("Food", R.drawable.ic_category_food),
        TRANSPORT("Transport", R.drawable.ic_category_transport),
        SHOPPING("Shopping", R.drawable.ic_category_shopping),
        ENTERTAINMENT("Entertainment", R.drawable.ic_category_entertainment),
        HEALTH("Health", R.drawable.ic_category_health),
        OTHER("Other", R.drawable.ic_category_other)
    }
}