/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui.theme.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.dp

var roundColor: Color? = null

@Composable
fun RoundView(modifier: Modifier, percentageTime: Float) {

    roundColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }

    Canvas(
        modifier = modifier,
    ) {
        val middle = Offset(size.minDimension / 2, size.minDimension / 2)
        drawCircle(
            color = roundColor!!,
            center = middle,
            radius = size.minDimension / 2,
            style = Stroke(4.dp.toPx()),
        )

        withTransform(
            {
            },
            {
                drawLine(
                    cap = StrokeCap.Butt,
                    strokeWidth = 5.dp.toPx(),
                    color = roundColor!!,
                    start = middle,
                    end = Offset(size.minDimension / 2, 10.dp.toPx())
                )
            }
        )
        withTransform(
            {
                rotate(360 * percentageTime, middle)
            },
            {
                drawLine(
                    strokeWidth = 8.dp.toPx(),
                    cap = StrokeCap.Round,
                    color = Color.Red,
                    start = middle,
                    end = Offset(size.minDimension / 2, 12.dp.toPx())
                )
            }
        )
    }
}
