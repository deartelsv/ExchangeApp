package ru.artelsv.exchangeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.artelsv.exchangeapp.ui.screen.FavouriteScreen
import ru.artelsv.exchangeapp.ui.screen.MainScreen
import ru.artelsv.exchangeapp.ui.theme.ExchangeAppTheme

@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val nav = rememberNavController()

            ExchangeAppTheme {
                Scaffold(
                    bottomBar = { ExchangeBottomNavigation(nav) }
                ) {
                    ExchangeNavHost(nav, it)
                }
            }
        }
    }
}

@Composable
fun ExchangeBottomNavigation(nav: NavController) {
    val navBackStackEntry by nav.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
        ?: Screen.Main.route

    NavigationBar {
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_home_24),
                    contentDescription = null
                )
            },
            label = { Text(stringResource(R.string.home)) },
            selected = true,
            onClick = {
                if (Screen.Main.route != currentRoute) {
                    nav.navigate(Screen.Main.route)
                }
            },
            modifier = Modifier.navigationBarsPadding()
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_favorite_24),
                    contentDescription = null
                )
            },
            label = { Text(stringResource(R.string.favourite)) },
            selected = true,
            onClick = {
                if (Screen.Favourite.route != currentRoute) {
                    nav.navigate(Screen.Favourite.route)
                }
            },
            modifier = Modifier.navigationBarsPadding()
        )
    }
}

@ExperimentalMaterial3Api
@Composable
fun ExchangeNavHost(nav: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = nav,
        startDestination = Screen.Main.route,
        Modifier.padding(paddingValues)
    ) {

        composable(Screen.Main.route) {
            MainScreen()
        }
        composable(Screen.Favourite.route) {
            FavouriteScreen()
        }
    }
}