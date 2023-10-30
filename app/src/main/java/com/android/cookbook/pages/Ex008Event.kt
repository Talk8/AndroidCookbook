package com.android.cookbook.pages

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.android.cookbook.navigation.LocalNavController
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Ex008EventScreen(title: String) {
    val navController = LocalNavController.current
    Scaffold(
        topBar = { CommonCenterAppBar(title, navController = navController) },
    ) { innerPadding ->
       EventMainScreen(paddingValue = innerPadding)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EventMainScreen(paddingValue:PaddingValues) {
    val TAG = "Event"
    Column(
        modifier = Modifier
            .padding(paddingValue)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        //用来显示发出的event
        val defaultStr = "default value"
        var str by remember { mutableStateOf(defaultStr) }

        //clickable修饰符可以检测好点击事件，并且产生涟漪效果
        Text(modifier =
            Modifier.clickable { str = "clicked" },
            text = str)

        //combinedClickable修饰符可以检测双击，长按事件
        Text(modifier =
            Modifier.combinedClickable(
                onClick = {str = "onClicked"},
                onClickLabel = "clickLabel",
                onDoubleClick = {str = "onDoubleClick"},
                onLongClick = {str = "onLongClick"}
            ),
            text = str)

        //pointerInput修饰符用来检测tap和drag手势
        Text(modifier = Modifier.pointerInput(Unit) { detectTapGestures(
            //先运行onPress再运行onTap
            onTap = { str = "onTap"},
            onDoubleTap = {str = "onDoubleTap"},
            onPress = {str = "onPress"},
            onLongPress = {str = "onLongPress"}
        ) },
            text = str)



        var offsetX by remember { mutableStateOf(0f) }
        var offsetY by remember { mutableStateOf(0f) }

        //响应简单的拖动，在拖动事件中打印偏移值,只能是水平或者垂直方向的拖动
        Text(modifier = Modifier
            .offset {
                IntOffset(offsetX.roundToInt(), offsetY.roundToInt())
            }
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState(onDelta = { offsetX += it }),
                onDragStarted = { offset -> str = offset.toString() },
                onDragStopped = { offset -> str = offset.toString() },
            ),
            text = "Pls Drag")

        //响应复杂的拖动
        Box(modifier = Modifier
            .size(200.dp)
            .background(color = Color.Yellow)
        ) {
            Text(modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .size(60.dp)
                .background(color = Color.Green)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        //change中包含的全是偏移值，原始坐标值如何查看
                        Log.d(TAG, "EventMainScreen: $change")
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                },
                text ="Drag"
            )
        }

        //添加滑动swip事件
        //添加滚动动事件

        //多点解控
    }

}