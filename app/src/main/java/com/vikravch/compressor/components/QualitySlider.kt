package com.vikravch.compressor.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun QualitySlider(
    value: Float,
    onValueChange: (Float) -> Unit
){

    Column(
        modifier = Modifier.padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 0f..100f,
            steps = 99,
            modifier = Modifier.padding(end = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            (0..100 step 20).forEach { label ->
                Text(text = "$label")
            }
        }
    }
}