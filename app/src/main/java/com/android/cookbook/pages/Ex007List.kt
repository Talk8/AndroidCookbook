package com.android.cookbook.pages

import android.widget.GridView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.magnifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.android.cookbook.navigation.LocalNavController


val strList = listOf("abc","bbc","cba","acb")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Ex007ListScreen(title:String) {
    val  navController = LocalNavController.current
    Scaffold(
        topBar = { CommonCenterAppBar(title, navController = navController) },
    ) { innerPadding ->
        ListMainScreen(paddingValues = innerPadding)
    }
}

@Composable
fun ListMainScreen(paddingValues: PaddingValues) {

    LazyColumn(
        //需要通过scaffold传递的参数来设置顶部的边距，不然topBar会和mainScreen中的内容重叠
        modifier = Modifier.padding( top = paddingValues.calculateTopPadding()),
        contentPadding = PaddingValues(horizontal = 16.dp),
        //指定list内容之间的间隔
        verticalArrangement = Arrangement.spacedBy(8.dp),
        //控制列表是否能够滚动，默认值为true，表示可以滚动
        userScrollEnabled = false
    ) {
        //不能直接使用可组合函数赋值,需要借助item函数
//        Text(text = "this is a list")
        item {
            Text(text = "this is a list")
        }


        //指定list的数量,并且添加分隔线
        items( count = 9, itemContent = { index ->
            Text(text = "This is item ${index+1}")
            Divider(color = Color(0.1f,0.8f,0.9f,1.0f))
        })

        //通过items直接使用list
        items(strList){ item ->
            Text(text = item )
        }
    }

}

