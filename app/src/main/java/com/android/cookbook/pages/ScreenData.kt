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
        //添加新页面是需要在这里和MainActivity中的navHost中添加路径和导航图
        val  screeList = listOf<Screens>(
        Screens("ex001: Layout and Pages","layout") { LayoutPage() },
        Screens("ex002: Test and TextField","text") { KindsOfText() },
        Screens("ex003: All kinds of Button","button") { ExButton() },
        Screens( "ex004: Progress","progress") { ExProgress() },
        Screens( "ex005: All Scaffold content","scaffold") { ExScaffold()}, )
    }
}