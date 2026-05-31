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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.projectstudy.core.util.toDurationText
import com.example.projectstudy.domain.model.Group

@Composable
fun GroupBanner(
    group: Group,
    modifier: Modifier = Modifier
) {
    val progress = if (group.goalMinutes > 0) {
        group.currentMinutes.toFloat() / group.goalMinutes.toFloat()
    } else {
        0f
    }.coerceIn(0f, 1f)

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = group.bannerUrl,
                contentDescription = group.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(122.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = group.name,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

//                    if (group.isActive) {
//                        Box(
//                            modifier = Modifier
//                                .clip(RoundedCornerShape(50))
//                                .background(MaterialTheme.colorScheme.primary)
//                                .padding(horizontal = 8.dp, vertical = 3.dp),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Text(
//                                text = "Ativo",
//                                color = MaterialTheme.colorScheme.onPrimary,
//                                style = MaterialTheme.typography.labelMedium,
//                                fontWeight = FontWeight.Bold
//                            )
//                        }
//                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${group.memberCount} membros · ${group.description}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(12.dp))

                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(50)),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Meta: ${group.goalMinutes.toDurationText()}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)
                    )

                    Text(
                        text = "${group.currentMinutes.toDurationText()} estudados",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}