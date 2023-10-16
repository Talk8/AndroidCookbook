package com.android.cookbook.pages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.cookbook.pages.ui.theme.CookbookTheme

class Ex004ActivityProgress : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CookbookTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ExProgress()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun  ExProgress() {
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(all = 16.dp)
    ) {
        //进度条的大小通过modifier参数设置
        //通过color参数设置进度条的颜色
        CircularProgressIndicator(
            modifier = Modifier.size(80.dp),
            color = Color.Green
        )
        LinearProgressIndicator(
            modifier = Modifier.width(200.dp).height(30.dp),
            color = Color.Green,
            trackColor = Color.Yellow
        )

        //可组合方法是重载方法，通过progress参数设置进度值
        LinearProgressIndicator(progress = 0.3f)
        CircularProgressIndicator(progress = 0.8f)
    }
}