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
package com.example.androiddevchallenge.ui.theme.screen

import android.os.CountDownTimer
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.timer
import com.example.androiddevchallenge.ui.theme.component.RoundView

@Composable
fun HomeScreen() {
    Surface(color = MaterialTheme.colors.background) {
        val timeElapsed = remember(calculation = { mutableStateOf(0L) })
        val totalTime = 60
        val countStarted = remember(calculation = { mutableStateOf(false) })

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Count-Er")
                    },
                    elevation = 12.dp,
                )
            },
            content = {
                val percentageTime =
                    ((totalTime - timeElapsed.value) / totalTime.toFloat())
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RoundView(
                        modifier = Modifier
                            .height(350.dp)
                            .width(350.dp)
                            .padding(25.dp),
                        percentageTime = percentageTime,
                    )
                    Text(
                        text = timeElapsed.value.toString(),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = TextStyle(fontSize = 46.sp, fontWeight = FontWeight.Bold)
                    )
                    Button(
                        onClick = {
                            if (countStarted.value) {
                                countStarted.value = false
                                timer.cancel()
                                timer.onFinish()
                            } else {
                                countStarted.value = true
                                startTimer(timeElapsed, totalTime.toLong(), countStarted)
                            }
                        },
                        modifier = Modifier
                            .padding(15.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        if (timeElapsed.value == 0L) Text("Start") else Text("Stop")
                    }
                }
            }
        )
    }
}

private fun startTimer(
    timeElapsed: MutableState<Long>,
    totalTime: Long,
    countStarted: MutableState<Boolean>
) {
    timer = object : CountDownTimer(totalTime * 1000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            timeElapsed.value = millisUntilFinished / 1000
            countStarted.value = true
        }

        override fun onFinish() {
            countStarted.value = false
            timeElapsed.value = 0L
        }
    }
    timer.start()
}
