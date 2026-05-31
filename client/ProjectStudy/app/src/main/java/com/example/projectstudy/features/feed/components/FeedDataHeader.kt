package com.example.projectstudy.features.feed.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun FeedDateHeader(
    label: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = label,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 6.dp, bottom = 2.dp),
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.45f),
        textAlign = TextAlign.Center
    )
}