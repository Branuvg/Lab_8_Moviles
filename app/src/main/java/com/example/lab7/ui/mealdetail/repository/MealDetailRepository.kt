package com.example.lab7.ui.mealdetail.repository

import com.example.lab7.networking.MealsWebService
import com.example.lab7.networking.response.MealResponseLookup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealDetailRepository(private val webService: MealsWebService= MealsWebService()) {
    fun getMealById(mealId: String, successCallback: (response: MealResponseLookup?) -> Unit){
        return webService.getMealById(mealId).enqueue(object: Callback<MealResponseLookup>{
            override fun onResponse(
                call: Call<MealResponseLookup>,
                response: Response<MealResponseLookup>
            ){
                if (response.isSuccessful)
                    successCallback(response.body())
            }

            override fun onFailure(call: Call<MealResponseLookup>, t: Throwable) {
                TODO("Not yet implemented")
            }
        }
        )
    }
}