package com.example.mascotas

sealed class  Screens(val screen: String){
    data object  Home:Screens("home") //Representa la pantalla principal de la aplicación
    data object Search:Screens("search") //Representa la pantalla de búsqueda en la aplicación
    data object  Favorites:Screens("favorites") // Representa la pantalla de favoritos en la aplicación
    data object  Info:Screens("info") //Representa la pantalla de información en la aplicación.
}