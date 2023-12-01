package com.bangkit.h_airup.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.h_airup.HAirUpApp
import com.bangkit.h_airup.ui.theme.HAirUpTheme

@Composable
fun ProfileItem(
    name: String,
    location: String,
    modifier: Modifier = Modifier
){
    Row(
        verticalAlignment =Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ){
        Image(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Account",
            modifier = Modifier
                .size(50.dp)
                .padding(8.dp)
        )
        Text(
            text = name,
            fontSize = 14.sp)
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = location,
            fontSize = 12.sp,
            modifier = Modifier.padding(end = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileItemPreview() {
    HAirUpTheme {
        ProfileItem(
            "Monkey D. Luffy",
            "Bandung, Jawa Barat",
        )
    }
}