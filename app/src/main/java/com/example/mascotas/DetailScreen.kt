package com.example.mascotas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mascotas.data.Datasource
import com.example.mascotas.ui.theme.MascotasTheme

class DetailScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MascotasTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFb4764f)
                ) {
                    val navController = rememberNavController()
                    DetailScreen(navController, 1) // Asumiendo 1 como ejemplo de mascotaId
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, mascotaId: Int) {
    val mascota = Datasource().loadMascotas().find { it.stringResourceId == mascotaId }
    var selectedButton by remember { mutableStateOf("Información") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Atrás     PET-HEALTH", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2a1d10)
                )
            )
        },
        bottomBar = {
            MyBottomAppBar(navController = navController)
        },
        content = { innerPadding ->
            mascota?.let {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(Color(0xFF795548)) // Establece el color de fondo
                        .padding(16.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                onClick = { selectedButton = "Información" },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 0.dp),
                                border = if (selectedButton == "Información") BorderStroke(2.dp, Color.DarkGray) else null,
                            ) {
                                Text("Información", color = if (selectedButton == "Información") Color.White else Color.Black)
                            }
                            Button(
                                onClick = { selectedButton = "Vacunas" },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 0.dp),
                                border = if (selectedButton == "Vacunas") BorderStroke(2.dp, Color.DarkGray) else null
                            ) {
                                Text(text = "Vacunas", color = if (selectedButton == "Vacunas") Color.White else Color.Black)
                            }
                            Button(
                                onClick = { selectedButton = "Citas Médicas" },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 0.dp),
                                border = if (selectedButton == "Citas Médicas") BorderStroke(2.dp, Color.DarkGray) else null
                            ) {
                                Text(text = "Citas Médicas", color = if (selectedButton == "Citas Médicas") Color.White else Color.Black)
                            }
                        }

                        when (selectedButton) {
                            "Información" -> {
                                Text(
                                    text = stringResource(id = it.stringResourceId),
                                    style = MaterialTheme.typography.headlineLarge,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 35.sp,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                Image(
                                    painter = painterResource(it.imageResourceId),
                                    contentDescription = stringResource(id = it.stringResourceId),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(194.dp),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp),
                                    elevation = CardDefaults.cardElevation(4.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFFd0ab7a)) // Color de fondo del Card
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Text(
                                            text = stringResource(id = mascota.stringEdadId),
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = Color.Black,
                                            fontSize = 30.sp,
                                            textAlign = TextAlign.Center
                                        )
                                        Text(
                                            text = stringResource(id = mascota.stringEnfermedadId),
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = Color.Black,
                                            fontSize = 30.sp,
                                            textAlign = TextAlign.Center
                                        )
                                        Text(
                                            text = stringResource(id = mascota.stringAlturaId),
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = Color.Black,
                                            fontSize = 30.sp,
                                            textAlign = TextAlign.Center
                                        )
                                        Text(
                                            text = stringResource(id = mascota.stringRazaId),
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = Color.Black,
                                            fontSize = 30.sp,
                                            textAlign = TextAlign.Center
                                        )
                                        Text(
                                            text = stringResource(id = mascota.stringApodoId),
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = Color.Black,
                                            fontSize = 30.sp,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                            "Vacunas" -> {
                                MascotaVacunas(vacunaIds = it.vacunaIds)

                            }
                            "Citas Médicas" -> {
                                MascotaCitas(citaIds = it.citaIds)
                            }
                        }
                    }
                }
            }
        }
    )
}


// Composable para mostrar una tarjeta de vacuna
@Composable
fun VacunaCard(@StringRes vacunaId: Int) {
    val vacuna = stringResource(id = vacunaId)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = vacuna,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold)
        }
    }
}

//Composable para mostrar todas las vacunas de una mascota
@Composable
fun MascotaVacunas(vacunaIds: List<Int>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "VACUNAS:",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        vacunaIds.forEach { vacunaId ->
            VacunaCard(vacunaId = vacunaId)
        }
    }
}


// Composable para mostrar una tarjeta de cita médica
@Composable
fun CitaCard(@StringRes citaId: Int) {
    val cita = stringResource(id = citaId)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = cita,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun MascotaCitas(citaIds: List<Int>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Citas Médicas",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 35.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        for (citaId in citaIds) {
            CitaCard(citaId = citaId)
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    MascotasTheme {
        val navController = rememberNavController()
        DetailScreen(navController = navController, mascotaId = 1) // Asumiendo 1 como ejemplo de mascotaId
    }
}