package com.android.cookbook.pages

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


//使用CenterAlignedTopAppBar组合函数，可以让title居中，其它的用法与上一个函数完全相同
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonCenterAppBar(title:String,navController: NavController) {


    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.Blue,
            navigationIconContentColor =  Color.White,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White,
        ),
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(//返回上一级菜单
                onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }
        },
        //没有actionBar
        actions = { }
    )
}