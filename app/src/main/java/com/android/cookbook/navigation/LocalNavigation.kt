package com.android.cookbook.navigation

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController

val LocalNavController = compositionLocalOf<NavHostController> {
    error("localNavController not present")
}

//val LocalBackPressedDispatcher = staticCompositionLocalOf {
//    error("there is not back dispatcher provided")
//}