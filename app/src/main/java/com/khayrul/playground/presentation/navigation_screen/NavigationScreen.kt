package com.khayrul.playground.presentation.navigation_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.khayrul.playground.R
import com.khayrul.playground.presentation.alarm_manager_playground.AlarmManagerPlayground
import com.khayrul.playground.presentation.mandelbrot.Mandelbrot
import com.khayrul.playground.presentation.notificaion_playground.NotificationPlayground
import com.khayrul.playground.presentation.util.Screen
import com.khayrul.playground.presentation.work_manager_playground.WorkManagerPlayground
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationScreen() {

    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    Scaffold(
        topBar = { TopBar(scope = scope)},
    ) {
        Navigation(
            modifier = Modifier.padding(it),
            navController = navController
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    scope: CoroutineScope,
) {
    TopAppBar(
        title = { Text(text = "Android Playground", fontSize = 18.sp)},
        navigationIcon = {
            IconButton(onClick = {scope.launch {  }}) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu Icon")
            }
        }
    )
}

@Composable
fun Drawer(
    scope: CoroutineScope,
    navController: NavController
) {
    val items = listOf(
        Screen.NotificationPlayground,
        Screen.WorkManagerPlayground,
        Screen.AlarmManagerPlayground
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

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

        Spacer(modifier = Modifier.height(8.dp))
        Divider()
        Spacer(modifier = Modifier.height(8.dp))

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
            }
        }

    }
}

@Composable
fun DrawerItem(item: Screen, selected: Boolean, onItemClick: (Screen) -> Unit) {
    val background = if(selected) colorResource(R.color.grey200) else Color.White
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(item) }
            .height(45.dp)
            .padding(start = 10.dp, end = 10.dp)
            .background(background)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = item.title)
    }
}

@Composable
fun Navigation(
    modifier: Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.MandelbrotPlayground.route
    ) {
        composable(route = Screen.NotificationPlayground.route) {
            NotificationPlayground()
        }
        composable(route = Screen.WorkManagerPlayground.route) {
            WorkManagerPlayground()
        }
        composable(
            route = Screen.AlarmManagerPlayground.route
        ) {
            AlarmManagerPlayground()
        }

        composable(
            route = Screen.MandelbrotPlayground.route
        ) {
            Mandelbrot()
        }
    }
}

