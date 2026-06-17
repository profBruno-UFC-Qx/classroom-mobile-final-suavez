package com.example.projectstudy.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.projectstudy.navigation.AppRoute
import com.example.projectstudy.navigation.MainBottomTab
import com.example.projectstudy.ui.theme.LumioTheme

/**
 * Barra inferior flutuante usada nas principais telas autenticadas do app.
 *
 * O componente funciona como overlay sobre a tela, sem depender de Scaffold.
 * O visual segue o formato de uma pílula compacta e centralizada.
 */
@Composable
fun LumioBottomBar(
    currentRoute: AppRoute?,
    onTabSelected: (MainBottomTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .widthIn(max = 320.dp)
            .fillMaxWidth(0.74f)
            .height(58.dp)
            .clip(RoundedCornerShape(999.dp))
            .background(LumioTheme.colors.floatingBar)
            .border(
                width = 1.dp,
                color = LumioTheme.colors.floatingBarBorder,
                shape = RoundedCornerShape(999.dp)
            )
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
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

/**
 * Item individual da barra inferior.
 *
 * Quando selecionado, recebe um fundo arredondado interno semelhante ao efeito
 * de seleção da navbar usada como referência.
 */
@Composable
private fun LumioBottomBarItem(
    label: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val itemBackground by animateColorAsState(
        targetValue = if (selected) {
            LumioTheme.colors.floatingBarSelected
        } else {
            Color.Transparent
        },
        label = "bottom_bar_item_background"
    )

    val contentColor by animateColorAsState(
        targetValue = if (selected) {
            MaterialTheme.colorScheme.primary
        } else {
            LumioTheme.colors.floatingBarInactive
        },
        label = "bottom_bar_item_content"
    )

    Column(
        modifier = modifier
            .height(50.dp)
            .clip(RoundedCornerShape(999.dp))
            .background(itemBackground)
            .clickable { onClick() }
            .padding(horizontal = 4.dp, vertical = 5.dp)
            .semantics(mergeDescendants = true) { },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = contentColor
        )

        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = contentColor,
            fontWeight = if (selected) {
                FontWeight.Bold
            } else {
                FontWeight.Medium
            },
            maxLines = 1
        )
    }
}