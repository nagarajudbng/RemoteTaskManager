package com.single.point.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pesto.authentication.presentation.SignInScreen
import com.pesto.authentication.presentation.SignUpScreen
import com.pesto.profile.presentation.ProfileScreen
import com.pesto.taskhome.presentation.HomeScreen
import com.pesto.todocreate.presentation.TaskCreateScreen
import kotlinx.coroutines.launch

// Created by Nagaraju Deshetty on 07/05/24.

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = NavigationItem.SIGN_IN.route
    ) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) {
        Column {
            NavHost(
                modifier = Modifier,
                navController = navController,
                startDestination = startDestination
            ) {

                composable(NavigationItem.HOME.route) {
                    HomeScreen(
                        onNavigation ={
                            if(it == "Profile"){
                                navController.navigate(NavigationItem.PROFILE.route)
                            } else {
                                navController.navigate(NavigationItem.ADDTASK.route)
                            }
                        },
                        onSnackBarMessage={
                            scope.launch {
                                snackBarHostState.showSnackbar(it)
                            }
                        }
                    )
                }
                composable(NavigationItem.ADDTASK.route) {
                    TaskCreateScreen(
                        onNavigation = {
                            navController.popBackStack()
                        },
                        onSnackBarMessage = {
                            scope.launch {
                                snackBarHostState.showSnackbar(it)
                            }
                        }
                    )
                }
                composable(NavigationItem.PROFILE.route) {
                    ProfileScreen(
                        onNavigation = {
                            navController.popBackStack()
                        }
                    )
                }
                composable(NavigationItem.SIGN_IN.route) {
                    SignInScreen(
                        onNavigation = {
                            if(it == "TaskList"){
                                navController.navigate(NavigationItem.HOME.route)
                            }
                            if(it == "SingUp"){
                                navController.navigate(NavigationItem.SIGNUP.route)
                            }

                        }
                    )
                }
                composable(NavigationItem.SIGNUP.route) {
                    SignUpScreen(
                        onNavigation = {
                            if(it == "TaskList"){
                                navController.navigate(NavigationItem.HOME.route)
                            } else if(it == "SignIn"){
                                navController.navigate(NavigationItem.SIGN_IN.route)
                            }
                        }
                    )
                }
            }
        }
    }
}