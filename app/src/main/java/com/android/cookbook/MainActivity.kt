package com.android.cookbook

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.cookbook.ui.theme.CookbookTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CookbookTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainFramework()
                }
            }
        }
    }
}



@Preview
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainFramework () {
    Scaffold(
        topBar = {
                 TopAppBar(
                     modifier = Modifier.background(color = MaterialTheme.colorScheme.primary),
                     //这样仍然无法居中显示
                     title = {
                         Box(contentAlignment = Alignment.Center) {
                             Text(text = "title")
                         }
                     },
                     navigationIcon = { Icon(imageVector = Icons.Default.Menu, contentDescription = null)},
                     actions = {
                         IconButton(
                             onClick = { Log.d("tag","bt clicked") }) {
                             Icon(imageVector = Icons.Default.Share, contentDescription = null)
                         }
                     }
                 )
        },
        bottomBar = {},
    ) {contentPadding ->
        HomePage(padding = contentPadding)
    }
}

@Composable
fun HomePage(padding:PaddingValues) {
    //用来存放目录，这些目录会显示到按钮上
     val contentList = listOf(
        "ex001: Layout and Pages",
        "ex001: Layout and Pages",
        "ex001: Layout and Pages",
        "ex001: Layout and Pages",
    )
    
    Column(
        modifier = Modifier
            .padding(padding)
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        for (item in contentList) {
            //如何文字和图标对齐:修改Row的verticalAlignment参数值
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)) {
                Icon(
                    modifier = Modifier.weight(1f)
                        .background(color = Color.White),//这里修改的是图标背景颜色，tint可以修改图标颜色
                    tint = Color.Blue,
                    imageVector = Icons.Default.Check, contentDescription =null )
                TextButton(
                    modifier = Modifier.weight(9f),
                    onClick = {
                    //按钮事件就用来跳转页面
                    Log.d("a", "ex $item")
                }) {
                    Text(text = item)
                }
            }
        }
    }
}