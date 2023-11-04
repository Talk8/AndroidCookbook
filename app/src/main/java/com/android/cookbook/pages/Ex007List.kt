package com.android.cookbook.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.android.cookbook.R
import com.android.cookbook.navigation.LocalNavController


val strList = listOf("abc","bbc","cba","acb")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Ex007ListScreen(title:String) {
    val  navController = LocalNavController.current
    Scaffold(
        topBar = { CommonCenterAppBar(title, navController = navController) },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding( top = innerPadding.calculateTopPadding()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ListMainScreen()
            Grids()
            GridsAnother()
            GridWithCard()
        }
    }
}

@Composable
fun ListMainScreen() {

    LazyColumn(
        //需要通过scaffold传递的参数来设置顶部的边距，不然topBar会和mainScreen中的内容重叠
//        modifier = Modifier.padding( top = paddingValues.calculateTopPadding()),
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
        items( count = 3, itemContent = { index ->
            Text(text = "This is item ${index+1}")
            Divider(color = Color(0.1f,0.8f,0.9f,1.0f))
        })

        //通过items直接使用list
        items(strList){ item ->
            Text(text = item )
        }
    }

}

@Composable
fun Grids() {
    LazyVerticalGrid(
        //在保证内容间隔为100dp的前提下，自动排列行和列中的内容，数量不确定
        columns = GridCells.Adaptive(100.dp)) {
        items(count = 9, itemContent = {
            Image(painter = painterResource(id = R.drawable.ic_logo), contentDescription = null) } )
    }


}

@Composable
fun GridsAnother() {
     LazyVerticalGrid(
        //在保证内容数量的前提下，自动排列行和列中的内容之间的间隔，间隔不确定
         //它有一个共同的缺点，无法调整行距
        columns = GridCells.Fixed(4)) {
        items(count = 9, itemContent = {
            Image(painter = painterResource(id = R.drawable.ic_logo), contentDescription = null)
        } )
    }
}

@Composable
fun GridWithCard() {
    var index: Int
    LazyVerticalGrid(columns = GridCells.Fixed(3)) {
        items(count = 9,
//            span = { GridItemSpan(it) },//通过span创建不规则的Grid，
            itemContent = {
            index = it+1
            //通过各种计算，card不适合放在Grid中，因为它的大小受Grid影响，会自动调节
            Card ( modifier = Modifier.size(40.dp),
            ){  //通过Box让Card中的内容居中显示
                Box(modifier = Modifier.align(Alignment.CenterHorizontally)){
                    Text(text = "$index")
                }
            }
        })
    }
}

