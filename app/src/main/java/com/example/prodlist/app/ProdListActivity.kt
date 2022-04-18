package com.example.prodlist.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.example.prodlist.aosutil.navigation.LocalNavController
import com.example.prodlist.designsys.theme.ProdListTheme
import com.example.prodlist.home.HomeScreen
import com.example.prodlist.navigation.Uris
import com.example.prodlist.proddetail.ProductDetailScreen
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
        CompositionLocalProvider(LocalNavController provides rememberNavController()) {
            NavHost(navController = LocalNavController.current, startDestination = "home") {

                composable("home") {
                    HomeScreen()
                }

                composable(
                    route = "product?key={key}",
                    deepLinks = listOf(navDeepLink { uriPattern = "${Uris.ProdList.PRODUCT_DETAIL}?key={key}" }),
                ) { entry ->
                    val productKey = entry.arguments!!.getString("key")!!
                    ProductDetailScreen(productKey)
                }
            }
        }
    }
}
