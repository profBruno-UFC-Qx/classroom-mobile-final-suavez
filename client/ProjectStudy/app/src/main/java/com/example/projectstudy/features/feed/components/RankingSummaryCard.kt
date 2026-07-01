package com.example.projectstudy.features.feed.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.projectstudy.core.util.toDurationText
import com.example.projectstudy.domain.model.Group
import com.example.projectstudy.domain.model.RankingEntry
import com.example.projectstudy.domain.model.RankingMetric
import com.example.projectstudy.ui.components.LumioAvatar

@Composable
fun RankingSummaryCard(
    group: Group,
    ranking: List<RankingEntry>,
    modifier: Modifier = Modifier
) {
    val leader = ranking.firstOrNull()

    val currentUserEntry = ranking.firstOrNull { entry ->
        entry.isCurrentUser
    }

    val leaderValue = leader.toRankingValueText(group.rankingMetric)
    val currentUserValue = currentUserEntry.toRankingValueText(group.rankingMetric)

    val leaderLabel = when {
        leader == null -> "Sem ranking"

        currentUserEntry != null &&
                currentUserEntry.position == leader.position &&
                currentUserEntry.user.id != leader.user.id -> {
            "${leader.position}º empatado"
        }

        else -> "${leader.position}º lugar"
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RankingSide(
                entry = leader,
                value = leaderValue,
                label = leaderLabel,
                modifier = Modifier.weight(1f)
            )

            Box(
                modifier = Modifier
                    .height(42.dp)
                    .width(1.dp)
                    .background(
                        MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
                    )
            )

            RankingSide(
                entry = currentUserEntry,
                value = currentUserValue,
                label = "Você",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun RankingSide(
    entry: RankingEntry?,
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (entry != null) {
            LumioAvatar(
                initials = entry.user.avatarInitials,
                avatarUrl = entry.user.avatarUrl,
                colorKey = entry.user.id,
                size = 42.dp
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column {
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Black
            )

            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

private fun RankingEntry?.toRankingValueText(
    metric: RankingMetric
): String {
    if (this == null) {
        return "-"
    }

    return when (metric) {
        RankingMetric.TIME -> totalMinutes.toDurationText()
        RankingMetric.DAYS -> "$activeDays dias"
    }
}