package com.zeynep1yuksel.baseapp.ui.home


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zeynep1yuksel.baseapp.R
import com.zeynep1yuksel.baseapp.model.StudySession
import com.zeynep1yuksel.baseapp.ui.theme.buttonContentColor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ProfileContent(
    profileViewModel: ProfileViewModel = viewModel()
) {
    val sessions by profileViewModel.sessions.collectAsState()
    var currentScreen by remember { mutableStateOf("menu") }
    val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    val todaySessions = sessions.filter { it.date == todayDate }
    val userGoal by profileViewModel.userGoal.collectAsState()
    val todayTotalSeconds = todaySessions.sumOf { it.duration }
    val todaySessionCount = todaySessions.size

    if (currentScreen=="sessions"){
        SessionContent(
            sessions=sessions
        )
    }
    else{
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            ProfileSummaryCard(totalSecondsToday = todayTotalSeconds, dailyGoalHours = userGoal, totalSessions = todaySessionCount)
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(Modifier.weight(1f)) {
                    CategoryItem("Kişisel Bilgiler",
                        painterResource(R.drawable.profile_image_round_1326_svgrepo_com),
                        onClickable = {}) }
                Box(Modifier.weight(1f)) {
                    CategoryItem(
                        "Oturumlarım",
                        painterResource(R.drawable.timer_svgrepo_com__2_),
                        onClickable ={currentScreen="sessions"}
                    )
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(Modifier.weight(1f)) { CategoryItem("Sorularım", painterResource(R.drawable.question_svgrepo_com), onClickable = {}) }
                Box(Modifier.weight(1f)) { CategoryItem("Cevaplarım", painterResource(R.drawable.test_svgrepo_com), onClickable = {}) }
            }
        }
    }
}
@Composable
fun CategoryItem(
    text:String,
    icon: Painter,
    onClickable:()->Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth().height(110.dp).clickable{onClickable()},
    ){
        Column(
            modifier=Modifier.padding(16.dp).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Icon(
                painter = icon,
                contentDescription = text,
                tint=buttonContentColor,
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier=Modifier.height(8.dp))
            Text(
                text,
                color=DarkText,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
    }
}
@Composable
fun ProfileSummaryCard(totalSecondsToday: Long, dailyGoalHours: Int, totalSessions: Int) {
    val goalInSeconds = dailyGoalHours * 3600L
    val progress = (totalSecondsToday.toFloat() / goalInSeconds).coerceIn(0f, 1f)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        colors = CardDefaults.cardColors(containerColor = buttonContentColor),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            // Üst Başlık ve Oturum Sayısı
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Bugünkü İlerlemen",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 14.sp,
                    fontFamily = logoFont
                )
                Surface(
                    color = Color.White.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "$totalSessions Oturum",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                CustomProgressWithIcon(
                    progress=progress,
                    currentTimeText = formatTime(totalSecondsToday)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            if (progress >= 1f) {
                Text(
                    "Hedefe ulaşıldı! Harikasın 🏆",
                    color = Color.Yellow,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                val remainingSeconds = goalInSeconds - totalSecondsToday
                Text(
                    text = "Hedefine ${formatTime(remainingSeconds)} kaldı",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 13.sp,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}
@Composable
fun CustomProgressWithIcon(
    progress: Float,
    currentTimeText: String
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        ) {
            val barWidth = maxWidth
            val indicatorPosition = barWidth * progress

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .align(Alignment.BottomCenter)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f))
            )

            Box(
                modifier = Modifier
                    .width(indicatorPosition)
                    .height(8.dp)
                    .align(Alignment.BottomStart)
                    .clip(CircleShape)
                    .background(Color.Yellow)
            )


            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .offset(x = indicatorPosition - 15.dp)
            ) {
                Text(
                    text = currentTimeText,
                    color = Color.Yellow,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = logoFont
                )
                Icon(
                    painter = painterResource(id = R.drawable.fire_svgrepo_com),
                    contentDescription = null,
                    tint=Color.Unspecified,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
