package com.example.projectstudy.features.ranking.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.example.projectstudy.core.util.toHourMinuteText
import com.example.projectstudy.domain.model.RankingEntry
import com.example.projectstudy.ui.components.LumioAvatar

@Composable
fun RankingEntryCard(
    entry: RankingEntry,
    modifier: Modifier = Modifier
) {
    val borderColor = if (entry.isCurrentUser) {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.45f)
    } else {
        MaterialTheme.colorScheme.outline.copy(alpha = 0.14f)
    }

    val containerColor = if (entry.isCurrentUser) {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
    } else {
        MaterialTheme.colorScheme.surface
    }

    Card(
        modifier = modifier.border(
            width = 1.dp,
            color = borderColor,
            shape = RoundedCornerShape(20.dp)
        ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(76.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))

            RankingPositionBadge(
                position = entry.position,
                isCurrentUser = entry.isCurrentUser
            )

            Spacer(modifier = Modifier.width(14.dp))

            LumioAvatar(
                initials = entry.user.avatarInitials,
                avatarUrl = entry.user.avatarUrl,
                colorKey = entry.user.id,
                size = 44.dp
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = entry.user.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "${entry.user.username} - ${entry.activeDays} dias",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Column(
                modifier = Modifier.widthIn(
                    min = 72.dp,
                    max = 92.dp
                ),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = entry.totalMinutes.toHourMinuteText(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1
                )

                Text(
                    text = "total",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f),
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}

@Composable
private fun RankingPositionBadge(
    position: Int,
    isCurrentUser: Boolean
) {
    val backgroundColor = if (isCurrentUser) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
    }

    val textColor = if (isCurrentUser) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
    }

    Box(
        modifier = Modifier
            .size(34.dp)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = position.toString(),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Black,
            color = textColor
        )
    }
}