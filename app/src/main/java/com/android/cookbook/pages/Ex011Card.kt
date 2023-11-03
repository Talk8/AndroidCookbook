package com.android.cookbook.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.android.cookbook.navigation.LocalNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Ex011CardScreen(title:String) {
    var navController = LocalNavController.current

    Scaffold(
        topBar = { CommonCenterAppBar(title = title, navController = navController)}
    ) {
        paddingValues ->
        CardMainScreen(paddingValues = paddingValues)
    }
}

@Composable
fun CardMainScreen(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Card(//自带圆角，必须指定大小，否则使用wrap_content大小,默认灰色背景
            modifier = Modifier
                .background(color = Color.Blue)
                .size(width = 300.dp, height = 100.dp)
                .align(Alignment.CenterHorizontally),
            colors = CardDefaults.cardColors(
                containerColor = Color.Yellow,
                contentColor = Color.Red
            ),
            border = BorderStroke(width = 2.dp, color = Color.Red),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Text(//两处对齐都不起作用
                modifier = Modifier.padding(8.dp),
                text = "Custom card",
                textAlign = TextAlign.Center)
        }

        //背景比默认的淡一些
        ElevatedCard (
            modifier = Modifier.size(width = 300.dp, height = 100.dp))
        {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "Elevated card",
                    textAlign = TextAlign.Center)
        }
        Card(modifier = Modifier.size(width = 300.dp, height = 100.dp)) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Default card",
                textAlign = TextAlign.Center)
        }

        //自带一个边框
        OutlinedCard (
            modifier = Modifier.size(width = 300.dp, height = 100.dp) ){
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Outlined card",
                textAlign = TextAlign.Center)
        }
    }

}