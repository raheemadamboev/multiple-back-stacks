package xyz.teamgravity.multiplebackstacks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import xyz.teamgravity.multiplebackstacks.ui.theme.MultipleBackStacksTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MultipleBackStacksTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val controller = rememberNavController()
                    val currentBackStack by controller.currentBackStackEntryAsState()

                    Scaffold(
                        bottomBar = {
                            NavigationBar {
                                navigations.forEach { navigation ->
                                    val selected = navigation.route == currentBackStack?.destination?.route

                                    NavigationBarItem(
                                        selected = selected,
                                        label = {
                                            Text(
                                                text = navigation.title
                                            )
                                        },
                                        icon = {
                                            Icon(
                                                imageVector = if (selected) navigation.selectedIcon else navigation.unselectedIcon,
                                                contentDescription = navigation.title
                                            )
                                        },
                                        onClick = {
                                            controller.navigate(
                                                route = navigation.route,
                                                builder = {
                                                    popUpTo(
                                                        id = controller.graph.findStartDestination().id,
                                                        popUpToBuilder = {
                                                            saveState = true
                                                        }
                                                    )
                                                    launchSingleTop = true
                                                    restoreState = true
                                                }
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    ) { padding ->
                        Box(
                            modifier = Modifier.padding(padding)
                        ) {
                            NavHost(
                                navController = controller,
                                startDestination = MyNavigation.Home.route
                            ) {
                                composable(
                                    route = MyNavigation.Home.route
                                ) {
                                    HomeScreen()
                                }
                                composable(
                                    route = MyNavigation.Chat.route
                                ) {
                                    ChatScreen()
                                }
                                composable(
                                    route = MyNavigation.Settings.route
                                ) {
                                    SettingsScreen()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    controller: NavHostController = rememberNavController()
) {
    NavHost(
        navController = controller,
        startDestination = "${MyNavigation.Home.route}0"
    ) {
        repeat(10) { index ->
            composable(
                route = "${MyNavigation.Home.route}$index"
            ) {
                GenericScreen(
                    text = "${MyNavigation.Home.title} $index",
                    onNextClick = {
                        if (index < 9) controller.navigate("${MyNavigation.Home.route}${index + 1}")
                    }
                )
            }
        }
    }
}

@Composable
fun ChatScreen(
    controller: NavHostController = rememberNavController()
) {
    NavHost(
        navController = controller,
        startDestination = "${MyNavigation.Chat.route}0"
    ) {
        repeat(10) { index ->
            composable(
                route = "${MyNavigation.Chat.route}$index"
            ) {
                GenericScreen(
                    text = "${MyNavigation.Chat.title} $index",
                    onNextClick = {
                        if (index < 9) controller.navigate("${MyNavigation.Chat.route}${index + 1}")
                    }
                )
            }
        }
    }
}

@Composable
fun SettingsScreen(
    controller: NavHostController = rememberNavController()
) {
    NavHost(
        navController = controller,
        startDestination = "${MyNavigation.Settings.route}0"
    ) {
        repeat(10) { index ->
            composable(
                route = "${MyNavigation.Settings.route}$index"
            ) {
                GenericScreen(
                    text = "${MyNavigation.Settings.title} $index",
                    onNextClick = {
                        if (index < 9) controller.navigate("${MyNavigation.Settings.route}${index + 1}")
                    }
                )
            }
        }
    }
}

@Composable
fun GenericScreen(
    text: String,
    onNextClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = text
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Button(
            onClick = onNextClick
        ) {
            Text(
                text = stringResource(id = R.string.next)
            )
        }
    }
}

private val navigations: List<MyNavigation> = MyNavigation.entries

private enum class MyNavigation(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    Home(
        title = "Home",
        route = "home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    ),
    Chat(
        title = "Chat",
        route = "chat",
        selectedIcon = Icons.Filled.Email,
        unselectedIcon = Icons.Outlined.Email
    ),
    Settings(
        title = "Settings",
        route = "settings",
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings
    );
}