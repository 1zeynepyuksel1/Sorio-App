package com.zeynep1yuksel.baseapp.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zeynep1yuksel.baseapp.R
import com.zeynep1yuksel.baseapp.model.StudySession
import com.zeynep1yuksel.baseapp.ui.theme.buttonContentColor
import com.zeynep1yuksel.baseapp.ui.auth.logoFont
import com.zeynep1yuksel.baseapp.ui.components.SorioBackButton
import com.zeynep1yuksel.baseapp.ui.components.SorioButton
import java.text.SimpleDateFormat
import java.util.Locale

val logoFont = FontFamily(
    Font(R.font.rubik, FontWeight.ExtraBold)
)

@Composable
fun SessionContent(
    sessions: List<StudySession>
) {
    val groupedSessions = sessions.groupBy { it.date }.toSortedMap(reverseOrder())
    var selectedDate by remember { mutableStateOf<String?>(null) }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        if (selectedDate == null) {
            Text(
                "Çalışma Günlerim",
                fontSize = 24.sp,
                fontFamily = logoFont,
                color = buttonContentColor
            )
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(groupedSessions.keys.toList()) { date ->
                    val sessionsOfDate = groupedSessions[date] ?: emptyList()
                    val dailyTotalSeconds = sessionsOfDate.sumOf { it.duration }
                    DateGroupCard(
                        date = date,
                        sessionCount = groupedSessions[date]?.size ?: 0,
                        onClick = { selectedDate = date },
                        totalDuration = dailyTotalSeconds
                    )
                }
            }
        } else {
            Text(
                text = formatDateString(selectedDate!!),
                fontSize = 22.sp,
                fontFamily = logoFont,
                color = buttonContentColor
            )
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 20.dp)
            ) {
                items(groupedSessions[selectedDate] ?: emptyList()) { session ->
                    SessionItem(session)
                }
            }
        }
    }
}

@Composable
fun SessionItem(session: StudySession) {
    val cardColor = getSessionColor(session.duration)
    val textColor = if (cardColor == buttonContentColor) Color.White else DarkText
    Card(
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Icon(
                painter = painterResource(R.drawable.timer_svgrepo_com__1_),
                contentDescription = "",
                tint = textColor,
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    formatTime(session.duration),
                    fontSize = 25.sp,
                    color = textColor,
                    fontFamily = logoFont,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${session.date}  •  ${session.time}",
                    color = textColor.copy(alpha = 0.7f),
                    fontSize = 12.sp,
                    fontFamily = logoFont,
                    fontWeight = FontWeight.Medium
                )
            }

        }

    }
}

@Composable
fun getSessionColor(durationSeconds: Long): Color {
    val minutes = durationSeconds
    return when {
        minutes < 20 -> Color(0xFFD1D9E6)
        minutes < 40 -> Color(0xFF7A91B1)
        else -> buttonContentColor
    }
}

fun formatDateString(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = inputFormat.parse(dateString)

        val outputFormat = SimpleDateFormat("d MMMM EEEE", Locale("tr"))

        date?.let { outputFormat.format(it) } ?: dateString
    } catch (e: Exception) {
        dateString
    }
}

@Composable
fun DateGroupCard(
    date: String,
    sessionCount: Int,
    totalDuration: Long,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = formatDateString(date),
                    fontWeight = FontWeight.Bold,
                    color = DarkText,
                    fontSize = 18.sp
                )
                Text(
                    text = "$sessionCount Oturum",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = formatTime(totalDuration),
                    fontWeight = FontWeight.ExtraBold,
                    color = buttonContentColor,
                    fontSize = 16.sp,
                    fontFamily = logoFont
                )
                Text(
                    text = "Toplam",
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = buttonContentColor
            )
        }
    }
}




