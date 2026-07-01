package com.example.projectstudy.features.ranking.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.projectstudy.core.util.toHourMinuteText
import com.example.projectstudy.domain.model.RankingEntry
import com.example.projectstudy.ui.components.LumioAvatar

@Composable
fun RankingTopThreeCard(
    entries: List<RankingEntry>,
    modifier: Modifier = Modifier
) {
    val first = entries.firstOrNull { entry -> entry.position == 1 }
    val second = entries.firstOrNull { entry -> entry.position == 2 }
    val third = entries.firstOrNull { entry -> entry.position == 3 }

    Card(
        modifier = modifier.border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.14f),
            shape = RoundedCornerShape(28.dp)
        ),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Top estudantes",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onSurface
            )

//            Text(
//                text = "Quem mais estudou no grupo",
//                style = MaterialTheme.typography.bodyMedium,
//                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
//            )

            Spacer(modifier = Modifier.height(22.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                RankingPodiumItem(
                    entry = second,
                    positionText = "2º",
                    modifier = Modifier.weight(1f)
                )

                RankingPodiumItem(
                    entry = first,
                    positionText = "1º",
                    isMain = true,
                    modifier = Modifier.weight(1.15f)
                )

                RankingPodiumItem(
                    entry = third,
                    positionText = "3º",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun RankingPodiumItem(
    entry: RankingEntry?,
    positionText: String,
    modifier: Modifier = Modifier,
    isMain: Boolean = false
) {
    val avatarSize = if (isMain) {
        66.dp
    } else {
        54.dp
    }

    val positionColor = if (isMain) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = positionText,
            style = if (isMain) {
                MaterialTheme.typography.titleLarge
            } else {
                MaterialTheme.typography.titleMedium
            },
            fontWeight = FontWeight.Black,
            color = positionColor
        )

        Spacer(modifier = Modifier.height(8.dp))

        LumioAvatar(
            initials = entry?.user?.avatarInitials ?: "-",
            avatarUrl = entry?.user?.avatarUrl ?: "",
            colorKey = entry?.user?.id ?: positionText,
            size = avatarSize,
            textStyle = if (isMain) {
                MaterialTheme.typography.titleMedium
            } else {
                MaterialTheme.typography.labelMedium
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = entry?.user?.name ?: "Sem aluno",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = entry?.let { rankingEntry ->
                rankingEntry.totalMinutes.toHourMinuteText()
            } ?: "0min",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}