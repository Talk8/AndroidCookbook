package com.android.cookbook.pages

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.DialogProperties
import com.android.cookbook.navigation.LocalNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Ex006DialogScreen(title: String) {

    //获取当前的NavController,主要用来返回上一级菜单
    val navController = LocalNavController.current

    Scaffold(
        topBar = { CommonCenterAppBar(title, navController = navController) },
    ) { innerPadding ->
       DialogScreenMain(innerPadding)
    }
}

    @Composable
    fun ExDialog(
        onDismissRequest:  () ->Unit,
        onConfirmation:() -> Unit,
        title: String,
        message: String,
        icon: ImageVector
    ) {

        AlertDialog(
            icon = { Icon(imageVector = icon, contentDescription = null)},
            title = { Text(text = title)},
            text = { Text(text = message)},
            //控制dialog中文字，标题、图标的颜色,不过没有包含底部yes/no两个文字的颜色
            containerColor = Color.LightGray,
            textContentColor = Color.Black,
            titleContentColor = Color.Red,
            iconContentColor = Color.Yellow,
            //这两个值用来控制点击dialog区域外时,dialog是否会消失，默认值为true
            properties = DialogProperties(
                dismissOnClickOutside = false,
                dismissOnBackPress = false,
            ),
            onDismissRequest = {onConfirmation()},
            confirmButton = {
                TextButton(onClick = {onConfirmation()}) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = {onDismissRequest()}) {
                    Text(text = "No",color = Color.Black )
                }
            }
        )
    }

    @Composable
    fun ShowDialog(showDialog:MutableState<Boolean>){
        when {
            showDialog.value -> {
                ExDialog(
                    onDismissRequest = {  showDialog.value = false},
                    onConfirmation = { showDialog.value = false },
                    title = "Title of AlertDialog",
                    message = "Message of AlertDialog",
                    icon = Icons.Default.Warning
                )
            }
        }
    }

    @Composable
    fun DialogScreenMain(innerPadding:PaddingValues) {
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            val showDialog = remember { mutableStateOf(false) }

            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround){
                //点击按钮显示图标
                ElevatedButton(onClick = {
                    showDialog.value = true
                }) {
                    Text(text = "Show Dialog")
                }

                ShowDialog(showDialog)

                //点击按钮显示Toast,尽量使用SnackBar来替代Toast
                val context = LocalContext.current
                ElevatedButton(onClick = {
                    Toast.makeText(context,"Hello Toast",Toast.LENGTH_SHORT).show()
                }) {
                    Text(text = "Show Toast")
                }

                //TextButton中的text无法居中，给Text添加点击事件来替代
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
//                        .background(color = Color.Yellow)
                        .clickable {
                            Toast
                                .makeText(context, "Text is Clicked", Toast.LENGTH_SHORT)
                                .show()
                        },
                    text = "click me ",
                    textAlign = TextAlign.Start
                )
            }
        }
    }