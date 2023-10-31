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
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.android.cookbook.navigation.LocalNavController
import kotlin.math.roundToInt

//事件主要分点击和拖动，点击又分单击，双击，长按。
//官方文档称xxxable修饰符为高级事件，带有涟漪效果，xxxGestures修饰符为低级事件
//低级事件中可以获取到坐标偏移值，高级事件则不能

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

        //combinedClickable修饰符可以检测双击，长按事件,官方文档中没有介绍此接口
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
            //先运行onPress再运行onTap,onPress相当于View中的EventDown,onTap相当于Up
            //通过参数offset可以获取到偏移值
            onTap = { offset -> str = "onTap: $offset" },
            onDoubleTap = {str = "onDoubleTap"},
            onPress = {offset -> str = "onPress: $offset"},
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
        //滑动(swip)事件,官方文档中还有这方面的知识，API中提示is deprecated,这里不做介绍

        //滚动修饰符有3种：verticalScroll、horizontalScroll和scrollable,前两个是高级修饰符，可以滚动被修饰的组件，
        //最后一个是低级的组件，不能滑动组件，但是可以获取到滚动的偏移值

        //滚动动事件,当前显示内容无法全部在区域内显示时才可以滑动
        //还有一个水平滚动修饰符horizontalScroll()，用法相同，不做介绍
        val scrollState = rememberScrollState()
        //指定滚动到的位置，这个值与滚动区域的大小有关，也就是代码中的120dp
        LaunchedEffect(Unit) { scrollState.animateScrollTo(60)}

        Column(
            modifier = Modifier
                .height(120.dp) //限定区域大小
                .background(color = Color.LightGray)
//                .verticalScroll(rememberScrollState())
                .verticalScroll(scrollState)
        ) {
           repeat(5) {
               Text(
                   modifier = Modifier.padding(8.dp),
                   text = "This is No: $it")
           }
        }

        //通过ScrollableState修饰符获取滚动的偏移值
        var scrollOffset by remember { mutableStateOf(0f)}
        Box(modifier = Modifier
            .height(80.dp)
            .scrollable(
                orientation = Orientation.Vertical,
                state = rememberScrollableState { consumeScrollDelta ->
                    scrollOffset += consumeScrollDelta
                    consumeScrollDelta
                }
            )
        ) { //在文本上显示滚动偏移值
            Text(text = if(scrollOffset == 0f) "ScrollableState"
                else scrollOffset.toString())
        }

        //滚动修饰符自带嵌套功能也可以通过nestedScroll修饰符指定嵌套相关的功能，以后有时间再文档，我觉得它类似Flutter中的Sliver

        //多点解控:平移，缩放，旋转，这些功能主要通过transformable修饰符和TransformableState这个状态实现，操作时需要双指操作才可以
        var scale by remember { mutableStateOf(1f) }
        var rotation by remember { mutableStateOf(0f) }
        var offset by remember { mutableStateOf(Offset.Zero) }
        val state = rememberTransformableState {
            zoomChange: Float, panChange: Offset, rotationChange: Float ->
            scale *= zoomChange
            rotation += rotationChange
            offset += panChange
        }

        //可以使用graphicsLayer修饰符传入各个参值，也可以单独使用scale,rotation等修饰符
        Box(
            modifier = Modifier
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    rotationZ = rotation,
                    translationX = offset.x,
                    translationY = offset.y
                )
                .transformable(state = state)
                .background(color = Color.Red)
                .size(80.dp)
        )
    }

}