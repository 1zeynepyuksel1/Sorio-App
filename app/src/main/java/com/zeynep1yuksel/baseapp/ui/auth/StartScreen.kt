package com.zeynep1yuksel.baseapp.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zeynep1yuksel.baseapp.R
import com.zeynep1yuksel.baseapp.ui.components.SorioButton
import com.zeynep1yuksel.baseapp.ui.theme.backgroundColor
import com.zeynep1yuksel.baseapp.ui.theme.buttonContentColor
import com.zeynep1yuksel.baseapp.ui.theme.grayText

val logoFont= FontFamily(
    Font(R.font.rubik, FontWeight.ExtraBold)
)
val textFont= FontFamily(
    Font(R.font.montserrat)
)
@Composable
fun StartScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Column(modifier= Modifier
        .fillMaxSize()
        .background(
            color= backgroundColor
        )
        .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally){
        Box(modifier= Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center){
            Column(horizontalAlignment = Alignment.CenterHorizontally){
                Text("sorio",
                    fontFamily = logoFont,
                    color=buttonContentColor,
                    fontSize = 85.sp,
                    style = TextStyle(
                        shadow = Shadow(
                            color=Color.Black.copy(alpha=0.5f),
                            offset = Offset(4f,8f),
                            blurRadius = 12f
                        )
                    )
                )
                Text(
                    text="ready to learn?",
                    color=Color.DarkGray,
                    fontSize=20.sp
                )
            }

        }
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(bottom=50.dp)){
            Text("Already a Sorio?",
                fontFamily = textFont,
                color = Color.DarkGray,
                fontSize = 15.sp

            )
            //Spacer(modifier=Modifier.height(10.dp))
            SorioButton(text="Log In",
                containerColor=buttonContentColor,
                contentColor=Color.White,
                onClick = onLoginClick
            )
            Text("New to Sorio?",
                fontFamily = textFont,
                color = Color.DarkGray,
                fontSize = 15.sp
            )
            SorioButton("Sign Up",Color.White,buttonContentColor, onClick = onRegisterClick)
        }

    }
}

@Preview
@Composable
private fun StartScreenPreview() {
    StartScreen(onLoginClick = {}, onRegisterClick = {})
}





