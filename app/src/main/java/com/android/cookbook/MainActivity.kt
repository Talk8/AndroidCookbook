package com.android.cookbook

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.cookbook.ui.theme.CookbookTheme
import kotlinx.coroutines.selects.select
import java.lang.reflect.Type
import kotlin.math.log

class MainActivity : ComponentActivity() {

   private val todoViewModel by viewModels<TodoViewModel>()

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
//                    TodoActivityScreen()
                }
            }
        }
    }


    @Composable
    fun TodoActivityScreen() {
        /*
        val items = listOf(
            TodoItem("Learn compose",TodoIcon.Event),
            TodoItem("Take the codelab",TodoIcon.Square),
            TodoItem("make the lab",TodoIcon.Trash),
            TodoItem("Take the codelab",TodoIcon.Done),
            TodoItem("This is the time",TodoIcon.Privacy),

        )
        TodoScreen(items)
         */
//        val  items: List<TodoItem> by todoViewModel.todoItems.observe(todoViewModel.todoItems,
//            Observer<List<TodoViewModel>> { todoViewModel.todoItems })
//        TodoScreen(items = todoViewModel,
//            onAddItem = todoViewModel.addItem(it),
//            onRemoveItem = todoViewModel.removeItem(it) )
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

@OptIn(ExperimentalUnitApi::class)
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
                    modifier = Modifier
                        .weight(1f)
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

        //WR,自动创建row,WC自动创建column
        //comp自动创建组合函数

}



@Composable
fun TodoScreen(
    items: List<TodoItem>,
    onAddItem:(TodoItem) -> Unit,
    onRemoveItem: (TodoItem) -> Unit,
) {

    val count = items.size
    Column() {

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(color = Color.Green),
            contentPadding = PaddingValues(top = 8.dp),
        ) {
            items(count = count) {
                TodoRow(
                    todo = items[it],
                    onItemClicked = {onRemoveItem(it)}
                )
            }
        }

        Button(
            onClick = {onAddItem(generateRandomTodoItem())},
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Add item")
        }
    }
}

@Composable
fun TodoRow(
    todo:TodoItem,
    onItemClicked: (TodoItem)->Unit,
//    modifier: Modifier = Modifier,

) {

    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable { onItemClicked(todo) }
            .background(color = Color.Yellow),
        //行内元素的对齐方式,不起作用
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = todo.name)

        Icon(
            imageVector = todo.icon.imageVector,
            contentDescription = null
//            contentDescription = Text(text = todo.icon.contentDescription)
        )
    }
}

enum class TodoIcon(
    val imageVector: ImageVector,
    val contentDescription:String
) {
    Square(Icons.Default.AccountBox,"Expand"),
    Done(Icons.Default.Done,"Done"),
    Event(Icons.Default.MoreVert,"Event"),
    Privacy(Icons.Default.Share,"Privacy"),
    Trash(Icons.Default.Star,"Trash");

    companion object {
        val Default = Square
    }
}

class TodoItem(
    name: String,
    icon: TodoIcon,
) {
    var name:String
    var icon:TodoIcon

    init {
        this.name = name
        this.icon = icon
    }
}

fun generateRandomTodoItem() :TodoItem {
    val message = listOf(
        "Number 1,it is ok",
        "Number 2,it is not ok",
        "Number 3,it is not ok",
        "Number 4,it is ok",
        "Number 5,it is not ok",
    ).random()

    val icon = TodoIcon.values().random()

    return TodoItem(message,icon)
}

class TodoViewModel :ViewModel() {
    private var _todoItems = MutableLiveData(listOf<TodoItem>())
    val todoItems: LiveData<List<TodoItem>> = _todoItems
    fun addItem(item: TodoItem) {
        _todoItems.value = _todoItems.value!! + listOf(item)
    }

    fun  removeItem(item: TodoItem) {
        _todoItems.value = _todoItems.value!!.toMutableList().also {
            it.remove(item)
        }
    }
}