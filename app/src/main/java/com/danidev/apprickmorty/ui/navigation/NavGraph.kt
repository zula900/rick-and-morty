package com.danidev.apprickmorty.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.danidev.apprickmorty.data.model.RickCharacter
import com.danidev.apprickmorty.ui.screens.CharacterDetailScreen
import com.danidev.apprickmorty.ui.screens.FavoritosScreen
import com.danidev.apprickmorty.ui.screens.HomeScreen
import com.danidev.apprickmorty.ui.screens.SplashScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    // Lista de favoritos compartida entre pantallas (en memoria, mientras la app está abierta)
    val favorites = remember { mutableStateListOf<RickCharacter>() }

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(
                onNavigateNext = {
                    navController.navigate("home") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        composable("home") {
            HomeScreen(
                favorites = favorites,
                onCharacterClick = { character ->
                    navController.navigate("detail/${character.id}")
                },
                onFavoritesClick = {
                    navController.navigate("favorites")
                },
                onToggleFavorite = { character ->
                    if (favorites.any { it.id == character.id }) {
                        favorites.removeAll { it.id == character.id }
                    } else {
                        favorites.add(character)
                    }
                }
            )
        }

        composable(
            route = "detail/{characterId}",
            arguments = listOf(navArgument("characterId") { type = NavType.IntType })
        ) { backStackEntry ->
            val characterId = backStackEntry.arguments?.getInt("characterId") ?: 0
            CharacterDetailScreen(
                characterId = characterId,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("favorites") {
            FavoritosScreen(
                favorites = favorites,
                onExploreClick = {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                onFavoritesClick = { /* ya estás aquí */ },
                onAddMoreClick = {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                onToggleFavorite = { character ->
                    favorites.remove(character)
                }
            )
        }
    }
}