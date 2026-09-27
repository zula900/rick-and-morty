package com.danidev.apprickmorty.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val SplashBackground = Color(0xFF050811)
private val SplashGreen = Color(0xFF39FF14)
private val SplashDescColor = Color(0xFF9AA5BA)

@Composable
fun SplashScreen(onNavigateNext: () -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SplashBackground)
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))

            // Portal (círculo con gradiente en vez de imagen, para no depender de un drawable)
            Box(
                modifier = Modifier
                    .size(215.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(SplashGreen.copy(alpha = 0.6f), Color.Transparent)
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .background(Color.Transparent, CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(95.dp))

            Text(
                text = "PORTAL",
                color = Color.White,
                fontSize = 34.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )
            Text(
                text = "DIMENSIONAL",
                color = SplashGreen,
                fontSize = 34.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Viaja por el multiverso y explora todos los\npersonajes de Rick y Morty desde una sola\nterminal.",
                color = SplashDescColor,
                fontSize = 15.sp,
                lineHeight = 22.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = onNavigateNext,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(51.dp)
                    .shadow(
                        elevation = 12.dp,
                        shape = RoundedCornerShape(14.dp),
                        ambientColor = SplashGreen,
                        spotColor = SplashGreen
                    ),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SplashGreen)
            ) {
                Text(
                    text = "Iniciar Viaje",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Continuar",
                    tint = Color.Black
                )
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}