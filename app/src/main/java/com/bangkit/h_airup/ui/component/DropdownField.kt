package com.bangkit.h_airup.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bangkit.h_airup.ui.theme.md_theme_light_onSecondaryContainer
import com.bangkit.h_airup.ui.theme.md_theme_light_secondaryContainer

@Composable
fun DropdownField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    expanded: Boolean,
    items: List<Any>,
    onValueChange: (String) -> Unit,
    onExpand: () -> Unit,
    onClose: () -> Unit
) {
    Box(
        modifier = modifier
            .background(md_theme_light_secondaryContainer)
            .border(1.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.small)
            .clickable { onExpand() }
    ) {
        Text(
            text = if (value.isBlank()) "Select $label" else value,
            color = if (value.isBlank()) md_theme_light_onSecondaryContainer.copy(alpha = 0.6f) else md_theme_light_onSecondaryContainer,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onClose() },
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item.toString()) },
                    onClick = {
                        onValueChange(item.toString())

                        onClose()
                    }
                )
            }
        }
    }
}