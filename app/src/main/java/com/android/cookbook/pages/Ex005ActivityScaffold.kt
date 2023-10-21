package com.android.cookbook.pages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.node.modifierElementOf
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.android.cookbook.R
import com.android.cookbook.pages.ui.theme.CookbookTheme
import com.android.cookbook.pages.ui.theme.CusColor

class Ex005ActivityScaffold : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CookbookTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = CusColor),
                    //这个color不起作用，去掉外层的theme也不行,在下一层的scaffold中设置color也起作用。
                    //经过实践总结出：在最上层的组合函数中修改背景色才有效果，同时要修改最上层函数的size为屏幕尺寸
                    color = MaterialTheme.colorScheme.error
                ) {
                    ExScaffold()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ExScaffold(
) {

    //这个NavController主要用来切换底部的导航，只能在这里获取，不能在底部导航的方法中获取，不会导航栏会出现在屏幕上方
    val  naviController = rememberNavController()

    Scaffold(
        //这个color也不起作用
        modifier = Modifier.background(color = MaterialTheme.colorScheme.primary),
//        topBar = { customAppBar() },
        topBar = { customCenterAppBar()},

//        bottomBar = { customBottomBar() },
//        bottomBar = { CustomBottomNavigationBar(naviController) },
        bottomBar = { BottomNaviBarTemplate(navController = naviController)},

        //控制FAB的位置,只有两种
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = { CustomFAB() }

    ) {innerPadding->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(color = CusColor)
        ) {
            Text(text = "hello")
        }

        //NavHost容器中包含所有的导航页面，只能在这里获取，不能在底部导航的方法中获取，不会导航栏会出现在屏幕上方
        //这个是自定义的页面配合自定义的底部导航栏使用:CustomBottomNavigationBar
        /*
        NavHost(
            navController = naviController,
            startDestination = "PersonPage") {

            composable("PersonPage") {
                PersonPage()
            }

            composable("HomePage") {
                HomePage()
            }

            composable("SettingPage") {
                SettingPage()
            }
        }
         */
        //这个navHost容器是配合BottomNaviBarTemplate使用的，可以当作代码模板
        NavHost(
            navController = naviController,
            startDestination = BottomNaviScreen.HomeScreen.route,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(BottomNaviScreen.PersonScreen.route) { PersonScreen(naviController)}
            composable(BottomNaviScreen.HomeScreen.route) { HomeScreen(naviController) }
            composable(BottomNaviScreen.SettingScreen.route) { SettingScreen(naviController) }
        }
    }
}

//使用TopAppBar组合函数，除了无法让title居中外，其它的都可以正常使用
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun customAppBar() {
    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.Blue,
            navigationIconContentColor =  Color.White,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White,
        ),
        title = {
            //不论是Box还是Row都无法让title居中源代码中使用layout组合函数实现
            Row(
                //加上背景后可以看到它的大小，这也是无法居中对齐的原因
                modifier = Modifier.background(color= CusColor),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ){
                Text(text = "Title")
            }
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null
            )
        },
        actions = {
            IconButton(
                onClick = { }){
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null
                )
            }
        }
    )
}

//使用CenterAlignedTopAppBar组合函数，可以让title居中，其它的用法与上一个函数完全相同
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun customCenterAppBar() {
    var actionClicked by remember { mutableStateOf(false) }

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.Blue,
            navigationIconContentColor =  Color.White,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White,
        ),
        title = { Text(text = "Title") },
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null
            )
        },
        //默认显示图标，点击时修改状态，然后显示popupmenu,同时隐藏图标
        actions = {
            if(actionClicked)
                ShowPopupMenu()
            else
                IconButton(
                    onClick = { actionClicked = true }){
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null
                    )
                }
        }
    )
}

//BottomAppBar,需要自己组合其它函数来实现，我组合的是Row布局和IconButton
@Composable
fun customBottomBar() {
    BottomAppBar(
        //控制高度，背景颜色和内容颜色
        modifier = Modifier.height(64.dp) ,
        contentColor = Color.White,
        containerColor = Color.Cyan,
        tonalElevation = BottomAppBarDefaults.ContainerElevation,
        contentPadding = BottomAppBarDefaults.ContentPadding,
    ) {
        //bottomBar中的内容
        Row (
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ){
            IconButton(
                onClick = {  },
            ) {
                Icon(imageVector = Icons.Default.Person, contentDescription = null )
            }

            IconButton(
                onClick = {  },
            ) {
                Icon(imageVector = Icons.Default.Home, contentDescription = null )
            }

            IconButton(
                onClick = {  },
            ) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = null )
            }
        }
    }
}

//BottomNavigationBar,使用库中的NavigationBar和NavigationBarItem函数
data class NavigationItem(
    val text:String,
    val icon:ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomNavigationBar(naviController: NavController) {
    //定义一个状态值，在切换bar时使用
    var selectedItem by remember { mutableStateOf(0) }

    val items = listOf<NavigationItem>(
        NavigationItem("Person",Icons.Default.Person),
        NavigationItem("Home",Icons.Default.Home),
        NavigationItem("Setting",Icons.Default.Settings),
    )

    //不能在这里获取naviController和设置NavHost,否则导航栏出现在屏幕最上方
//    val  naviController = rememberNavController()
//    NavHost(
//        navController = naviController,
//        startDestination = "PersonPage") {
//
////        composable("Scaffold") {
////            ExScaffold()
////        }
//
//        composable("PersonPage") {
//           PersonPage()
//        }
//
//        composable("HomePage") {
//            HomePage()
//        }
//
//        composable("SettingPage") {
//            SettingPage()
//        }
//    }


    NavigationBar(
        containerColor = Color.Blue,
        contentColor = Color.Green, //这个颜色不起作用
    ) {
        //获取当前的navigation
        val navBackStackEntry by naviController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEachIndexed { index, item ->
            NavigationBarItem(
                //通过状态值来确定当前bar是否被选中
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index

                    when (index) {
                        0 -> naviController.navigate("PersonPage") {
                //                    naviController.navigate(currentDestination.toString()) {
                            //点击Item时清空栈内到NavOptionBuilder.pupUpTo id之间所有的item
                            //相当于返回到导航堆栈的起始页面并且把当前页面到起始页面之间的其它导航信息移出堆栈
                //                        popUpTo("Person") //和下面的代码效果相同
                            popUpTo(naviController.graph.findStartDestination().id) {
                                saveState = true
                //                            inclusive = true
                            }

                            //避免多次点击Item时产生多个实例
                            launchSingleTop = true
                            //再次点击Item时恢复状态
                            restoreState = true
                        }
                        1 -> naviController.navigate("HomePage") {
                            //点击Item时清空栈内到NavOptionBuilder.pupUpTo id之间所有的item
                            popUpTo(naviController.graph.findStartDestination().id) {
                                saveState = true
                            }

                            //避免多次点击Item时产生多个实例
                            launchSingleTop = true
                            //再次点击Item时恢复状态
                            restoreState = true
                        }
                        else -> naviController.navigate("SettingPage") {
                            //点击Item时清空栈内到NavOptionBuilder.pupUpTo id之间所有的item
                            popUpTo(naviController.graph.findStartDestination().id) {
                                saveState = true
                            }

                            //避免多次点击Item时产生多个实例
                            launchSingleTop = true
                            //再次点击Item时恢复状态
                            restoreState = true
                        }
                    }
                },
                //设置bar上的图标
                icon = {
                    //在bar的图标上创建小红点,不过不能调整小红点的位置
                    BadgedBox(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        badge = {
                            //依据条件显示小红点：选中时才显示小红点
                            if(selectedItem == index)
                            Badge(
                                //调整小红点的大小
                                modifier = Modifier.size(16.dp),
                                containerColor = Color.Red,
                                contentColor = Color.White,
                                content = {Text(text = "6")},
                            )else
                                null
                        },
                    ) {
                        Icon(imageVector = item.icon, contentDescription = null )
                    }
                },
                //设置bar的各种颜色
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Cyan,
                    selectedTextColor = Color.Cyan,
                    //这个颜色最好和NavigationBar的containerColor颜色保持一致，不然会在Icon外层显示一个背景颜色
                    indicatorColor = Color.Blue,
                    unselectedIconColor = Color.Yellow,
                    unselectedTextColor = Color.Yellow,
                ),
                //设置bar上的文字
                label = { Text(text = item.text) }
            )
        }

        //不使用数组，一个一个地添加barItem,缺点是不能处理点击事件，点击item后不能切换item.
        /*
       NavigationBarItem(
           selected = true,
           onClick = {  },
           icon = {Icon(imageVector = Icons.Default.Person, contentDescription = null )},
           colors = NavigationBarItemDefaults.colors(
               selectedTextColor = Color.Cyan,
               indicatorColor = Color.White,
               unselectedIconColor = Color.Yellow,
               unselectedTextColor = Color.Yellow,
           ),
           label = {Text(text = "Person")},
       );
       NavigationBarItem(
           selected = false,
           onClick = {  },
           icon = {Icon(imageVector = Icons.Default.Home, contentDescription = null )},
           label = {Text(text = "Home")},
           colors = NavigationBarItemDefaults.colors(
               selectedTextColor = Color.Cyan,
               indicatorColor = Color.White,
               unselectedIconColor = Color.Yellow,
               unselectedTextColor = Color.Yellow,
           ),
        );
        NavigationBarItem(
           selected = true,
           onClick = {  },
           icon = {Icon(imageVector = Icons.Default.Settings, contentDescription = null )},
           label = {Text(text = "Setting")},
        )
         */
    }
}

//自定义的FloatingActionButton
@Composable
fun CustomFAB() {
    ExtendedFloatingActionButton(
        text = { Text(text = "Add") },
        icon = { Icon(imageVector = Icons.Default.Add,
        contentDescription = null) },
        onClick = { })
}

//自定义的popupMenu，通过可以组合函数DropdownMenu实现，同时实现了点击功能，不过没有给item添加具体的功能
@Composable
fun ShowPopupMenu() {
    //是否显示popupMenu,默认显示
    var show by remember { mutableStateOf(true) }

    //菜单中显示的内容：包含图标和文字
    val items = listOf<NavigationItem>(
        NavigationItem("One",Icons.Default.Done),
        NavigationItem("Two",Icons.Default.Done),
        NavigationItem("Three",Icons.Default.Done),
    )

    if (show)
        DropdownMenu(
            //是否显示菜单
            expanded = show,
            //控制菜单的弹出位置，向左和向下进行偏移
            offset = DpOffset(x = 20.dp, y = 30.dp),
            //点击菜单外任意位置时，菜单消失
            onDismissRequest = { show = false }) {
            Column() {
                items.forEachIndexed { index, item ->
                    //这个菜单项目比较好，封装了图标、文本和点击事件
                        DropdownMenuItem(
                            leadingIcon = { Icon(imageVector = item.icon, contentDescription = null)},
                            text = { Text(text = item.text) },
                            //点击任意菜单项目时，菜单消失
                            onClick = { show = false})
                }
            }
        }
    else
        //action第二次点击弹出菜单，第一次点击在scaffold中控制
        IconButton(onClick = { show = true}) {
            Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
        }
}

@Composable
fun PersonPage() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "This is page of person")
    }
}

@Composable
fun HomePage() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "This is page of Home")
    }
}

@Composable
fun SettingPage() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "This is page of Setting")
    }
}

//参考官方文档编写，compose中习惯把页面叫screen,而在Flutter中习惯叫page,因此在代码中screen和page的含义相同
//我在CustomBottomNavigationBar创建导航栏在切换标签时有屏幕闪烁，这个模板则没有，差别就在这个sealed class上
sealed class BottomNaviScreen(val route:String, val resourceId:Int,val icon:ImageVector) {
    object PersonScreen : BottomNaviScreen("person", R.string.navi_person,Icons.Default.Person)
    object HomeScreen : BottomNaviScreen("home",R.string.navi_home,Icons.Default.Home)
    object SettingScreen: BottomNaviScreen("setting",R.string.navi_setting,Icons.Default.Settings)
}

//创建底部导航栏的模板代码
@Composable
fun BottomNaviBarTemplate(navController: NavController) {
    val screens = listOf(
        BottomNaviScreen.PersonScreen,
        BottomNaviScreen.HomeScreen,
        BottomNaviScreen.SettingScreen,
    )

    NavigationBar(
        containerColor = Color.Blue,
        contentColor = Color.Green, //这个颜色不起作用
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        screens.forEach { item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = null)},
                label = { Text(text = stringResource(id = item.resourceId))},
                selected = currentDestination?.hierarchy?.any{it.route == item.route} == true,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                //设置bar的各种颜色
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Cyan,
                    selectedTextColor = Color.Cyan,
                    //这个颜色最好和NavigationBar的containerColor颜色保持一致，不然会在Icon外层显示一个背景颜色
                    indicatorColor = Color.Blue,
                    unselectedIconColor = Color.Yellow,
                    unselectedTextColor = Color.Yellow,
                ),
            )
        }
    }
}

@Composable
fun PersonScreen(naviController: NavController) {
    Box (
        modifier = Modifier.fillMaxSize().background(color = Color.Cyan),
        contentAlignment = Alignment.Center){
        Text(text = "Person Screen")
    }
}
@Composable
fun HomeScreen(naviController: NavController) {
    Box (
        modifier = Modifier.fillMaxSize().background(color = Color.Magenta),
        contentAlignment = Alignment.Center){
        Text(text = "Home Screen")
    }
}
@Composable
fun SettingScreen(naviController: NavController) {
    Box (
        modifier = Modifier.fillMaxSize().background(color = Color.LightGray),
        contentAlignment = Alignment.Center){
        Text(text = "Setting Screen")
    }
}
