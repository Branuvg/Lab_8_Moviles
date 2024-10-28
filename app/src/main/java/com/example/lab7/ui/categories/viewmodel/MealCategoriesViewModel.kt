package com.example.lab7.ui.categories.viewmodel

import androidx.lifecycle.ViewModel
import com.example.lab7.networking.response.MealResponseCategories
import com.example.lab7.ui.categories.repository.MealsCategoriesRepository

class MealCategoriesViewModel(private val repository: MealsCategoriesRepository= MealsCategoriesRepository()): ViewModel(){
    fun getMealCategories(successCallback: (response: MealResponseCategories?) -> Unit){
        repository.getMealCategories{response ->
            successCallback(response)
        }
    }
}