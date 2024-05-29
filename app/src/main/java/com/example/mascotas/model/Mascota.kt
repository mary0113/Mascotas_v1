package com.example.mascotas.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Mascota(
    @StringRes val stringResourceId:Int, // Identificador de recurso de cadena para el nombre
    @StringRes val stringEdadId:Int, // Identificador de recurso de cadena para la edad
    @StringRes val stringEnfermedadId:Int, // Identificador de recurso de cadena enfermedad
    @StringRes val stringAlturaId:Int,   // Identificador de recurso de cadena para altura
    @StringRes val stringRazaId:Int, // Identificador de recurso de cadena para raza
    @StringRes val stringApodoId:Int, // Identificador de recurso de cadena para raza
    @DrawableRes val imageResourceId :Int, //Identificador de recurso de imagen
    @StringRes val vacunaIds: List<Int>, // Lista de identificadores de recursos de cadena para las vacunas
    @StringRes val citaIds: List<Int> // Lista de identificadores de recursos de cadena para las citas médicas

)

