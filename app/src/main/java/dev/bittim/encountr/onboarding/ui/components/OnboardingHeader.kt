/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       OnboardingHeader.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   14.08.25, 03:45
 */

package dev.bittim.encountr.onboarding.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import dev.bittim.encountr.core.ui.theme.EncountrTheme
import dev.bittim.encountr.core.ui.theme.Spacing
import dev.bittim.encountr.core.ui.util.UiText
import dev.bittim.encountr.core.ui.util.annotations.ComponentPreview

@Composable
fun OnboardingHeader(
    modifier: Modifier = Modifier,
    title: UiText,
    progress: Float,
    description: UiText,
    icon: ImageVector? = null,
    minLines: Int = 2,
    maxLines: Int = 2
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.l)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.m),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedVisibility(
                modifier = Modifier
                    .height(Spacing.xxl)
                    .aspectRatio(1f),
                visible = icon != null
            ) {
                AnimatedContent(icon) {
                    it?.let { imageVector ->
                        Icon(
                            imageVector = imageVector,
                            contentDescription = title.asString(),
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
            }

            AnimatedContent(
                targetState = title, label = "Title content change",
            ) {
                Text(
                    text = it.asString(),
                    style = MaterialTheme.typography.headlineLarge
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            val animatedProgress = animateFloatAsState(
                targetValue = progress,
                animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
                label = "Progress animation"
            )
            CircularProgressIndicator(progress = { animatedProgress.value })
        }

        AnimatedContent(
            targetState = description, label = "Description content change",
        ) {
            Text(
                text = it.asString(),
                style = MaterialTheme.typography.bodyMedium,
                minLines = minLines,
                maxLines = maxLines,
                overflow = TextOverflow.Ellipsis,
                softWrap = true,
            )
        }
    }
}

@ComponentPreview
@Composable
fun OnboardingHeaderPreview() {
    EncountrTheme {
        Surface {
            OnboardingHeader(
                modifier = Modifier.fillMaxWidth(),
                title = UiText.DynamicString("Sample section"),
                progress = 0.37f,
                description = UiText.DynamicString("This is a sample section that is supposed to serve as a preview to this composable. If this text is visible, then this sample is working fine. If you see this in the finished app, something went wrong... Like really wrong"),
                icon = Icons.Default.Layers
            )
        }
    }
}