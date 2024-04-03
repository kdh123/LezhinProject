package com.dhkim.lezhin.presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dhkim.lezhin.R
import com.dhkim.lezhin.presentation.bookmark.BookmarkScreen
import com.dhkim.lezhin.presentation.search.SearchScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDes = backStackEntry?.destination?.route
    val bottomNavItems = listOf(Screen.Search, Screen.Bookmark)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = colorResource(id = R.color.purple_200)
            ) {
                bottomNavItems.forEach {
                    NavigationBarItem(
                        selected = currentDes == it.route,
                        onClick = {
                            navController.navigate(it.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (currentDes == Screen.Search.route) {
                                    it.selectIcon
                                } else {
                                    it.defaultIcon
                                },
                                contentDescription = it.route
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = colorResource(id = R.color.black),
                            unselectedIconColor = colorResource(id = R.color.white),
                            indicatorColor = colorResource(id = R.color.purple_200)
                        )
                    )
                }
            }
        }
    ) {
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            navController = navController,
            startDestination = Screen.Search.route
        ) {
            composable(Screen.Search.route) {
                SearchScreen()
            }
            composable(Screen.Bookmark.route) {
                BookmarkScreen()
            }
        }
    }
}

sealed class Screen(
    val defaultIcon: ImageVector,
    val selectIcon: ImageVector,
    val route: String
) {
    object Search : Screen(Icons.Outlined.Home, Icons.Filled.Home, "search")
    object Bookmark : Screen(Icons.Outlined.Favorite, Icons.Filled.Favorite, "bookmark")
}