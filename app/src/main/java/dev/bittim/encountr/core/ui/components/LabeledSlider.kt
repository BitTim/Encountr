/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       LabeledSlider.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   02.09.25, 16:12
 */

package dev.bittim.encountr.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSliderState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import dev.bittim.encountr.core.ui.theme.Spacing
import dev.bittim.encountr.core.ui.util.UiText
import dev.bittim.encountr.core.ui.util.annotations.ComponentPreview

data object LabeledSliderDefaults {
    val spacing = Spacing.xxs
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabeledSlider(
    modifier: Modifier = Modifier,
    label: UiText,
    sliderState: SliderState,
    enabled: Boolean = true,
    spacing: Dp = LabeledSliderDefaults.spacing,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(spacing),
    ) {
        Text(text = label.asString(), style = MaterialTheme.typography.labelMedium)

        Slider(
            state = sliderState,
            enabled = enabled,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@ComponentPreview
@Composable
fun LabeledSliderPreview() {
    MaterialTheme {
        Surface {
            Column {
                LabeledSlider(
                    label = UiText.DynamicString("Default"),
                    sliderState = rememberSliderState()
                )

                LabeledSlider(
                    label = UiText.DynamicString("Progressed"),
                    sliderState = rememberSliderState(value = 0.5f)
                )

                LabeledSlider(
                    label = UiText.DynamicString("Stepped Default"),
                    sliderState = rememberSliderState(
                        valueRange = 0f..8f,
                        steps = 7
                    )
                )

                LabeledSlider(
                    label = UiText.DynamicString("Stepped Progressed"),
                    sliderState = rememberSliderState(
                        valueRange = 0f..8f,
                        steps = 7,
                        value = 3f
                    )
                )
            }
        }
    }
}