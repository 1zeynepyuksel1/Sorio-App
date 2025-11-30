package com.zeynep1yuksel.baseapp.ui.auth

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.zeynep1yuksel.baseapp.ui.theme.backgroundColor
import com.zeynep1yuksel.baseapp.ui.theme.buttonContentColor

data class BottomNavItem(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)
@Composable
fun HomeScreen() {
    var selectedTab by remember{mutableStateOf(0)}
    val navItems = listOf(
        BottomNavItem("Home", Icons.Default.Home),
        BottomNavItem("Notifications", Icons.Default.Notifications),
        BottomNavItem("Ask a question", Icons.Default.AddCircle),
        BottomNavItem("Timer", Icons.Default.Timer),
        BottomNavItem("Profile", Icons.Default.Person)
    )
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                navItems.forEachIndexed { index,item->
                NavigationBarItem(
                    selected = selectedTab == index && index!=2,
                    onClick = {
                        if(index==2){
                            /*kamera açılacak*/
                        }else{
                            selectedTab=index
                        }
                    },
                    icon={
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            modifier=Modifier.size(28.dp),
                            tint=if(index==2) buttonContentColor else LocalContentColor.current
                        )
                    },
                    label={
                        if(selectedTab==index && index !=2){
                            Text(item.label, fontFamily = logoFont, fontSize = 10.sp)
                        }
                    },
                    colors= NavigationBarItemDefaults.colors(
                        selectedIconColor = buttonContentColor,
                        indicatorColor = backgroundColor,
                        unselectedIconColor = Color.Gray
                    )
                )
            }
            }
        },
        containerColor = backgroundColor,
    ) { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 24.dp)) {
            Spacer(modifier=Modifier.height(20.dp))
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
                        color = buttonContentColor
                    )
                    Text(
                        text = "ready to learn?",
                        fontSize = 14.sp,
                        color = Color.DarkGray
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
                            tint = buttonContentColor
                        )
                    }
                    Text("Zeynep")
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            when(selectedTab){
                0->HomeContent()
                1->NotificationContent()
                2->{}
                3->TimerContent()
                4->ProfileContent()
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
fun HomeContent() {
    Text(
        "Community",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = buttonContentColor
    )
    Spacer(modifier = Modifier.height(16.dp))
    LazyColumn(contentPadding = PaddingValues(bottom=100.dp)){
        items(5){index->
            QuestionPostCard(
                userName = "Student $index",
                subject = if(index%2==0) "Matematik * Türev" else "Fizik * Kuvvet",
                questionPreview = "Soru Fotoğrafı *$index"
            )
        }
    }
}
@Composable
fun ProfileContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Profile page", color = Color.Gray)
    }
}
@Composable
fun TimerContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.Timer, contentDescription = null, modifier = Modifier.size(50.dp), tint = buttonContentColor)
            Spacer(modifier = Modifier.height(16.dp))
            Text("00:00:00", fontSize = 40.sp, fontFamily = logoFont, color = buttonContentColor)
            Text("Focus mode", fontFamily = logoFont, color = Color.Gray)
        }
    }
}
@Composable
fun NotificationContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.Notifications, contentDescription = null, modifier = Modifier.size(50.dp), tint = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))
            Text("No notification yet.", fontFamily = logoFont, color = Color.Gray)
        }
    }
}
@Composable
fun QuestionPostCard(userName: String, subject: String, questionPreview: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = CircleShape,
                    color = backgroundColor,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.padding(8.dp), tint = Color.Gray)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(text = userName, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = buttonContentColor)
                    Text(text = subject, fontSize = 12.sp, color = Color.Gray)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(Color(0xFFEEEEEE), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = questionPreview, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row {
                Icon(Icons.Default.FavoriteBorder, contentDescription = "Like", tint = buttonContentColor)
                Spacer(modifier = Modifier.width(16.dp))
                Icon(Icons.Default.Comment, contentDescription = "Comment", tint = buttonContentColor)
            }
        }
    }
}

