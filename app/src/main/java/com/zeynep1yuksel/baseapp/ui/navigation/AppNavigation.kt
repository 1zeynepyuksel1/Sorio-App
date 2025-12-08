package com.zeynep1yuksel.baseapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost

import androidx.navigation.compose.rememberNavController
import com.zeynep1yuksel.baseapp.ui.auth.LogInScreen
import com.zeynep1yuksel.baseapp.ui.auth.SignUpScreen
import com.zeynep1yuksel.baseapp.ui.auth.StartScreen
import androidx.navigation.compose.composable
import com.zeynep1yuksel.baseapp.ui.home.HomeScreen

@Composable
fun AppNavigation() {
    val navController= rememberNavController()
    NavHost(navController=navController,startDestination="start"){
        composable("start"){
            StartScreen(
                onLoginClick = {navController.navigate("login")},
                onRegisterClick = {navController.navigate("signup")})
        }
        composable("login"){
            LogInScreen(onRegisterClick = {navController.navigate("signup"){popUpTo("start"){inclusive=false} } },
                onBackClick = {navController.popBackStack()},
                onHomeClick = {navController.navigate("home")}
            )
        }
        composable("signup"){
            SignUpScreen(onLoginClick = {navController.navigate("login"){popUpTo("start"){inclusive=false} }},
                onBackClick = {navController.popBackStack()},
                onHomeClick = {navController.navigate("home")})
        }
        composable ("home"){
            HomeScreen()
        }
    }
}