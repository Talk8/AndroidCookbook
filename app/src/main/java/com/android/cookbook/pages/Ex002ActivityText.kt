package com.android.cookbook.pages

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.cookbook.R
import com.android.cookbook.pages.ui.theme.CookbookTheme

class Ex002ActivityText : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CookbookTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
                    color = Color.White,
                ) {
                    KindsOfText()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun KindsOfText() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {


        //本来想在外层嵌套一个容器来让text居中，但是仍然没有效果
        Box ( contentAlignment = Alignment.Center) {
            Text(
                modifier = Modifier
                    .width(220.dp)
                    .height(100.dp)
                    .padding(8.dp) //padding位于border不同地方作用不同，这里是外边距，下面是内边距
                    .background(color = Color.Yellow)
                    .border(2.dp, color = Color.Red, shape = RectangleShape)
                    .padding(16.dp),
                text = stringResource(id = R.string.str_hello),
                //这个颜色的优先级高于style
//                color = Color.Blue,
                textAlign = TextAlign.Center,
                fontSize = 22.sp,
                maxLines = 2,
                //字符超过最大行后仍然无法显示时显示为三个点
                overflow = TextOverflow.Ellipsis,
                //使用的是ui.theme目录下的主题:type.kt中的style
                style = MaterialTheme.typography.labelLarge
            )
        }

        //可以选择的文本，主要是嵌套了一个SelectionContainer函数，icon嵌套后不能被选择
        //如何操作复制事件？
        SelectionContainer() {
            Text(text = "click me and then selected")
//            Row {
//                Icon(imageVector = Icons.Default.Star, contentDescription = null)
//                Icon(imageVector = Icons.Default.Star, contentDescription = null)
//                Icon(imageVector = Icons.Default.Star, contentDescription = null)
//            }
        }

        //富文本
        Text(text = buildAnnotatedString {
            withStyle( style = SpanStyle(color = Color.Red, fontSize = 12.sp)) {
                append("part1")
            }

            withStyle( style = SpanStyle(color = Color.Green, fontSize = 22.sp)) {
                append("part2")
            }

            withStyle( style = SpanStyle(color = Color.Blue)) {
                append("part3")
            }
        })


        //带有点击功能的文本
        val annotationText = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 24.sp)) {
                append("url:")
            }

            withStyle(
                style = SpanStyle(color = Color.Red, textDecoration = TextDecoration.Underline)
            ) {
                pushStringAnnotation(
                    tag = "URL",
                    annotation = "www.baidu.com",
                )
                append("address")
                pop()
            }
        }

        ClickableText(text = annotationText,
            onClick = {
                    offset -> annotationText.getStringAnnotations(
                tag = "URL",
                start = offset,
                end = offset
            ).toString()
                Log.d("tag","text is clicked : ${annotationText.toString()}")
            }
        )

        InputText()
    }
}


@Preview
@Composable
@ExperimentalMaterial3Api
fun InputText() {
     var text by remember { mutableStateOf("") }

    Column {
        Text(text = "Echo text")
        Text(text = text)
        Spacer(modifier = Modifier.size(16.dp))
        //带下划线的输入框,不过我在外层加了一层边框
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(
                    width = 2.dp,
                    color = Color.Green,
                    shape = CircleShape.copy(all = CornerSize(16.dp))
                )
                .clip(shape = CircleShape.copy(all = CornerSize(16.dp)))
                .background(Color.Yellow),
            value = text,
            //默认为false，设置为true时,label和supportText显示为红色
//            isError = true,
            //设置输入类型为密码
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            //获取输入的内容
            onValueChange = { text = it },
            placeholder = { Text("pls holder") },
            //开始和结束时的图标
            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null)},
            trailingIcon = { Icon(imageVector = Icons.Filled.Notifications, contentDescription = null)},
            //在输入框下方显示的文字
            supportingText = { Text("supporting text ") },
            //在图标后面显示的文字
            label = { Text("Name") },
            //设置后不起作用
            shape = TextFieldDefaults.outlinedShape,
            colors = TextFieldDefaults.outlinedTextFieldColors(),
        )

        Spacer(modifier = Modifier.size(16.dp))
        //不带下划线的输入框,不过周围有一圈边框
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = text,
            onValueChange = {},
            label = { Text("Name") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                //修改输入框背景色
                containerColor = Color.Yellow,
                //修改边框颜色
                unfocusedBorderColor = Color.Green,
                focusedBorderColor = Color.Blue,
            ),
        )
    }


}