package com.single.point.navigation

// Created by Nagaraju Deshetty on 07/05/24.

enum class Screen{
    HOME,
    ADD_TASK
}
sealed class NavigationItem(val route:String){
    data object HOME: NavigationItem(Screen.HOME.name)
    data object ADDTASK: NavigationItem(Screen.ADD_TASK.name)
}