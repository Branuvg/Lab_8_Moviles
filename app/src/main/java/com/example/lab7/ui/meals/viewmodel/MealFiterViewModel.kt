package com.example.lab7.ui.meals.viewmodel

import androidx.lifecycle.ViewModel
import com.example.lab7.networking.response.MealResponseFilter
import com.example.lab7.ui.meals.repository.MealFilterRepository

class MealFilterViewModel(private val repository: MealFilterRepository= MealFilterRepository()) : ViewModel() {
    fun getMealsByCategory(
        category: String,
        successCallback: (response: MealResponseFilter?) -> Unit
    ) {
        repository.getMealsByCategory(category) { response ->
            successCallback(response)
        }
    }
}
