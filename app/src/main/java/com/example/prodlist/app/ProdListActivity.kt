package com.example.prodlist.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.navDeepLink
import com.example.prodlist.aosutil.navigation.LocalNavController
import com.example.prodlist.designsys.theme.ProdListTheme
import com.example.prodlist.home.HomeScreen
import com.example.prodlist.navigation.Uris
import com.example.prodlist.proddetail.ProductDetailScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class ProdListActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProdListApp()
        }
    }
}


@Composable
private fun ProdListApp() {
    ProdListTheme {
        @OptIn(ExperimentalAnimationApi::class)
        CompositionLocalProvider(LocalNavController provides rememberAnimatedNavController()) {

            // 참고 : https://google.github.io/accompanist/navigation-animation
            AnimatedNavHost(navController = LocalNavController.current, startDestination = "home") {

                composable(
                    route = "home",
                    exitTransition = { fadeOut() },
                    popEnterTransition = { fadeIn() },
                ) {
                    HomeScreen()
                }

                composable(
                    route = "product?key={key}",
                    deepLinks = listOf(navDeepLink { uriPattern = "${Uris.ProdList.PRODUCT_DETAIL}?key={key}" }),
                    enterTransition = { slideIn { fullSize -> IntOffset(fullSize.width, 0) } },
                    popExitTransition = { slideOut { fullSize -> IntOffset(fullSize.width, 0) } },
                ) { entry ->
                    val productKey = entry.arguments!!.getString("key")!!
                    ProductDetailScreen(productKey)
                }
            }
        }
    }
}
