package com.android.cookbook

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.android.cookbook.pages.ExButton
import com.android.cookbook.pages.ExProgress
import com.android.cookbook.pages.ExScaffold
import com.android.cookbook.pages.KindsOfText
import com.android.cookbook.pages.LayoutPage
import com.android.cookbook.pages.ScreenData
import com.android.cookbook.ui.theme.CookbookTheme

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
                    AppNavigation()
//                    TodoActivityScreen()
//                    MainFramework()
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



@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainFramework (navController: NavController) {
    Scaffold(
        topBar = {
                 TopAppBar(
                     //这样仍然无法居中显示
                     title = {
                         Box(contentAlignment = Alignment.Center) {
                             Text(text = " Example of Jetpack Compose")
                         }
                     },
                     navigationIcon = { Icon(imageVector = Icons.Default.Menu, contentDescription = null)},
                     actions = {
                         IconButton(
                             onClick = { Log.d("tag","bt clicked") }) {
                             Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                         }
                     }
                 )
        },
        bottomBar = {},
    ) {contentPadding ->
        HomePage(padding = contentPadding, navController = navController)

        //如果在当前页获取navController然后再添加navHost，那么跳转到其它页面时scaffold的topBar会一直存在
//        NavHost(
//            navController = navController,
//            startDestination = "home" ) {

            //程序主页
//            composable("home") {
//                HomePage(padding = contentPadding, navController = navController)
//            }
//        composable(ScreenData.screeList[1].route) {
//            ExButton()
//        }
//        composable(ScreenData.screeList[2].route) {
//            ScreenData.screeList[2].composableFun
//        }
//        composable(ScreenData.screeList[3].route) {
//            ScreenData.screeList[3].composableFun
//        }
//        composable("button") {
//            ExButton()
//        }
//        }
    }
}

@Composable
fun HomePage(padding:PaddingValues,navController: NavController) {

    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        ScreenData.screeList.forEachIndexed { index, screen ->
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier .padding(start = 16.dp, end = 16.dp)) {
                Icon(
                    modifier = Modifier
                        .weight(1f)
                        .background(color = Color.White),//这里修改的是图标背景颜色，tint可以修改图标颜色
                    tint = Color.Blue,
                    imageVector = Icons.Default.Check , contentDescription = null)

                //TextButton的文字无法居中
                TextButton(
                    modifier = Modifier
                        .weight(9f),
//                        .background(color = Color.Cyan),
                    onClick = {
                       navController.navigate(screen.route)
//                        navController.navigate("button")
                    }) {
                    Text(text = (index+1).toString()+": "+screen.description, style = TextStyle(textAlign = TextAlign.Start))
                }
            }
        }
        //尝试三种方法都无法让TextButton中的文本向左对齐
        //通过Text的属性，无法让Text向左对齐
        //在Text外嵌套一层行或者列也无法让Text向左对齐
        //修改modify也无法让Text向左对齐
//        TextButton(
//            modifier = Modifier
//                .background(color = Color.Yellow)
//                .align(Alignment.Start),
//            onClick = {}) {
//            Row(
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text(
//                    modifier = Modifier.background(color = Color.Green),
//                    text = "test", style = TextStyle(textAlign = TextAlign.Start),
//                    textAlign = TextAlign.Start
//                )
//                Text(
//                    modifier = Modifier.background(color = Color.Red),
//                    text = "     ", style = TextStyle(textAlign = TextAlign.Start),
//                    textAlign = TextAlign.Start
//                )
//                Text(
//                    modifier = Modifier.background(color = Color.Blue),
//                    text = "     ", style = TextStyle(textAlign = TextAlign.Start),
//                    textAlign = TextAlign.Start
//                )
//            }
//        }
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

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home" ) {

        //程序主页
        composable("home") {
            MainFramework(navController)
        }

        //composable函数不起作用时手动添加导航，否则使用下面的循环自动添加导航图
        /*
        composable(ScreenData.screeList[0].route) {
            LayoutPage()
        }

        composable(ScreenData.screeList[1].route) {
            KindsOfText()
        }
        composable(ScreenData.screeList[2].route) {
//            ScreenData.screeList[2].composableFun
            ExButton()
        }
        composable(ScreenData.screeList[3].route) {
//            ScreenData.screeList[3].composableFun
            ExProgress()
        }
        composable(ScreenData.screeList[4].route) {
            ExScaffold()
        }


         */
        //这种赋值形式正确，但是无法导航到正确页面，原因是composable函数传递来后没有效果，进入空白页面，换成注释掉的代码就可以
        //在composable函数后面加上括号就可以了
        ScreenData.screeList.forEach { screen ->
            composable(screen.route) {
//                ExButton()
//                Text(text = "hello")
                //原来使用screen.composableFun,没有效果，加上括号就可以了
                screen.composableFun()
            }
        }

        //在导航中通过参数传递数据，dataParam是个占位符,
        composable("exButton/{dataParam}",
            //把被传递的数据存放在参数中，使用了navArgument()方法，这里指定了默认值，调用navigate方法传入真实数据
            arguments = listOf(
                navArgument(name = "dataParam") {
                    type = NavType.StringType
                    defaultValue = "it is a default data"
                    nullable = true
                }
            )
        ) {
            //从导航参数中获取数据，这里的it是lambda中的参数，它的类型是NavBackStackEntry类型
            val data = it.arguments?.getString("dataParam")?:"no data"
            println(data)
            ExButton()
        }
    }
}