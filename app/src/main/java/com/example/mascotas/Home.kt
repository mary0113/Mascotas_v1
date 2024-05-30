package com.example.mascotas

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.mascotas.data.Datasource
import com.example.mascotas.ui.theme.MascotasTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController

import com.example.mascotas.model.Mascota

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, favoriteMascotas: MutableState<List<Int>>, onFavoriteChange: (Int) -> Unit) {

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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.onSecondaryContainer)// Fondo de color específico
                    .padding(innerPadding)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "PET-HEALTH",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 35.sp,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Divider(
                        color = Color(0xFF5a4928),
                        thickness = 2.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "TUS MASCOTAS",
                        color = MaterialTheme.colorScheme.tertiary,
                        fontSize = 25.sp,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Aquí se incluye la lista de mascotas
                    MascotaList(
                        mascotaList = Datasource().loadMascotas(),
                        onItemClick = { mascotaId ->
                            navController.navigate("detail_screen/$mascotaId")
                        },
                        onFavoriteClick = { mascotaId ->
                            onFavoriteChange(mascotaId)
                        },
                        favoriteMascotas = favoriteMascotas.value
                    )
                }
            }
        }
    )
}

@Composable
fun MascotaCard(mascota: Mascota, onClick: () -> Unit, onFavoriteClick: () -> Unit, isFavorite: Boolean, modifier: Modifier = Modifier) {
    val nombre = stringResource(id = mascota.stringResourceId)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Column {
            Image(
                painter = painterResource(mascota.imageResourceId),
                contentDescription = nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp),
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = nombre,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
                IconButton(onClick = onFavoriteClick) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Añadir a Favoritos",
                        tint = if (isFavorite) Color.Red else Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun MascotaList(mascotaList: List<Mascota>, onItemClick: (Int) -> Unit, onFavoriteClick: (Int) -> Unit, favoriteMascotas: List<Int>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(mascotaList) { mascota ->
            MascotaCard(
                mascota = mascota,
                onClick = { onItemClick(mascota.stringResourceId) },
                onFavoriteClick = { onFavoriteClick(mascota.stringResourceId) },
                isFavorite = favoriteMascotas.contains(mascota.stringResourceId),
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    val favoriteMascotas = remember { mutableStateOf(listOf<Int>()) } // Añade la lista de favoritos

    MascotasTheme {
        HomeScreen(navController = navController,
            favoriteMascotas = favoriteMascotas, // Añade el parámetro favoriteMascotas
            onFavoriteChange = { mascotaId -> // Añade el parámetro onFavoriteChange
                if (favoriteMascotas.value.contains(mascotaId)) {
                    favoriteMascotas.value = favoriteMascotas.value - mascotaId
                } else {
                    favoriteMascotas.value = favoriteMascotas.value + mascotaId
                }
            })
    }
}