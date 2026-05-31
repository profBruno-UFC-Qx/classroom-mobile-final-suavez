package com.example.projectstudy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Leaderboard
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.projectstudy.navigation.MainBottomTab

@Composable
fun LumioBottomBar(
    selectedTab: MainBottomTab,
    onTabSelected: (MainBottomTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .navigationBarsPadding()
            .padding(horizontal = 12.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LumioBottomBarItem(
            label = "Grupo",
            icon = Icons.Outlined.Groups,
            selected = selectedTab == MainBottomTab.GROUP,
            onClick = {
                onTabSelected(MainBottomTab.GROUP)
            },
            modifier = Modifier.weight(1f)
        )

        LumioBottomBarItem(
            label = "Ranking",
            icon = Icons.Outlined.Leaderboard,
            selected = selectedTab == MainBottomTab.RANKING,
            onClick = {
                onTabSelected(MainBottomTab.RANKING)
            },
            modifier = Modifier.weight(1f)
        )

        LumioBottomBarItem(
            label = "Perfil",
            icon = Icons.Outlined.Person,
            selected = selectedTab == MainBottomTab.PROFILE,
            onClick = {
                onTabSelected(MainBottomTab.PROFILE)
            },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun LumioBottomBarItem(
    label: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val contentColor = if (selected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.45f)
    }

    val indicatorColor = if (selected) {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
    } else {
        MaterialTheme.colorScheme.background
    }

    Column(
        modifier = modifier
            .padding(horizontal = 4.dp)
            .clip(RoundedCornerShape(14.dp))
            .clickable {
                onClick()
            }
            .background(indicatorColor)
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(20.dp),
            tint = contentColor
        )

        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = contentColor
        )
    }
}