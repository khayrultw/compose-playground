package com.khayrul.androidplayground.presentation.navigation_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.khayrul.androidplayground.R
import com.khayrul.androidplayground.core.preference.PreferencesManager
import com.khayrul.androidplayground.presentation.alarm_manager_playground.AlarmManagerPlayground
import com.khayrul.androidplayground.presentation.notificaion_playground.NotificationPlayground
import com.khayrul.androidplayground.presentation.util.Screen
import com.khayrul.androidplayground.presentation.work_manager_playground.WorkManagerPlayground
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavigationScreen(
    preferencesManager: PreferencesManager,
    createWork: (time: Long) -> Unit
) {

    val scaffoldState = rememberScaffoldState( rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopBar(scope = scope, scaffoldState = scaffoldState)},
        drawerContent = {
            Drawer(
                scope = scope,
                scaffoldState = scaffoldState,
                navController = navController
            )
        }
    ) {
        Navigation(
            navController = navController,
            preferencesManager = preferencesManager,
            createWork = createWork
        )
    }
}

@Composable
fun TopBar(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState
) {
    TopAppBar(
        title = { Text(text = "Android Playground", fontSize = 18.sp)},
        navigationIcon = {
            IconButton(onClick = {scope.launch { scaffoldState.drawerState.open() }}) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu Icon")
            }
        },
        backgroundColor = Color.LightGray,
        contentColor = Color.Black
    )
}

@Composable
fun Drawer(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavController
) {
    val items = listOf(
        Screen.NotificationPlayground,
        Screen.WorkManagerPlayground,
        Screen.AlarmManagerPlayground
    )

    Column(modifier = Modifier.background(Color.White)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_fav),
                contentDescription = "logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(10.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            DrawerItem(item = item, selected = currentRoute == item.route ) {
                navController.navigate(item.route) {
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            }
        }

    }
}

@Composable
fun DrawerItem(item: Screen, selected: Boolean, onItemClick: (Screen) -> Unit) {
    val background = if(selected) Color.LightGray else Color.White
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(item) }
            .height(45.dp)
            .background(background)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = item.title)
    }
}

@Composable
fun Navigation(
    navController: NavHostController,
    preferencesManager: PreferencesManager,
    createWork: (time: Long) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.NotificationPlayground.route
    ) {
        composable(route = Screen.NotificationPlayground.route) {
            NotificationPlayground(preferencesManager = preferencesManager)
        }
        composable(route = Screen.WorkManagerPlayground.route) {
            WorkManagerPlayground(createWork = createWork)
        }
        composable(
            route = Screen.AlarmManagerPlayground.route
        ) {
            AlarmManagerPlayground()
        }
    }
}

