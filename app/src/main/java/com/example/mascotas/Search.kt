package com.example.mascotas

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mascotas.data.Datasource
import com.example.mascotas.model.Mascota


//Se cargan los datos de las mascotas y se guardan
@Composable
fun getString(context: Context, @StringRes id: Int): String {
    return context.getString(id)
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController, favoriteMascotas: MutableState<List<Int>>, onFavoriteChange: (Int) -> Unit) {
    val mascotas = Datasource().loadMascotas() // Carga la lista de mascotas desde el datasource
    var query by remember { mutableStateOf("") }  // Estado para la consulta de búsqueda
    var active by remember { mutableStateOf(false) } // Estado para la consulta de búsqueda
    var searchResults by remember { mutableStateOf(listOf<Mascota>()) } // Estado para los resultados de búsqueda
    val context = LocalContext.current  //Accede a los recursos string

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Atrás", color = Color.White) },  // Título de la barra superior
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) { // Botón de navegación hacia atrás
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Atrás",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF352514) // Color del fondo de la barra superior
                )
            )
        },
        bottomBar = {
            MyBottomAppBar(navController) // Barra inferior
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.onSecondaryContainer)
                    .padding(innerPadding)
            ) {
               //Se reutiliza el código de Home para que muestre la lista y pueda agregar/quitar de favoritos así como al dar clic se diriga a
                //la de DetailsScreen
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "PET-HEALTH", // Título principal
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 35.sp,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Divider(
                        color = Color(0xFF5a4928), // Color del divisor
                        thickness = 2.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "BUSCAR MASCOTAS", // Subtítulo
                        color = MaterialTheme.colorScheme.tertiary,
                        fontSize = 25.sp,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    // Barra de búsqueda
                    SearchBar(
                        query = query, //Estado actual del texto en la SearchBar
                        onQueryChange = { query = it }, // Actualiza el estado de `query` cuando el usuario escribe algo nuevo
                        onSearch = { //Filtra la lista de mascotas basándose en el valor actual de query y actualiza searchResults con los resultados de la búsqueda
                            searchResults = mascotas.filter {
                                val name = context.getString(it.stringResourceId) //Usa el context para obtener el nombre de la mascota basado en su id
                                name.contains(query, ignoreCase = true)
                            }
                            active = false
                        },
                        active = active, //Inidca si la searchBar está activa(interactuando)
                        onActiveChange = { active = it } //Estado de la actividad
                    )
                    //Da un espacio
                    Spacer(modifier = Modifier.height(16.dp))
                    if (query.isNotEmpty()) { // Verifica si la consulta de búsqueda no está vacía
                        if (searchResults.isNotEmpty()) { // Verifica si hay resultados de búsqueda
                            // Muestra la lista de mascotas filtradas
                            MascotaList(
                                mascotaList = searchResults, // Lista de resultados de búsqueda
                                onItemClick = { mascotaId ->
                                    navController.navigate("detail_screen/$mascotaId")  // Navega a la pantalla de detalles de la mascota seleccionada
                                },
                                onFavoriteClick = { mascotaId ->  // Maneja el cambio en el estado de favorito de la mascota
                                    onFavoriteChange(mascotaId)
                                },
                                favoriteMascotas = favoriteMascotas.value // Lista de mascotas favoritas
                            )
                        } else {
                            // Muestra un mensaje cuando no se encuentran resultados
                            Text(
                                text = "No se encontraron resultados.",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit, onSearch: () -> Unit, active: Boolean, onActiveChange: (Boolean) -> Unit) {
    // Estado para mantener el valor del TextField
    var textFieldValue by remember { mutableStateOf(TextFieldValue(query)) }
    // Objeto para manejar el enfoque del TextField
    val focusRequester = remember { FocusRequester() }

    // Efecto que se ejecuta cuando "active" cambia
    LaunchedEffect(active) {
        if (active) {
            focusRequester.requestFocus() // Solicita el enfoque del TextField cuando 'active' es verdadero
        }
    }

    TextField(
        // Componente TextField para la barra de búsqueda
        value = textFieldValue,
        // Actualiza el estado del TextField y llama a onQueryChange con el texto actual
        onValueChange = {
            textFieldValue = it
            onQueryChange(it.text)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .focusRequester(focusRequester), // Aplica el foco al TextField
        placeholder = { Text(text = "Buscar...") },
        trailingIcon = {
            // Icono de búsqueda que ejecuta la acción de búsqueda cuando se presiona
            IconButton(onClick = onSearch) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar")
            }
        },
        singleLine = true, // Configura el TextField para que sea de una sola línea
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.onPrimaryContainer, // Color de fondo del TextField
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,  // Color del indicador cuando el TextField está enfocado
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)  // Color del indicador cuando el TextField no está enfocado
        )
    )

    // Efecto que se ejecuta una vez cuando el componente se compone
    LaunchedEffect(Unit) {
        // Cambia el estado activo a verdadero
        onActiveChange(true)
    }
}