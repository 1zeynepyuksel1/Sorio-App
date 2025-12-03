package com.zeynep1yuksel.baseapp.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zeynep1yuksel.baseapp.ui.auth.textFont
import com.zeynep1yuksel.baseapp.ui.theme.backgroundColor
import com.zeynep1yuksel.baseapp.ui.theme.buttonContentColor
import com.zeynep1yuksel.baseapp.ui.theme.darkBlue

@Composable
fun SorioButton(text: String, containerColor: Color, contentColor: Color,onClick:()->Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor.copy(0.85f),
            contentColor = contentColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        elevation = ButtonDefaults.buttonElevation(10.dp)
    ) {
        Text(text)
    }
}
@Composable
fun SamplePasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "Password" // Varsayılan etiket
) {
    // Şifrenin görünüp görünmediğini takip eden değişken
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,

        // Etiket (Senin fontunla)
        label = {
            Text(
                text = label,
                fontFamily = textFont,
                fontSize = 14.sp
            )
        },

        // Sol İkon (Kilit)
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Lock Icon",
                tint = Color.Gray
            )
        },

        // Sağ İkon (Göz - Tıklanabilir)
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Default.Visibility     // Açık Göz
            else
                Icons.Default.VisibilityOff  // Kapalı Göz

            // Butona basılınca 'passwordVisible' değerini tersine çevirir (true <-> false)
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    imageVector = image,
                    contentDescription = "Toggle Password",
                    tint = Color.Gray
                )
            }
        },

        // --- SİHİRLİ KISIM ---
        // Görünürse: VisualTransformation.None (Düz yazı)
        // Gizliyse: PasswordVisualTransformation (Noktalı)
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),

        // Tasarım Ayarları (Senin diğer alanlarınla aynı)
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = buttonContentColor, // Odaklanınca senin rengin
            focusedLabelColor = buttonContentColor,
            cursorColor = buttonContentColor,

            unfocusedBorderColor = Color.LightGray,
            unfocusedLabelColor = Color.Gray,

            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        ),

        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), // Klavye şifre modunda açılsın
        singleLine = true, // Tek satır
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp) // Altına biraz boşluk
    )
}
@Composable
fun SorioTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    keyboardType: KeyboardType
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                fontFamily = textFont,
                fontSize = 14.sp,
                color=Color.DarkGray
            )
        },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.DarkGray
            )
        },
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = buttonContentColor,
            focusedLabelColor = buttonContentColor,
            cursorColor = buttonContentColor,
            unfocusedBorderColor = Color.LightGray,
            unfocusedLabelColor = Color.Gray,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    )
}

@Composable
fun SorioBackButton(onClick:()->Unit) {
    IconButton(onClick=onClick, modifier = Modifier.padding(top=10.dp)) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint= buttonContentColor,
            modifier= Modifier.size(32.dp)
        )
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