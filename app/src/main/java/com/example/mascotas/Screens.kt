package com.example.mascotas

sealed class  Screens(val screen: String){
    data object  Home:Screens("home")
    data object Search:Screens("search")
    data object  Favorites:Screens("favorites")
    data object  Info:Screens("info")
}