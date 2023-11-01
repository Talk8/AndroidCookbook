package com.android.cookbook.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.android.cookbook.navigation.LocalNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Ex009SliderScreen(title : String) {
    val navController = LocalNavController.current
    Scaffold(
        topBar = { CommonCenterAppBar(title, navController = navController) },
    ) { innerPadding ->
        SliderMainScreen(paddingValues = innerPadding)
    }
}

@Composable
fun SliderMainScreen(paddingValues: PaddingValues) {
    var sliderPosition by remember {mutableStateOf(0f)}
    var sliderPosition2 by remember {mutableStateOf(0f)}
    Column(
        //不添加scaffold传入的padding会让topBar和第一行内容重叠
        //添加后第一行内容从topBar下方开始布局
        modifier = Modifier.padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        //slider最基本的用法
        Slider(
            modifier = Modifier.padding(horizontal = 16.dp),
            value = sliderPosition,
            onValueChange = {sliderPosition = it}
        )

        //在slider中添加值的范围和步进值，通过Text显示它的步进值
        Slider(
            modifier = Modifier.padding(horizontal = 16.dp),
            value = sliderPosition,
            //滑动值发生变化时调用
            onValueChange = { sliderPosition = it },
            //滑动值的范围，默认0-1，值为浮点类型
            valueRange = 1f..10f,
            //滑动的步进值,注意需要用range除setps+1才是真正的步进值，比如这里的步进值是10/5=2
            //它会在进度显示4个分段标记
            steps = 4
        )
        Text(text = sliderPosition.toString())

         //在slider中添加值的范围和步进值，通过Text显示它的步进值
        Slider(
            modifier = Modifier.padding(horizontal = 16.dp),
            value = sliderPosition2,
//            enabled = false,
            //滑动值发生变化时调用
            onValueChange = { sliderPosition2 = it },

            colors = SliderDefaults.colors(
                //滑动条头部的颜色
                thumbColor = Color.Red,
                //滑动轨道的颜色
                activeTrackColor = Color.Green,
                inactiveTrackColor = Color.Blue,
                //滑动轨道上刻度尺的颜色
                activeTickColor = Color.White,
                inactiveTickColor = Color.Red,
            ),
            //滑动值的范围，默认0-1，值为浮点类型
            valueRange = 1f..10f,
            //滑动的步进值,注意需要用range除setps+1才是真正的步进值，比如这里的步进值是10/5=2
            //它会在进度显示4个分段标记
            steps = 4,
        )
        Text(text = sliderPosition2.toString())
    }
}