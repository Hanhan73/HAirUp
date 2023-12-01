package com.bangkit.h_airup.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.h_airup.HAirUpApp
import com.bangkit.h_airup.R
import com.bangkit.h_airup.ui.theme.HAirUpTheme

@Composable
fun GreetingItem(
    name: String,
    modifier: Modifier = Modifier
){
    Row(
        verticalAlignment =Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ){
        Image(
            painter = painterResource(id = R.drawable.hand_waving),
            contentDescription = "Waving Hand",
            modifier = Modifier
                .size(50.dp)
                .padding(8.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.greeting),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
                fontSize = 14.sp
            )
            Text(
                text = "Good Morning to You $name!",
                fontSize = 12.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingItemPreview() {
    HAirUpTheme {
        GreetingItem(
            "Monkey D. Luffy"
        )
    }
}