package com.example.projectstudy.features.feed.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RankingSide(
                entry = leader,
                value = leaderValue,
                label = leader?.let { "${it.position}º lugar" } ?: "Sem ranking",
                horizontalAlignment = Alignment.Start
            )

            RankingSide(
                entry = currentUserEntry,
                value = currentUserValue,
                label = "Você",
                horizontalAlignment = Alignment.End
            )
        }
    }
}

@Composable
private fun RankingSide(
    entry: RankingEntry?,
    value: String,
    label: String,
    horizontalAlignment: Alignment.Horizontal,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (horizontalAlignment == Alignment.End) {
            RankingTexts(
                value = value,
                label = label,
                horizontalAlignment = Alignment.End
            )

            RankingAvatar(entry = entry)
        } else {
            RankingAvatar(entry = entry)

            RankingTexts(
                value = value,
                label = label,
                horizontalAlignment = Alignment.Start
            )
        }
    }
}

@Composable
private fun RankingAvatar(
    entry: RankingEntry?
) {
    if (entry != null) {
        LumioAvatar(
            initials = entry.user.avatarInitials,
            avatarUrl = entry.user.avatarUrl,
            colorKey = entry.user.id,
            size = 48.dp
        )
    } else {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.14f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "—",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Black
            )
        }
    }
}

@Composable
private fun RankingTexts(
    value: String,
    label: String,
    horizontalAlignment: Alignment.Horizontal
) {
    Column(
        horizontalAlignment = horizontalAlignment
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Black
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
        )
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