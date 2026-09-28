package com.danidev.apprickmorty.ui.screens

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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.danidev.apprickmorty.data.model.RickCharacter
import com.danidev.apprickmorty.ui.viewmodel.CharacterUiState
import com.danidev.apprickmorty.ui.viewmodel.CharacterViewModel

private val HomeBackground = Color(0xFF050811)
private val HomeCardColor = Color(0xFF111827)
private val HomeGreen = Color(0xFF39FF14)
private val HomeWhite = Color.White
private val HomeGray = Color(0xFF9AA5BA)

@Composable
fun HomeScreen(
    favorites: List<RickCharacter> = emptyList(),
    onCharacterClick: (RickCharacter) -> Unit = {},
    onFavoritesClick: () -> Unit = {},
    onToggleFavorite: (RickCharacter) -> Unit = {},
    viewModel: CharacterViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HomeBackground)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Multiverso",
                    color = HomeWhite,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "Terminal de Consulta v1.0",
                    color = HomeGreen,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .border(width = 1.dp, color = HomeGreen, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(HomeGreen)
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(HomeCardColor)
                .border(width = 1.dp, color = Color(0xFF25314A), shape = RoundedCornerShape(12.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar",
                    tint = HomeGreen,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.onSearchQueryChange(it) },
                    placeholder = {
                        Text(text = "Buscar personaje...", color = HomeGray, fontSize = 13.sp)
                    },
                    singleLine = true,
                    modifier = Modifier.weight(1f).fillMaxHeight(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        cursorColor = HomeGreen
                    )
                )
                Icon(
                    imageVector = Icons.Default.Tune,
                    contentDescription = "Filtros",
                    tint = HomeGreen,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "Personajes",
            color = HomeWhite,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box(modifier = Modifier.weight(1f)) {
            when (val state = uiState) {
                is CharacterUiState.Loading -> {
                    CircularProgressIndicator(
                        color = HomeGreen,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is CharacterUiState.Error -> {
                    Text(
                        text = state.message,
                        color = HomeGray,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is CharacterUiState.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.characters) { character ->
                            HomeCharacterCard(
                                character = character,
                                isFavorite = favorites.any { it.id == character.id },
                                onClick = { onCharacterClick(character) },
                                onFavoriteClick = { onToggleFavorite(character) }
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth().height(62.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.Search, contentDescription = "Explorar", tint = HomeGreen, modifier = Modifier.size(21.dp))
                Spacer(modifier = Modifier.height(3.dp))
                Text(text = "Explorar", color = HomeGreen, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { onFavoritesClick() }
            ) {
                Icon(Icons.Default.FavoriteBorder, contentDescription = "Favoritos", tint = HomeGray, modifier = Modifier.size(21.dp))
                Spacer(modifier = Modifier.height(3.dp))
                Text(text = "Favoritos", color = HomeGray, fontSize = 10.sp)
            }
        }
    }
}

@Composable
private fun HomeCharacterCard(
    character: RickCharacter,
    isFavorite: Boolean,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(HomeCardColor)
            .border(width = 1.dp, color = Color(0xFF25314A), shape = RoundedCornerShape(15.dp))
            .clickable { onClick() }
    ) {
        Box(modifier = Modifier.fillMaxWidth().height(145.dp)) {
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(27.dp)
                    .align(Alignment.TopEnd)
                    .clip(CircleShape)
                    .background(Color(0xAA111827))
                    .clickable { onFavoriteClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorito",
                    tint = if (isFavorite) Color(0xFFFF4D6D) else HomeWhite,
                    modifier = Modifier.size(17.dp)
                )
            }
        }
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 9.dp)) {
            Text(text = character.name, color = HomeWhite, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(3.dp))
            Text(text = character.species, color = HomeGray, fontSize = 11.sp, lineHeight = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            HomeStatusTag(status = character.status)
        }
    }
}

@Composable
private fun HomeStatusTag(status: String) {
    val statusColor = when (status.lowercase()) {
        "alive" -> HomeGreen
        "dead" -> Color.Red
        else -> Color(0xFF8B9AB5)
    }
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF080D17))
            .padding(horizontal = 7.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(statusColor))
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = status.uppercase(), color = HomeWhite, fontSize = 9.sp, fontWeight = FontWeight.Medium)
    }
}