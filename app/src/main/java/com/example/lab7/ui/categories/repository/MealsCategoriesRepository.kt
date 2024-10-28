package com.example.lab7.ui.categories.repository

import com.example.lab7.networking.MealsWebService
import com.example.lab7.networking.response.MealResponseCategories
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealsCategoriesRepository(private val webService: MealsWebService= MealsWebService()){
    fun getMealCategories(successCallback: (response: MealResponseCategories?) -> Unit){
        return webService.getMealCategories().enqueue(object: Callback<MealResponseCategories>{
            override fun onResponse(
                call: Call<MealResponseCategories>,
                response: Response<MealResponseCategories>
            ){
                if (response.isSuccessful)
                    successCallback(response.body())
            }

            override fun onFailure(call: Call<MealResponseCategories>, t: Throwable) {
                TODO("Not yet implemented")
            }
        }
        )
    }
}