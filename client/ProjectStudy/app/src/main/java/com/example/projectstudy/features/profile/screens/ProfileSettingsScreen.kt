package com.example.projectstudy.features.profile.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSettingsScreen(
    onNavigateBack: () -> Unit,
    onLogoutClick: () -> Unit = {}
) {
    // TODO: nao tem implementacao real ainda
    var isDarkMode by remember { mutableStateOf(false) }
    var isFocusMode by remember { mutableStateOf(false) }
    var hideRankings by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configurações", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
        ) {
            SettingsSectionTitle(title = "Conta e Perfil")

            SettingsClickableItem(
                icon = Icons.Outlined.Person,
                title = "Editar Informações",
                subtitle = "Nome, username e curso",
                onClick = { /* TODO: Navegar para edição */ }
            )

            SettingsClickableItem(
                icon = Icons.Outlined.Face,
                title = "Alterar Avatar",
                subtitle = "Personalize sua foto de perfil",
                onClick = { /* TODO: Abrir modal de avatar */ }
            )

            SettingsClickableItem(
                icon = Icons.Outlined.Lock,
                title = "Segurança e Senha",
                subtitle = "Altere sua senha de acesso",
                onClick = { /* TODO: Navegar para alterar senha */ }
            )

            Divider()

            SettingsSectionTitle(title = "Interface e Experiência")

            SettingsSwitchItem(
                icon = Icons.Outlined.DarkMode,
                title = "Tema Escuro",
                subtitle = "Alternar aparência do aplicativo",
                isChecked = isDarkMode,
                onCheckedChange = { isDarkMode = it }
            )

            SettingsSwitchItem(
                icon = Icons.Outlined.VisibilityOff,
                title = "Ocultar Rankings",
                subtitle = "Esconde sua posição para focar apenas no seu progresso",
                isChecked = hideRankings,
                onCheckedChange = { hideRankings = it }
            )

            SettingsSwitchItem(
                icon = Icons.Outlined.CenterFocusStrong,
                title = "Modo Foco",
                subtitle = "Reduz distrações visuais durante sessões ativas",
                isChecked = isFocusMode,
                onCheckedChange = { isFocusMode = it }
            )

            Divider()

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onLogoutClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.Logout,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Sair da Conta")
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun SettingsSectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp, end = 16.dp)
    )
}



// TODO: mover pra components

@Composable
fun SettingsClickableItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun SettingsSwitchItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!isChecked) }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
private fun Divider() {
    HorizontalDivider(
        modifier = Modifier.padding(top = 8.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    )
}