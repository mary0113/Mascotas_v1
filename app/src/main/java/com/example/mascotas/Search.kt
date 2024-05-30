package com.example.mascotas

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
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
fun SearchScreen(navController: NavController) {
    val ctx = LocalContext.current
    val mascotas = remember { Datasource().loadMascotas() }
    var active by remember { mutableStateOf(false) } // Representa el estado de la searchBar
    var query by remember { mutableStateOf(TextFieldValue("")) }  // Representa la consulta

    // Filtra las mascotas según la consulta
    val filteredMascotas = mascotas.filter {
        val nombre = getString(ctx, it.stringResourceId)
        nombre.contains(query.text, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Atrás", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Atrás",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF352514)
                )
            )
        },
        bottomBar = {
            MyBottomAppBar(navController)
        },
        content = { innerPadding ->
            Scaffold(
                modifier = Modifier.padding(innerPadding),
                content = {
                    Column {
                        SearchBar(
                            query = query.text,
                            onQueryChange = { query = TextFieldValue(it) },
                            onSearch = {
                                active = false // Cambia de activo a no activo
                            },
                            active = active,
                            onActiveChange = { active = it }
                        ) {
                            // Contenido del SearchBar si es necesario
                        }

                        // Lista de resultados de búsqueda
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(filteredMascotas) { mascota ->
                                MascotaItem(mascota = mascota)
                            }
                        }
                    }
                }
            )
        }
    )
}


@Composable
fun MascotaItem(mascota: Mascota) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(mascota.imageResourceId),
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column {
            Text(text = getString(context, mascota.stringResourceId))
            Text(text = getString(context, mascota.stringEdadId))
            Text(text = getString(context, mascota.stringRazaId))
        }
    }
}