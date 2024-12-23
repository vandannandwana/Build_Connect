package com.nandwana.buildconnect.presentation.navigation


sealed class Screens(val route: String) {
    data object GetStartedScreen : Screens("get_started_screen")
    data object AppIntroductionScreen : Screens("app_introduction_screen")
    data object LoginScreen : Screens("login_screen")
    data object SignUpScreen : Screens("sign_up_screen")
    data object HomeScreen : Screens("home_screen")
    data object MainScreen : Screens("main_screen")
    data object ProjectScreen: Screens("project_screen")
    data object ProjectProgressScreen: Screens("project_progress_screen")
}