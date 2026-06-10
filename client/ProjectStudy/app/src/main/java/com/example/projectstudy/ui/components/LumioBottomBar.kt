package com.example.projectstudy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.projectstudy.navigation.AppRoute
import com.example.projectstudy.navigation.MainBottomTab
import androidx.compose.foundation.border
import androidx.compose.ui.graphics.Color
import com.example.projectstudy.ui.theme.LumioTheme

@Composable
fun LumioBottomBar(
    currentRoute: AppRoute?,
    onTabSelected: (MainBottomTab) -> Unit,
    modifier: Modifier = Modifier
) {

    val barColor = if (currentRoute != null) {
        Color(0xFFF0F6EA)
    } else {
        MaterialTheme.colorScheme.surface
    }

    val borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.45f)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 18.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .widthIn(max = 520.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(42.dp))
                .background(LumioTheme.colors.floatingBar)
                .border(
                    width = 1.dp,
                    color = LumioTheme.colors.floatingBarBorder,
                    shape = RoundedCornerShape(42.dp)
                )
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            MainBottomTab.entries.forEach { tab ->
                LumioBottomBarItem(
                    label = tab.label,
                    icon = tab.icon,
                    selected = currentRoute == tab.route,
                    onClick = {
                        onTabSelected(tab)
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
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
    val itemBackground = if (selected) {
        LumioTheme.colors.floatingBarSelected
    } else {
        Color.Transparent
    }

    val contentColor = if (selected) {
        MaterialTheme.colorScheme.primary
    } else {
        LumioTheme.colors.floatingBarInactive
    }

    Column(
        modifier = modifier
            .height(56.dp)
            .clip(RoundedCornerShape(34.dp))
            .background(itemBackground)
            .clickable {
                onClick()
            }
            .padding(vertical = 7.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(19.dp),
            tint = contentColor
        )

        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = contentColor,
            fontWeight = if (selected) {
                FontWeight.Bold
            } else {
                FontWeight.Medium
            }
        )
    }
}