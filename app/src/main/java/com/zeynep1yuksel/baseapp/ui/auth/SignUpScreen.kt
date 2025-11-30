package com.zeynep1yuksel.baseapp.ui.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zeynep1yuksel.baseapp.data.SorioAuth
import com.zeynep1yuksel.baseapp.ui.components.SamplePasswordField
import com.zeynep1yuksel.baseapp.ui.components.SorioBackButton
import com.zeynep1yuksel.baseapp.ui.components.SorioButton
import com.zeynep1yuksel.baseapp.ui.components.SorioTextField
import com.zeynep1yuksel.baseapp.ui.theme.backgroundColor
import com.zeynep1yuksel.baseapp.ui.theme.buttonContentColor

@Composable
fun SignUpScreen(onLoginClick: () -> Unit, onBackClick: () -> Unit, onHomeClick: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val context = LocalContext.current
    val authManager = remember { SorioAuth(context) }
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
            .padding(32.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top
    ) {
        SorioBackButton(onClick = onBackClick)
        Spacer(modifier = Modifier.height(60.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Black,
                        fontSize = 35.sp,
                        fontFamily = logoFont
                    )
                ) {
                    append("join ")
                }
                withStyle(
                    style = SpanStyle(
                        color = buttonContentColor,
                        fontSize = 50.sp,
                        fontFamily = logoFont,
                    )
                ) {
                    append("sorio")
                }
            }
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Gray
                    )
                ) {
                    append("Already have an account? / ")
                }
                withStyle(style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold)) {
                    append("Log in")
                }
            },
            modifier = Modifier.clickable { onLoginClick() }
        )
        Spacer(modifier = Modifier.height(35.dp))
        SorioTextField(
            value = name,
            onValueChange = { name = it },
            label = "name",
            icon = Icons.Default.AccountBox,
            keyboardType = KeyboardType.Text
        )
        SorioTextField(
            value = surname,
            onValueChange = { surname = it },
            label = "surname",
            icon = Icons.Default.AccountBox,
            keyboardType = KeyboardType.Text
        )
        SorioTextField(
            value = email,
            onValueChange = { email = it },
            label = "email",
            icon = Icons.Default.Email,
            keyboardType = KeyboardType.Email
        )
        SamplePasswordField(
            value = password,
            onValueChange = { password = it },
            label = "password"
        )
        SamplePasswordField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = "confirm password"
        )
        Spacer(modifier = Modifier.height(25.dp))
        SorioButton(
            text = "Sign up",
            containerColor = buttonContentColor,
            contentColor = Color.White,
            onClick = { if (password == confirmPassword) {
                authManager.signUp(
                    email = email,
                    sifre = password,
                    onSuccess = {
                        onHomeClick()
                    }
                )
            } else {
                Toast.makeText(context, "Şifreler uyuşmuyor!", Toast.LENGTH_SHORT).show()
            } }
        )
    }
}
@Preview
@Composable
private fun PreviewSignUpScreen() {
    SignUpScreen(onLoginClick = {}, onBackClick = {}, onHomeClick = {})
}