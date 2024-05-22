package com.pesto.remotetaskmanager

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pesto.remotetaskmanager.ui.theme.RemoteTaskManagerTheme
import com.single.point.navigation.AppNavHost
import com.single.point.navigation.NavigationItem
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sharedPreferences:SharedPreferences
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RemoteTaskManagerTheme {
                  val navController = rememberNavController()
                    val userName = sharedPreferences.getString("userName","")
                    var startDestination =  NavigationItem.SIGN_IN.route
                    if(!userName.isNullOrBlank()){
                        startDestination = NavigationItem.HOME.route
                    }
                    AppNavHost(
                        navController = navController,
                        startDestination = startDestination
                    )


            }
        }
    }
}
