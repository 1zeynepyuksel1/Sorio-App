package com.zeynep1yuksel.baseapp.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zeynep1yuksel.baseapp.R
import com.zeynep1yuksel.baseapp.ui.components.SorioButton
import com.zeynep1yuksel.baseapp.ui.components.SorioTextField
import com.zeynep1yuksel.baseapp.ui.theme.backgroundColor
import com.zeynep1yuksel.baseapp.ui.theme.buttonContentColor
import com.zeynep1yuksel.baseapp.ui.theme.darkBlue
import kotlin.math.log

@Composable
fun LogInScreen() {
    var username by remember{ mutableStateOf("") }
    var password by remember{mutableStateOf("")}
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color=backgroundColor)
        .padding(32.dp),
        verticalArrangement = Arrangement.Top) {
        Spacer(modifier=Modifier.height(100.dp))
        Text("welcome back", color = Color.Black, fontSize = 35.sp, fontFamily = logoFont)
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Black,
                        fontSize = 35.sp,
                        fontFamily = logoFont
                    )
                ) {
                    append("to ")
                }
                withStyle(
                    style = SpanStyle(
                        color = buttonContentColor,
                        fontSize = 50.sp,
                        fontFamily = logoFont
                    )
                ) {
                    append("sorio")
                }
            }
        )
        Spacer(modifier=Modifier.height(15.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Gray)) {
                    append("If you are new / ")
                }
                withStyle(style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold)) {
                    append("Create new")
                }
            },
            modifier = Modifier.clickable {  }
        )
        Spacer(modifier=Modifier.height(75.dp))
        SorioTextField(
            value=username,
            onValueChange = {username=it},
            label="username",
            icon=Icons.Default.AccountBox,
            keyboardType = KeyboardType.Text
        )
        SorioTextField(
            value=password,
            onValueChange = {password=it},
            label="password",
            icon=Icons.Default.Lock,
            keyboardType = KeyboardType.Password
        )
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Gray)) {
                    append("Forgot password? / ")
                }
                withStyle(style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold)) {
                    append("Create a new password")
                }
            },
            modifier = Modifier.clickable {  }
        )
        Spacer(modifier=Modifier.height(25.dp))
        SorioButton(text="Log in", containerColor = buttonContentColor, contentColor = Color.White)

    }
}
@Preview
@Composable
private fun LogInScreenPreview() {
    LogInScreen()
}