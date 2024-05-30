package com.example.mascotas

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mascotas.data.Datasource


// Composable para mostrar la pantalla de favoritos
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(navController: NavController, favoriteMascotas: List<Int>) {
    // Carga las mascotas que están marcadas como favoritas
    val mascotas = Datasource().loadMascotas().filter { it.stringResourceId in favoriteMascotas }

    // Diseño de la pantalla usando Scaffold
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Atrás", color = Color.White) }, // Título de la barra superior
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) { // Botón de navegación para volver atrás
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
            // Contenido principal de la pantalla
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding), // Añade relleno
                color = Color(0xFFb4764f) // Color de fondo de la pantalla
            ) {
                Column(
                    modifier = Modifier.fillMaxSize() // Ocupa todoel espacio disponible
                ) {
                    Text(
                        text = "FAVORITOS", // Título de la sección de favoritos
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.primary, // Color del texto
                        textAlign = TextAlign.Center,
                        fontSize = 30.sp,
                        modifier = Modifier.padding(16.dp) // Añade relleno
                    )
                    // Verifica si no hay mascotas favoritas
                    if (mascotas.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(), // Ocupa todoel espacio disponible
                            contentAlignment = Alignment.Center // Alinea el contenido al centro
                        ) {
                            Text(
                                text = "No hay mascotas en favoritos",
                                style = MaterialTheme.typography.headlineMedium,
                                color = Color.White,
                                fontSize = 24.sp
                            )
                        }
                    } else {
                        // Muestra la lista de mascotas favoritas
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(mascotas) { mascota ->
                                // Composable para mostrar una tarjeta de mascota
                                MascotaCard(
                                    mascota = mascota,
                                    onClick = { /* No necesita hacer nada*/ }, // Acción cuando se hace clic
                                    onFavoriteClick = { /* No necesita hacer nada */ }, // Acción cuando se marca/desmarca como favorita
                                    isFavorite = true, // Todas las mascotas aquí son favoritas
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}