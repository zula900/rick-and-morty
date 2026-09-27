package com.danidev.apprickmorty.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.danidev.apprickmorty.data.model.RickCharacter

private val FavBackgroundDark = Color(0xFF0E1420)
private val FavCardDark = Color(0xFF161D2B)
private val FavAccentCyan = Color(0xFF3FD6E0)
private val FavAccentGreen = Color(0xFF3DDC6B)
private val FavAccentPink = Color(0xFFFF4D6D)
private val FavTextSecondary = Color(0xFF9AA3B2)

@Composable
fun FavoritosScreen(
    favorites: List<RickCharacter>,
    onExploreClick: () -> Unit = {},
    onFavoritesClick: () -> Unit = {},
    onAddMoreClick: () -> Unit = {},
    onToggleFavorite: (RickCharacter) -> Unit = {}
) {
    Scaffold(
        containerColor = FavBackgroundDark,
        bottomBar = {
            FavoritosBottomBar(
                onExploreClick = onExploreClick,
                onFavoritesClick = onFavoritesClick
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(FavBackgroundDark)
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(Modifier.height(12.dp))
            FavoritosHeader(count = favorites.size)
            Spacer(Modifier.height(20.dp))

            if (favorites.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Aún no tienes favoritos guardados",
                        color = FavTextSecondary,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(favorites, key = { it.id }) { character ->
                        FavoriteCard(
                            character = character,
                            onToggleFavorite = { onToggleFavorite(character) }
                        )
                    }
                    item {
                        AddMoreFavoritesCard(onClick = onAddMoreClick)
                    }
                }
            }
        }
    }
}

@Composable
private fun FavoritosHeader(count: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column {
            Text(
                text = "Favoritos",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Tus dimensiones preferidas",
                color = FavAccentCyan,
                fontSize = 14.sp
            )
        }
        Surface(
            shape = RoundedCornerShape(50),
            border = BorderStroke(1.dp, FavAccentCyan),
            color = Color.Transparent
        ) {
            Text(
                text = "$count Guardados",
                color = FavAccentCyan,
                fontSize = 12.sp,
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
            )
        }
    }
}

@Composable
private fun FavoriteCard(
    character: RickCharacter,
    onToggleFavorite: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(18.dp))
            .background(FavCardDark)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) {
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                modifier = Modifier.fillMaxSize()
            )
            IconButton(
                onClick = onToggleFavorite,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(32.dp)
                    .background(Color.Black.copy(alpha = 0.45f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Quitar de favoritos",
                    tint = FavAccentPink,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = character.name,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
            Text(
                text = character.species,
                color = FavTextSecondary,
                fontSize = 13.sp
            )
            Spacer(Modifier.height(6.dp))
            StatusBadge(isAlive = character.status.equals("alive", ignoreCase = true))
        }
    }
}

@Composable
private fun StatusBadge(isAlive: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(if (isAlive) FavAccentGreen else Color.Gray, CircleShape)
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text = if (isAlive) "VIVO" else "MUERTO",
            color = if (isAlive) FavAccentGreen else Color.Gray,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun AddMoreFavoritesCard(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(RoundedCornerShape(18.dp))
            .border(
                width = 1.5.dp,
                color = FavTextSecondary.copy(alpha = 0.4f),
                shape = RoundedCornerShape(18.dp)
            )
            .background(FavCardDark.copy(alpha = 0.3f))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = null,
                tint = FavTextSecondary,
                modifier = Modifier
                    .size(36.dp)
                    .border(1.dp, FavTextSecondary, CircleShape)
                    .padding(6.dp)
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = "Añadir más\nfavoritos",
                color = FavTextSecondary,
                fontSize = 13.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun FavoritosBottomBar(
    onExploreClick: () -> Unit,
    onFavoritesClick: () -> Unit
) {
    NavigationBar(
        containerColor = FavBackgroundDark,
        tonalElevation = 0.dp
    ) {
        NavigationBarItem(
            selected = false,
            onClick = onExploreClick,
            icon = { Icon(Icons.Filled.Explore, contentDescription = "Explorar") },
            label = { Text("Explorar") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = FavAccentGreen,
                unselectedIconColor = FavTextSecondary,
                selectedTextColor = FavAccentGreen,
                unselectedTextColor = FavTextSecondary,
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            selected = true,
            onClick = onFavoritesClick,
            icon = { Icon(Icons.Outlined.Favorite, contentDescription = "Favoritos") },
            label = { Text("Favoritos") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = FavAccentGreen,
                unselectedIconColor = FavTextSecondary,
                selectedTextColor = FavAccentGreen,
                unselectedTextColor = FavTextSecondary,
                indicatorColor = Color.Transparent
            )
        )
    }
}