package com.example.projectstudy.features.feed.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import com.example.projectstudy.core.util.toDurationText
import com.example.projectstudy.core.util.toHourText
import com.example.projectstudy.ui.components.LumioAvatar

import com.example.projectstudy.domain.model.StudyActivity

@Composable
fun ActivityCard(
    activity: StudyActivity,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(110.dp),
        shape = RoundedCornerShape(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxSize()
        ) {

            AsyncImage(
                model = activity.imageUrl,
                contentDescription = activity.title,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(120.dp),
                contentScale = ContentScale.Crop
            )


            Column (
                modifier = Modifier
                    .padding(16.dp)
            ){

                Text(
                    text = "${activity.title} - ${activity.durationMinutes.toDurationText()}",
                    style = MaterialTheme.typography.titleSmall
                )



                Spacer(modifier = Modifier.height(8.dp))

                //Text(
                //    text = activity.description,
                //    style = MaterialTheme.typography.bodyMedium
                //)

                //Spacer(modifier = Modifier.height(8.dp))

                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        LumioAvatar(
                            initials = activity.author.avatarInitials,
                            avatarUrl = activity.author.avatarUrl,
                            colorKey = activity.author.id,
                            size = 38.dp
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = activity.author.name,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    Text(
                        text = activity.createdAtMillis.toHourText(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}