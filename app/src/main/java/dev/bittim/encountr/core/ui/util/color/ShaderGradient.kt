/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ShaderGradient.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.08.25, 02:32
 */

package dev.bittim.encountr.core.ui.util.color

import android.graphics.RuntimeShader
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.toArgb
import org.intellij.lang.annotations.Language

@Language("AGSL")
val GRADIENT_SHADER = """
    uniform float2 resolution;
    layout(color) uniform half4 colorTopLeft;
    layout(color) uniform half4 colorTopRight;
    layout(color) uniform half4 colorBottomRight;
    layout(color) uniform half4 colorBottomLeft;

    half4 main(in float2 fragCoord) {
        float2 uv = fragCoord/resolution.xy;
        return mix(mix(mix(colorTopLeft, colorBottomLeft, uv.y),  mix(colorTopRight, colorBottomRight, uv.y), uv.x), half4(0, 0, 0, 1), 0.3);
    }
""".trimIndent()

class ShaderGradient() {
    private var topLeft: Color = Color.Unspecified
    private var topRight: Color = Color.Unspecified
    private var bottomRight: Color = Color.Unspecified
    private var bottomLeft: Color = Color.Unspecified
    private var disabledSaturation: Float = 0.3f
    private var enabledSaturation: Float = 1f

    companion object {
        fun fromList(list: List<Color>): ShaderGradient? {
            return when (list.size) {
                1 -> ShaderGradient(list[0])
                2 -> ShaderGradient(list[0], list[1])
                4 -> ShaderGradient(list[0], list[1], list[2], list[3])
                else -> null
            }
        }
    }

    constructor(
        color: Color,
        disabledSaturation: Float = 0.3f,
        enabledSaturation: Float = 1f
    ) : this() {
        this.topLeft = color
        this.topRight = color
        this.bottomRight = color
        this.bottomLeft = color
        this.disabledSaturation = disabledSaturation
        this.enabledSaturation = enabledSaturation
    }

    constructor(
        topLeftBottomRight: Color,
        topRightBottomLeft: Color,
        disabledSaturation: Float = 0.3f,
        enabledSaturation: Float = 1f
    ) : this() {
        this.topLeft = topLeftBottomRight
        this.topRight = topRightBottomLeft
        this.bottomRight = topLeftBottomRight
        this.bottomLeft = topRightBottomLeft
        this.disabledSaturation = disabledSaturation
        this.enabledSaturation = enabledSaturation
    }

    constructor(
        topLeft: Color,
        topRight: Color,
        bottomRight: Color,
        bottomLeft: Color,
        disabledSaturation: Float = 0.3f,
        enabledSaturation: Float = 1f
    ) : this() {
        this.topLeft = topLeft
        this.topRight = topRight
        this.bottomLeft = bottomLeft
        this.bottomRight = bottomRight
        this.disabledSaturation = disabledSaturation
        this.enabledSaturation = enabledSaturation
    }

    fun getColorTopLeft(isDisabled: Boolean): Color {
        return saturateColor(
            topLeft,
            if (isDisabled) disabledSaturation else enabledSaturation
        )
    }

    fun getColorTopRight(isDisabled: Boolean): Color {
        return saturateColor(
            topRight,
            if (isDisabled) disabledSaturation else enabledSaturation
        )
    }

    fun getColorBottomRight(isDisabled: Boolean): Color {
        return saturateColor(
            bottomRight,
            if (isDisabled) disabledSaturation else enabledSaturation
        )
    }

    fun getColorBottomLeft(isDisabled: Boolean): Color {
        return saturateColor(
            bottomLeft,
            if (isDisabled) disabledSaturation else enabledSaturation
        )
    }
}

fun Modifier.drawShaderGradient(gradient: ShaderGradient, isDisabled: Boolean): Modifier {
    return this.then(
        Modifier.drawWithCache {
            val shader = RuntimeShader(GRADIENT_SHADER)
            val shaderBrush = ShaderBrush(shader)
            shader.setFloatUniform(
                "resolution",
                size.width,
                size.height
            )
            onDrawBehind {
                shader.setColorUniform(
                    "colorTopLeft",
                    gradient.getColorTopLeft(isDisabled).toArgb()
                )
                shader.setColorUniform(
                    "colorTopRight",
                    gradient.getColorTopRight(isDisabled).toArgb()
                )
                shader.setColorUniform(
                    "colorBottomRight",
                    gradient.getColorBottomRight(isDisabled).toArgb()
                )
                shader.setColorUniform(
                    "colorBottomLeft",
                    gradient.getColorBottomLeft(isDisabled).toArgb()
                )
                drawRect(shaderBrush)
            }
        }
    )
}