package com.android.cookbook.pages

import androidx.compose.runtime.Composable

class ScreenData {

    //定义页面类：用来存放页面说明，路由导航，以及页面中的组合函数，
    class Screens(description:String,route:String,composeFun:@Composable ()->Unit) {
        val description:String
        val route:String
        val composableFun:@Composable ()->Unit

        init {
            this.description = description
            this.route = route
            this.composableFun =composeFun
        }
    }


    companion object {
        //创建路由的string中不能包含/这种反斜线,最后一个参数不起作用，估计是类型有问题，我感觉向二级指针一样操作就会好
        //在composable函数后面加上括号就可以,这样就可以自动通过循环添加导航图了，不再需要手动添加导航图
        //如何使用手动添加导航图的模式，那么添加新页面是需要在这里和MainActivity中的navHost中添加路径和导航图，
        val  screeList = listOf(
            Screens("ex001: Layout and Pages", "layout") { LayoutPage() },
            Screens("ex002: Test and TextField", "text") { KindsOfText() },
            Screens("ex003: All kinds of Button", "button") { ExButton() },
            Screens("ex004: Progress", "progress") { ExProgress() },
            Screens("ex005: All Scaffold content", "scaffold") { ExScaffold() },
//            Screens("ex006: Dialog and Toast ", "dialog") { DialogScreenMain()},
            Screens("ex006: Dialog and Toast ", "dialog") { Ex006DialogScreen("Dialog example")},
            Screens("ex007: Kinds of list", "list") { Ex007ListScreen("List example") },
            Screens("ex008: Event and Gesture", "event") { Ex008EventScreen("Event example") },
            Screens("ex009: Slider ", "slider") { Ex009SliderScreen("Slider example") },
        )
    }
}