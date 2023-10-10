package com.android.cookbook.pages

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.cookbook.pages.ui.theme.CookbookTheme

//创建页面时使用了new -> compose -> emptyActivity，因此生成了此模板，以及当前目录下的主题文件夹
class Ex001ActivityLayoutAndPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CookbookTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LayoutPage()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LayoutPage() {
    val  list = listOf("1-One","2-Tow","3-Three")

    Column(
        modifier = Modifier
            .padding(top = 60.dp)
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        //列中元素的对齐方式，需要设置fillMaxHeight，不然只有内容大小，无法调节元素对齐
        verticalArrangement = Arrangement.Center
    ) {
        for (item in list) {
            //控制每个list选项的边距，长宽度，边框和背景颜色
            Surface(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth() //相当于match_parent,默认是wrap_content
                    .height(48.dp)
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .border(width = 3.dp, color = Color.Yellow,)
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    //行中元素的对齐方式，需要设置fillMaxWidth，不然只有内容大小，无法调节元素对齐
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(start = 24.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                        ){
                    Text(text = item)
                    Button(onClick = { Log.d("ex001","button is clicked")}) {
                        Text(text = "Button")
                    }
                }
            }
        }

        //这个组件和column并列，weight针对它们两个
        Text(
            //权重值设置,会把当前面分成两部分，其它部分用wrap_content填充，本内容占用剩下的所有空间
            modifier = Modifier.weight(1f),
            text = "Bottom Line")
    }
}

/* 以下内容打开注释后可以直接演示
    Column {
        Text(text = "row 1")
        Text(text = "row 2")
        Text(text = "row 3")
        Spacer(modifier = Modifier.height(8.dp))
        GreenBox()
        Spacer(modifier = Modifier.height(8.dp))
        Greeting(name = "column")
        Image(painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "launcher image",
            modifier = Modifier
                .size(width = 200.dp, height = 200.dp)
                .clip(CircleShape)
                .border(width = 2.dp, color = Color.Green, shape = CircleShape))
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { }) {

        }

        var isExpanded = false
        val surfaceColor by animateColorAsState(targetValue = Color.Green ) {
            if(isExpanded)
                Color.Red
            else
                Color.Yellow

        }
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Surface(shape = MaterialTheme.shapes.medium,
                color = surfaceColor ,
            shadowElevation = 8.dp) {
                Text(text = "one")
            }

            Text(
                text = "one",
                maxLines = 2,
                color = Color.Green,
            )
            Text(text = "one")
            Text(text = "one")

        }
        LazyColumn {
            val listItems = listOf<String>("a","b","c","d","e")
            items(listItems) { listItem -> Text(text = listItem)}
        }

    }
     */


/* 以下内容打开注释后可以直接演示
@Preview
@Composable
fun ExList() {
    val scrollState = rememberScrollState()

    Column(Modifier.verticalScroll(scrollState)) {
        repeat(times = 20,) {
            Text(text = "This is item: $it", style = MaterialTheme.typography.titleSmall)
        }
    }
}

@Composable
fun ExLazyList() {
    val scrollState = rememberLazyListState()
    LazyColumn(state = scrollState) {
        items(count = 30) {
            Text(text = "Number: $it", style = MaterialTheme.typography.titleLarge)
        }
    }


}
 */