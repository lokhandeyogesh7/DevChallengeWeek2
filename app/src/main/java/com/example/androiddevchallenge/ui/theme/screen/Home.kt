package com.example.androiddevchallenge.ui.theme.screen

import android.os.CountDownTimer
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.RoundView
import com.example.androiddevchallenge.timer

@Composable
fun HomeScreen() {
    Surface(color = MaterialTheme.colors.background) {
        val timeElapsed = remember(calculation = { mutableStateOf(0L) })
        val scaffoldState =
            rememberScaffoldState(rememberDrawerState(initialValue = DrawerValue.Closed))
        val coroutineScope = rememberCoroutineScope()
        val totalTime = 60
        val countStarted = remember(calculation = { mutableStateOf(false) })

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Count-Er")
                    },
                    elevation = 12.dp,
                )
            }, content = {
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
                                startTimer(timeElapsed,totalTime.toLong(),countStarted)
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