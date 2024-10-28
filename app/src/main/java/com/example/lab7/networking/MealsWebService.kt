package com.example.lab7.networking

import com.example.lab7.networking.response.MealResponseCategories
import com.example.lab7.networking.response.MealResponseFilter
import com.example.lab7.networking.response.MealResponseLookup
import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory

class MealsWebService{

    private lateinit var api: MealsApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")// URL base de la API
            .addConverterFactory(GsonConverterFactory.create())// Convierte JSON en objetos Kotlin usando Gson
            .build()

        api = retrofit.create(MealsApi::class.java) // Crea una instancia de la interfaz de la API
    }

    fun getMealCategories(): Call<MealResponseCategories> {     // Función para obtener categorías de comidas
        return api.getMealCategories()
    }

    fun getMealsByCategory(category: String): Call<MealResponseFilter> {  // Función para obtener comidas por categoría
        return api.getMealsByCategory(category)
    }

    fun getMealById(mealId: String): Call<MealResponseLookup> { // Función para obtener detalles de una comida por su ID
        return api.getMealById(mealId)
    }

}