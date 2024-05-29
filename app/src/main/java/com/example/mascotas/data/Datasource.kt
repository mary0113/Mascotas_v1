package com.example.mascotas.data

import android.annotation.SuppressLint
import com.example.mascotas.R
import com.example.mascotas.model.Mascota

class Datasource (){
    @SuppressLint("ResourceType")
    fun loadMascotas(): List<Mascota>{
        return listOf<Mascota>(
            Mascota(R.string.nombre1, R.string.edad1, R.string.enfermedad1, R.string.altura1, R.string.raza1,R.string.apodo1,R.drawable.image1,
                listOf(R.string.vacuna1C, R.string.vacuna2C, R.string.vacuna3C, R.string.vacuna4C)),
            Mascota(R.string.nombre2, R.string.edad2, R.string.enfermedad2, R.string.altura2, R.string.raza2,R.string.apodo2, R.drawable.image2,
                listOf(R.string.vacuna1D, R.string.vacuna2D, R.string.vacuna3D, R.string.vacuna4D)),
            Mascota(R.string.nombre3, R.string.edad3, R.string.enfermedad3, R.string.altura3, R.string.raza3,R.string.apodo3, R.drawable.image3,
                listOf(R.string.vacuna1CH, R.string.vacuna2CH, R.string.vacuna3CH, R.string.vacuna4CH)),
            Mascota(R.string.nombre4, R.string.edad4, R.string.enfermedad4, R.string.altura4, R.string.raza4,R.string.apodo4,R.drawable.image4,
                listOf(R.string.vacuna1M, R.string.vacuna2M, R.string.vacuna3M, R.string.vacuna4M))
        )
    }
}