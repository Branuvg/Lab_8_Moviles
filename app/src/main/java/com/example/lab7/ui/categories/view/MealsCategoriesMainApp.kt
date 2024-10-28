package com.example.lab7.ui.categories.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.lab7.networking.response.MealResponse
import com.example.lab7.ui.categories.viewmodel.MealCategoriesViewModel
import com.example.lab7.ui.meals.view.MealFilter

class MealCategory: ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContent{
            MealCategoriesMainApp()
        }
    }
}

@Composable
fun MealCategoriesMainApp() {
    val viewModel: MealCategoriesViewModel = viewModel() //Obteer el ViewModel de las categorías disponibles
    val categorizedMeals: MutableState<List<MealResponse>> = //Almacenar todas las categorías en una lista de estado mutable
        remember { mutableStateOf(emptyList()) }
    val context = LocalContext.current //Contexto local

    viewModel.getMealCategories { response -> //Llama a la función para obtener las categorías de comida desde la API
        val mealsFromTheApi = response?.categories
        categorizedMeals.value = mealsFromTheApi.orEmpty()
    }
    //Composición de la pantalla
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally // Centrar horizontalmente
    ) {
        Text( //Título de la pantalla
            text = "Meal Categories",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier.padding(16.dp)
        )
        //La lista de categorías se despliegan en una LazyColumn
        LazyColumn {
            items(categorizedMeals.value) { meal ->
                CategoryItem(meal = meal, context= context)
            }
        }
    }
}

@Composable
fun CategoryItem(meal: MealResponse, context: Context) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color(52, 58, 235))
            .clickable {
                val intent = Intent(context, MealFilter::class.java)
                intent.putExtra("category", meal.name) // Agrega el valor de la categoría como un extra
                context.startActivity(intent)
            }
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            //Imagen de la comida
            val painter = rememberAsyncImagePainter(model = meal.imageUrl)
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
            ) //Nombre de la comida
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = meal.name,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = Color.Black,
            ) //Descripción de la comida
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = meal.description,
                fontSize = 12.sp,
                color = Color.Black,
                textAlign = TextAlign.Justify
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMealCategoriesMainApp() {
    MealCategoriesMainApp()
}