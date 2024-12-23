package com.nandwana.buildconnect.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nandwana.buildconnect.presentation.screens.AppIntroductionScreen
import com.nandwana.buildconnect.presentation.screens.GetStartedScreen
import com.nandwana.buildconnect.presentation.screens.LoginScreen
import com.nandwana.buildconnect.presentation.screens.MainScreen
import com.nandwana.buildconnect.presentation.screens.ProjectProgressScreen
import com.nandwana.buildconnect.presentation.screens.ProjectScreen
import com.nandwana.buildconnect.presentation.screens.SignUpScreen

@Composable
fun Navigation(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screens.MainScreen.route) {

        composable(
            Screens.GetStartedScreen.route
        ){
            GetStartedScreen(navController = navController)
        }

        composable(
            Screens.AppIntroductionScreen.route,
            enterTransition = {
                slideIntoContainer(initialOffset = { 1000 },  towards = AnimatedContentTransitionScope.SlideDirection.End, animationSpec = tween(500))
            },
            exitTransition = {
                slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Start, animationSpec = tween(500))
            }
        ){
            AppIntroductionScreen(navController = navController)
        }

        composable(
            Screens.LoginScreen.route,
            enterTransition = {
                slideIntoContainer(initialOffset = { 1000 }, towards = AnimatedContentTransitionScope.SlideDirection.End, animationSpec = tween(500))
            },
            exitTransition = {
                slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Start, animationSpec = tween(500))
            }
        ){
            LoginScreen(navController = navController)
        }

        composable(route = Screens.SignUpScreen.route) {
            SignUpScreen(navHostController = navController)
        }

        composable(route = Screens.MainScreen.route){
            MainScreen(navHostController = navController)
        }

        composable(route = Screens.ProjectScreen.route+"/{projectId}"){
            val projectId = it.arguments?.getString("projectId")
            if(projectId != null){
                ProjectScreen(projectId = projectId)
            }
        }


    }

}