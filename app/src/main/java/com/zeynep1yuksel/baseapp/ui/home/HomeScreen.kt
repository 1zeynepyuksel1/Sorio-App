package com.zeynep1yuksel.baseapp.ui.home

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.zeynep1yuksel.baseapp.ml.SorioClassifier
import com.zeynep1yuksel.baseapp.model.TimerStatus
import com.zeynep1yuksel.baseapp.ui.auth.logoFont
import com.zeynep1yuksel.baseapp.ui.components.QuestionPostCard
import com.zeynep1yuksel.baseapp.ui.theme.backgroundColor
import com.zeynep1yuksel.baseapp.ui.theme.buttonContentColor


data class BottomNavItem(val label: String, val icon: ImageVector)

val FocusBackground = Color(0xFF0B132B)
val SoftGray = Color(0xFF636E72)
val DarkText = Color(0xFF2D3436)

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    timerViewModel: TimerViewModel = viewModel(),
    questionViewModel: QuestionViewModel=viewModel()
) {
    var myBackgroundColor by remember { mutableStateOf(backgroundColor) }
    var myDarkBlue by remember { mutableStateOf(buttonContentColor) }
    var myGray by remember { mutableStateOf(Color.Gray) }
    val timerState by timerViewModel.timerState.collectAsState()
    val formattedTime = formatTime(timerState.studyTimeSeconds)
    val motivationText by timerViewModel.currentMotivation.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var resultText by remember { mutableStateOf("Analiz ediliyor...") }
    val navItems = listOf(
        BottomNavItem("Home", Icons.Default.Home),
        BottomNavItem("Notifications", Icons.Default.Notifications),
        BottomNavItem("Ask a question", Icons.Default.AddCircle),
        BottomNavItem("Timer", Icons.Default.Timer),
        BottomNavItem("Profile", Icons.Default.Person)
    )
    val display = if (viewModel.currentUser != null) {
        "${viewModel.currentUser?.name} ${viewModel.currentUser?.surname}"
    } else {
        "Loading"
    }
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                navItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedTab == index && index != 2,
                        onClick = {
                            selectedTab=index
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
                                modifier = Modifier.size(28.dp),
                                tint = if (index == 2) buttonContentColor else LocalContentColor.current
                            )
                        },
                        label = {
                            if (selectedTab == index && index != 2) {
                                Text(item.label, fontFamily = logoFont, fontSize = 10.sp)
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = myDarkBlue,
                            indicatorColor = myBackgroundColor,
                            unselectedIconColor = Color.DarkGray
                        )
                    )
                }
            }
        },
        containerColor = myBackgroundColor,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "sorio",
                        fontSize = 35.sp,
                        fontFamily = logoFont,
                        color = myDarkBlue
                    )
                    Text(
                        text = "ready to learn?",
                        fontSize = 14.sp,
                        color = myGray
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Surface(
                        shape = CircleShape,
                        color = Color.White,
                        shadowElevation = 4.dp,
                        modifier = Modifier
                            .size(50.dp)
                            .padding()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "profile",
                            modifier = Modifier.padding(10.dp),
                            tint = myDarkBlue
                        )
                    }
                    Text(display, color = myDarkBlue)
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            when (selectedTab) {
                0 -> {
                    HomeContent(selectedImageUri, resultText)
                    myBackgroundColor = backgroundColor
                    myDarkBlue = buttonContentColor
                    myGray = Color.DarkGray
                }

                1 -> {
                    NotificationContent()
                    myBackgroundColor = backgroundColor
                    myDarkBlue = buttonContentColor
                    myGray = Color.DarkGray
                }

                2 -> {
                    AddQuestionContent ()
                }
                3 -> {
                    TimerContent(
                        formattedTime = formattedTime,
                        timerStatus = timerState.status,
                        onToggleClick = timerViewModel::toggleTimer,
                        onResetClick = timerViewModel::resetTimer,
                        onSaveClick = timerViewModel::saveTime,
                        studyTimeSeconds = timerState.studyTimeSeconds,
                        motivationText = motivationText
                    )
                    myBackgroundColor = FocusBackground
                    myDarkBlue = backgroundColor
                    myGray = Color.White
                }

                4 -> {
                    ProfileContent()
                    myBackgroundColor = backgroundColor
                    myDarkBlue = buttonContentColor
                    myGray = Color.DarkGray
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewHomeScreen() {
    HomeScreen()
}

@Composable
fun HomeContent(imageUri: Uri?, resultText: String) {
    if (imageUri != null) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(4.dp),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(imageUri),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(10.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = resultText,
                    color = buttonContentColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    } else {
        Text(
            "Community",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = buttonContentColor
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(contentPadding = PaddingValues(bottom = 100.dp)) {
            items(5) { index ->
                QuestionPostCard(
                    userName = "Student $index",
                    subject = if (index % 2 == 0) "Matematik * Türev" else "Fizik * Kuvvet",
                    questionPreview = "Soru Fotoğrafı *$index"
                )
            }
        }
    }

}












