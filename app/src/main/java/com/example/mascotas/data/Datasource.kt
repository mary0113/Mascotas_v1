package com.example.mascotas.data

import com.example.mascotas.R
import com.example.mascotas.model.Mascota

class Datasource (){
    fun loadMascotas(): List<Mascota>{
        return listOf<Mascota>(
            Mascota(R.string.Chesster, R.drawable.image1),
            Mascota(R.string.Duffy, R.drawable.image2),
            Mascota(R.string.Champiñon, R.drawable.image3),
            Mascota(R.string.Cubrebocas, R.drawable.image4)
        )
    }
}