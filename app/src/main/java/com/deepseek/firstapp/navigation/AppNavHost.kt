package com.deepseek.firstapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.deepseek.firstapp.Screens.Login.LoginScreen
import com.deepseek.firstapp.Screens.Package.AddProductScreen
import com.deepseek.firstapp.Screens.Register.RegisterScreen
import com.deepseek.firstapp.Screens.dashboard.DashboardScreen
import com.deepseek.firstapp.navigation.splashscreen.SplashScreen

@Composable
fun AppNavHost(
    modifier: Modifier= Modifier,
    navController: NavHostController= rememberNavController(),
    startDestination: String=ROUTE_SPLASHSCREEN
){
    NavHost(
        navController=navController,
        modifier=modifier,
        startDestination= startDestination
    ){
      composable(ROUTE_LOGIN) {
          LoginScreen(navController)
      }
        composable ( ROUTE_REGISTER ){
            RegisterScreen(navController)
        }
        composable (ROUTE_DASHBOARD){
            DashboardScreen(navController)
        }
        composable (ROUTE_SPLASHSCREEN){
            SplashScreen(navController)
        }
        composable ( ROUTE_ADDPRODUCT ){
            AddProductScreen(navController)
        }
    }
}