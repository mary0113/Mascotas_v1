package com.example.mascotas.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Mascota(
    @StringRes val stringResourceId:Int,
    @DrawableRes val imageResourceId :Int
)

