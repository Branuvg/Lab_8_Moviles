package com.example.lab7.ui.mealdetail.viewmodel

import androidx.lifecycle.ViewModel
import com.example.lab7.networking.response.MealResponseLookup
import com.example.lab7.ui.mealdetail.repository.MealDetailRepository


class MealDetailsViewModel(private val repository: MealDetailRepository = MealDetailRepository()) : ViewModel() {
    fun getMealById(
        mealId: String,
        successCallback: (response: MealResponseLookup?) -> Unit
    ) {
        repository.getMealById(mealId) { response ->
            successCallback(response)
        }
    }
}
