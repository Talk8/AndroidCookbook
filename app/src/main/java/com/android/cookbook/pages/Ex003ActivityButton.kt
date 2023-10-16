package com.android.cookbook.pages

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.cookbook.R
import com.android.cookbook.pages.ui.theme.CookbookTheme

class Ex003ActivityButton : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CookbookTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ExButton()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExButton() {
    //语法不同，不过功能相同，推荐第二种语法，by关键字会自动调用getter/setter语法操作变量
//    var textContent = remember { mutableStateOf("default") }
    var textContent by remember { mutableStateOf("default") }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {

        val interactionSource = remember {
            MutableInteractionSource()
        }

        //定义按钮不同状态下的颜色
        val pressState = interactionSource.collectIsPressedAsState()
        val borderColor = if (pressState.value) Color.Black else Color.White
        val backgroundColor = if (pressState.value) Color.White else Color.Black
        val textColor =  if (pressState.value) Color.Black else Color.White

        //基础button，圆角形状，无边框但是有背景色
        Button(
            border = BorderStroke(width = 2.dp, color = borderColor),
            colors = ButtonDefaults.buttonColors(
                containerColor = backgroundColor,
                contentColor = textColor
            ),
            //用来控制按钮不同状态下的颜色
            interactionSource = interactionSource,
            onClick = {
                Log.d("tag","bt is clicked")
            }) {
            //图标和文本可以并列存放
            Icon(Icons.Filled.Add, contentDescription = null)
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = "Add")
        }

        //带icon的button,不过icon和文字重叠了,无边框，无背景色
        IconButton(onClick = {  }) {
            Icon(Icons.Default.Add, contentDescription = null)
            Text(text = "Add")
        }

        //浅色背景的按钮，无边框有背景色
        ElevatedButton(onClick = {}) {
            Text(text = "Add")
        }

        //只有边框没有背景色的按钮
        OutlinedButton(onClick = {}) {
            Text(text = "Add")
        }

        //圆形按钮,无边框但是有背景色
        FilledIconButton(onClick = {}) {
//            Icon(Icons.Filled.Add, contentDescription = null)
            Text(text = "Add")
        }

        //无边框，无背景色的按钮
        TextButton(onClick = {}) {
            Text(text = "Add")
        }


        //在悬浮按钮中修改内容为随机数字，这里更新显示结果，注意通过remember状态控制
        Text(
            modifier = Modifier.padding(16.dp),
            text = "$textContent"
        )

        //悬浮按钮,带有阴影效果
        FloatingActionButton(onClick = {}) {
            //图标和文字不能并列排放
//            Icon(Icons.Default.Add, contentDescription = null )
            Text(text = "add")
        }

        Spacer(modifier = Modifier.size(16.dp))
        ExtendedFloatingActionButton(onClick = {
            var temp = (1..99).random()
            textContent = "it is $temp"
        }) {
            //图标和文字可以并列排放
            Icon(Icons.Default.Add, contentDescription = null )
            Text(text = "add")
        }

        //图标
        Icon(
            //控制icon的颜色
            tint = Color.Blue,
            imageVector = Icons.Default.FavoriteBorder,
            contentDescription = null)

        Spacer(modifier = Modifier.size(16.dp))
        //图片
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
            //图片填充方式,推荐Fit
            contentScale = ContentScale.FillBounds,
        )
        //图片剪裁为圆形，用来创建头像时使用
        Image(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape),
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
            //图片填充方式,推荐Fit
            contentScale = ContentScale.FillBounds,
        )
        //图片剪裁为圆角矩形
        Image(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(16.dp))
                .aspectRatio(16f / 9f),
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
            //图片填充方式,推荐Fit
            contentScale = ContentScale.FillBounds,
        )
    }
}