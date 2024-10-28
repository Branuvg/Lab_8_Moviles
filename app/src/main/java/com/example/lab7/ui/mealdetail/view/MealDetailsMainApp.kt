package com.example.lab7.ui.mealdetail.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.lab7.networking.response.MealDetailList
import com.example.lab7.ui.categories.view.MealCategoriesMainApp
import com.example.lab7.ui.mealdetail.viewmodel.MealDetailsViewModel


class MealDetail:ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val mealId= intent.getStringExtra("mealId")
        setContent{
            MealDetailMainApp(mealId)
        }
    }
}

@Composable
fun MealDetailMainApp(mealId:String?) {
    val viewModel: MealDetailsViewModel = viewModel() //ViewModel con la lista de comidas de la categoría
    val mealDetails: MutableState<List<MealDetailList>> = remember { mutableStateOf(emptyList()) } //Lista de estado mutable que guarda todas las comidas y sus detalles

    if (mealId != null) { //Llama a la función para obtener el detalle del platillo seleccionado desde la API
        viewModel.getMealById(mealId) { response ->
            val mealsFromTheApi = response?.meals.orEmpty()
            mealDetails.value = mealsFromTheApi
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(135, 139, 230)
    ) {
        LazyColumn( //Despliega el detalle de la comida en un LazyColumn
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            mealDetails?.let { details ->
                itemsIndexed(mealDetails.value) {index,detail ->
                    MealDetailItem(detail = detail,)
                }
            }
        }
    }
}

@Composable
fun MealDetailItem(detail: MealDetailList) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        //Imagen del platillo
        val painter = rememberAsyncImagePainter(model = detail.mealimage)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .shadow(8.dp, shape = RoundedCornerShape(8.dp))
                .background( Color(135, 139, 230))
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        //Nombre del platillo
        Text(
            text = " ${detail.name}",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color.Black,
            lineHeight = 30.sp,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(8.dp))
        //Categoría a la que pertenece el platillo
        Text(
            text = "Category: ${detail.category}",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color= Color.Black,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.CenterHorizontally)

        )

        Spacer(modifier = Modifier.height(8.dp))
        //Área del platillo
        Text(
            text = "Area: ${detail.area}",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color= Color.Black,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Envuelve la lista de ingredientes en un Box con color de fondo personalizado
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.LightGray)
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = "Ingredients:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )

                val ingredientsList = mutableListOf<String>() //Lista mutable de ingredientes

                for (i in 1..20) {
                    val ingredient = detail.getIngredient(i) //Se obtienen los ingredientes de la lista MealDetailsList
                    val measure = detail.getMeasure(i) //Se obtienen los measures de la lista MealDetailsList

                    if (ingredient != null) {
                        if (measure != null) {  //Si los valores no son nulos, ambos se agregan a la lista de ingredientes
                            if (ingredient.isNotEmpty() && measure.isNotEmpty()) {
                                ingredientsList.add("$ingredient: $measure")
                            }
                        }
                    }
                }

                ingredientsList.forEach { ingredient -> //Formato de muestra en pantalla de los ingredientes y sus respectivas medidas
                    Text(
                        text = ingredient,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 4.dp),
                        textAlign = TextAlign.Justify
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        //Instrucciones
        Text(
            text = "Instructions:",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.CenterHorizontally)
        )

        // Divide el texto de las instrucciones en líneas separadas
        val instructionsLines = detail.instructions.split("\n")

        // Mapea cada línea a un componente Text
        instructionsLines.forEachIndexed { index, instruction ->
            Text(
                text = if (index == 0) instruction else " $instruction", // Agrega viñetas a las líneas no vacías
                fontSize = 16.sp,
                textAlign = TextAlign.Justify,
            )
        }
    }
}

// Funciones para obtener los ingredientes y medidas a partir de su index
private fun MealDetailList.getIngredient(index: Int): String {
    when (index) {
        1 -> return strIngredient1
        2 -> return strIngredient2
        3 -> return strIngredient3
        4 -> return strIngredient4
        5 -> return strIngredient5
        6-> return strIngredient6
        7->return strIngredient7
        8-> return strIngredient8
        9-> return strIngredient9
        10-> return strIngredient10
        11 -> return strIngredient11
        12 -> return strIngredient12
        13 -> return strIngredient13
        14 -> return strIngredient14
        15 -> return strIngredient15
        16-> return strIngredient16
        17->return strIngredient17
        18-> return strIngredient18
        19-> return strIngredient19
        20-> return strIngredient20
        else -> return ""
    }
}

private fun MealDetailList.getMeasure(index: Int): String {
    when (index) {
        1 -> return strMeasure1
        2 -> return strMeasure2
        3 -> return strMeasure3
        4 -> return strMeasure4
        5 -> return strMeasure5
        6-> return strMeasure6
        7->return strMeasure7
        8-> return strMeasure8
        9-> return strMeasure9
        10-> return strMeasure10
        11 -> return strMeasure11
        12 -> return strMeasure12
        13 -> return strMeasure13
        14 -> return strMeasure14
        15 -> return strMeasure15
        16-> return strMeasure16
        17->return strMeasure17
        18-> return strMeasure18
        19-> return strMeasure19
        20-> return strMeasure20
        else -> return ""
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMealDetailMainApp() {
    MealDetailMainApp(mealId = "1234")
}