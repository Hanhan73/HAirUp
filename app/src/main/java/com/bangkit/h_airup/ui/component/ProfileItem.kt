package com.bangkit.h_airup.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.h_airup.ui.theme.HAirUpTheme
import com.bangkit.h_airup.ui.theme.md_theme_light_primary

@Composable
fun ProfileItem(
    name: String,
    city: String,
    province: String,
    modifier: Modifier = Modifier,

){
    Row(
        verticalAlignment =Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
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
            fontSize = 14.sp,
            color = md_theme_light_primary)
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier
        ) {
            Text(
                text = city,
                fontSize = 12.sp,
                modifier = Modifier.padding(end = 16.dp)
                    .align(Alignment.End),
                color = md_theme_light_primary
            )
            Text(
                text = province,
                fontSize = 12.sp,
                modifier = Modifier.padding(end = 16.dp)
                    .align(Alignment.End),
                color = md_theme_light_primary
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
fun ProfileItemPreview() {
    HAirUpTheme {
        ProfileItem(
            "Monkey D. Luffy",
            "Bandung",
            "Jawa Barat"
        )
    }
}