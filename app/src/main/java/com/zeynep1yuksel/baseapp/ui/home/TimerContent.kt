package com.zeynep1yuksel.baseapp.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zeynep1yuksel.baseapp.model.TimerStatus
import com.zeynep1yuksel.baseapp.ui.auth.logoFont

@Composable
fun TimerContent(
    formattedTime: String,
    timerStatus: TimerStatus,
    studyTimeSeconds: Long,//viewmodelden gelecek.
    onToggleClick: () -> Unit,
    onResetClick: () -> Unit,
    onSaveClick: () -> Unit,
    motivationText: String
) {
    val buttonText = when (timerStatus) {
        TimerStatus.RUNNING -> "Durdur"
        TimerStatus.PAUSED -> "Devam Et"
        TimerStatus.RESET -> "Başlat"
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
    ) {
        AnimatedTimerCircle(
            seconds = studyTimeSeconds,
            accentColor = Color(0xFF6FFFE9)
        )
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = formattedTime,
            fontSize = 64.sp,
            fontFamily = logoFont,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(40.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            val mainButtonColor =
                if (timerStatus == TimerStatus.RUNNING) Color(0xFFFF5252) else Color(0xFF6FFFE9)
            val mainButtonIcon =
                if (timerStatus == TimerStatus.RUNNING) Icons.Default.Pause else Icons.Default.PlayArrow

            Button(
                onClick = onToggleClick,
                shape = CircleShape,
                modifier = Modifier.size(80.dp),
                colors = ButtonDefaults.buttonColors(containerColor = mainButtonColor),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Icon(
                    imageVector = mainButtonIcon,
                    contentDescription = null,
                    tint = Color(0xFF0B132B),
                    modifier = Modifier.size(40.dp)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = onResetClick,
                    modifier = Modifier
                        .height(56.dp)
                        .weight(1f),
                    enabled = timerStatus != TimerStatus.RESET,
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        Icons.Default.Refresh,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Sıfırla")
                }


                Button(
                    onClick = onSaveClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    enabled = formattedTime != "00:00" && timerStatus != TimerStatus.RUNNING,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50),
                        disabledContainerColor = Color(0xFF4CAF50).copy(alpha = 0.2f)
                    )
                ) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Kaydet", softWrap = false)
                }
                Spacer(modifier = Modifier.height(48.dp))

                AnimatedVisibility(
                    visible = timerStatus == TimerStatus.RUNNING,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Text(
                        text = motivationText,
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Italic,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(horizontal = 32.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}
fun formatTime(seconds: Long): String {
    val hours = seconds/3600
    val minutes=(seconds%3600) / 60
    val secs = seconds %60
    return if (hours >0){
        String.format("%02d:%02d:%02d",hours,minutes,secs)
    } else{
        String.format("%02d:%02d",minutes,secs)
    }
}

@Composable
fun AnimatedTimerCircle(
    seconds: Long,
    modifier: Modifier = Modifier,
    accentColor: Color = Color(0xFF6FFFE9)
) {
    val sweepAngle = (seconds % 60) * 6f

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(200.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 12.dp.toPx()
            drawCircle(
                color = accentColor.copy(alpha = 0.1f),
                style = Stroke(width = strokeWidth)
            )
            drawArc(
                color = if (seconds % 60 == 0L && seconds > 0) Color.Yellow else accentColor,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }
        Icon(
            imageVector = Icons.Default.Timer,
            contentDescription = null,
            tint = accentColor.copy(alpha = 0.5f),
            modifier = Modifier.size(40.dp)
        )
    }
}