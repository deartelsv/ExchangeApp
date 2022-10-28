package ru.artelsv.exchangeapp

sealed class Screen(val route: String) {

    object Main : Screen("main")
    object Favourite : Screen("favourite")
}
