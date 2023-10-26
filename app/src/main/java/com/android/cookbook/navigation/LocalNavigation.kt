package com.android.cookbook.navigation

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController

//创建composeLocal对象来共享数据,我觉得它类似Flutter中的Provider，只是没有Provider做的好
//官方文档叫它：通过组合隐式向下传递数据的工具，可以舒服么显式参数来替代它
//项目中的theme中大量使用了ComposeLocal，官方不推荐使用它，我觉得使用它共享NavController是一个好的方案，不然每个页面中
//都需要传递navController
val LocalNavController = compositionLocalOf<NavHostController> {
    error("localNavController not present")
}

//val LocalBackPressedDispatcher = staticCompositionLocalOf {
//    error("there is not back dispatcher provided")
//}