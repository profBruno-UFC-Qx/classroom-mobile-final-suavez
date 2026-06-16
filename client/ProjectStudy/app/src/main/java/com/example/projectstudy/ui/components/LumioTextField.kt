package com.example.projectstudy.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

enum class LumioTextFieldStyle {
    Embedded,
    Standalone
}

@Composable
fun LumioTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,

    placeholder: String? = null,

    leadingIcon: ImageVector? = null,
    trailingIcon: (@Composable () -> Unit)? = null,

    isError: Boolean = false,
    errorMessage: String? = null,

    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,

    singleLine: Boolean = false,
    minLines: Int = 1,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,

    style: LumioTextFieldStyle = LumioTextFieldStyle.Embedded
) {
    val hasError = isError || errorMessage != null

    val containerColor = when (style) {
        LumioTextFieldStyle.Embedded -> {
            MaterialTheme.colorScheme.background
        }

        LumioTextFieldStyle.Standalone -> {
            MaterialTheme.colorScheme.surface
        }
    }

    val unfocusedBorderColor = when (style) {
        LumioTextFieldStyle.Embedded -> {
            MaterialTheme.colorScheme.background
        }

        LumioTextFieldStyle.Standalone -> {
            MaterialTheme.colorScheme.outline.copy(alpha = 0.45f)
        }
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = {
            Text(text = label)
        },
        placeholder = placeholder?.let { placeholderText ->
            {
                Text(
                    text = placeholderText,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }
        },
        leadingIcon = leadingIcon?.let { icon ->
            {
                Icon(
                    imageVector = icon,
                    contentDescription = null
                )
            }
        },
        trailingIcon = trailingIcon,
        isError = hasError,
        supportingText = {
            errorMessage?.let { error ->
                Text(text = error)
            }
        },
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        singleLine = singleLine,
        minLines = minLines,
        maxLines = maxLines,
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            disabledContainerColor = containerColor,

            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = unfocusedBorderColor,
            disabledBorderColor = unfocusedBorderColor,

            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),

            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,

            cursorColor = MaterialTheme.colorScheme.primary,

            errorContainerColor = containerColor,
            errorBorderColor = MaterialTheme.colorScheme.error,
            errorLabelColor = MaterialTheme.colorScheme.error,
            errorTextColor = MaterialTheme.colorScheme.onSurface,
            errorCursorColor = MaterialTheme.colorScheme.error,

            focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            errorLeadingIconColor = MaterialTheme.colorScheme.error,

            focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            errorTrailingIconColor = MaterialTheme.colorScheme.error
        )
    )
}