package com.example.firebaseapp.screens.settings.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times

@Composable
fun ThemeSwitcher(
    theme: String = "system",
    onSelectTheme: (String) -> Unit = {}
) {
    val containerWidth = LocalConfiguration.current.screenWidthDp.dp - 24.dp

    val themes = listOf("system", "light", "dark")

    val selectedIndex = rememberSaveable(theme) {
        mutableIntStateOf(themes.indexOf(theme))
    }

    val backgroundOffset by animateDpAsState(
        targetValue = selectedIndex.intValue * (1 / 3f * containerWidth),
        label = "Background Offset"
    )

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(40.dp)
        .clip(shape = CircleShape)
        .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        // Active theme highlight
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(1 / 3f * containerWidth)
                .offset(x = backgroundOffset)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
        )

        Row(modifier = Modifier.fillMaxSize()) {
            themes.forEachIndexed { index, theme ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }) {
                            selectedIndex.intValue = index
                            onSelectTheme(theme)
                        }
                ) {
                    Text(
                        text = theme.replaceFirstChar { char -> char.uppercase() },
                        textAlign = TextAlign.Center,
                        color = if (selectedIndex.intValue == index) MaterialTheme.colorScheme.onPrimary
                                else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}