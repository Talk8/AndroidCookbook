package com.android.cookbook.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.android.cookbook.navigation.LocalNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Ex010SwitchScreen(title : String) {
    val navController = LocalNavController.current

    Scaffold (
        topBar = { CommonCenterAppBar(title = title, navController = navController )} )
    { innerPadding ->
       SwitchMainScreen(paddingValues = innerPadding)
    }
}

@Composable
fun SwitchMainScreen(paddingValues: PaddingValues) {

    var checked1 by remember { mutableStateOf(false) }
    var checked2 by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "默认的样式")
        Switch(
            //缺点：只能修改整个Switch占用空间的大小，不能修改轨道(track)和图标(thumb)的大小，
            // 看源代码后，这是写死的,这个设计非常不友好
            modifier = Modifier.width(200.dp),
            checked = false, onCheckedChange = {})

        Text(text = "修改了默认的图标")
        Switch(checked = checked2,
            onCheckedChange = { checked2 = it },
            //如果选中后使用check图标：显示一个对号，否则显示其它图标
            thumbContent = if(checked2) { {
                    Icon(
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                        imageVector = Icons.Default.Check,
                        contentDescription = null
                    ) }
                }else{ {
                    Icon(
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null
                    ) }
                }
        )

        Text(text = "修改了选中后的图标")
        Switch(checked = checked1,
            onCheckedChange = { checked1 = it },
            //如果选中后使用check图标：显示一个对号，否则显示默认图标：这个小圆点
            thumbContent = if(checked1){  {
                Icon(
                    modifier = Modifier.size(SwitchDefaults.IconSize),
                    imageVector = Icons.Default.Check,
                    contentDescription = null )
            }
            }else{
                null
            }
        )

        Text(text = "修改了选中后的颜色")
        Switch(checked = checked1,
            onCheckedChange = { checked1 = it },
            colors = SwitchDefaults.colors(
                //switch最外层圆圈的颜色
                checkedBorderColor = Color.Red,
                //滑动轨道的颜色
                checkedTrackColor = Color.Yellow,
                //图标里层对号的颜色
                checkedIconColor = Color.Blue,
                //图标外层圆圈的颜色
                checkedThumbColor = Color.White,
                //这4个类型的颜色还对应unCheckedXXX，不设置时默认为灰色
            ),
            //如果选中后使用check图标：显示一个对号，否则显示默认图标：这个小圆点
            thumbContent = if(checked1){  {
                Icon(
                    modifier = Modifier.size(SwitchDefaults.IconSize),
                    imageVector = Icons.Default.Check,
                    contentDescription = null )
            }
            }else{
                null
            }
        )
    }
}