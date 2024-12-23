package com.nandwana.buildconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.LocalGroceryStore
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.WorkHistory
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.nandwana.buildconnect.presentation.navigation.Navigation
import com.nandwana.buildconnect.presentation.screens.MaterialsScreen
import com.nandwana.buildconnect.presentation.screens.lateef_regular_font
import com.nandwana.buildconnect.ui.theme.BuildConnectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BuildConnectTheme {
//                MaterialsScreen()
//                TopAppBarNavigation()
                val navController = rememberNavController()
//                PhasesBlock()
                Navigation(navController)
//                MainScreen(navHostController = navController)
//                HomeScreen()
//                SignUpScreen()
//                SignUpScreen(navHostController = navController)
//                GetStartedScreen(navController = navController)
//                AppIntroductionScreen(navController = navController)
//                LoginScreen(navController)
            }
        }
    }
}


data class ProjectExtraBlockItem(
    val name: String,
    val icon: ImageVector
)

val projectExtraItems = listOf(
    ProjectExtraBlockItem("Project Information", Icons.Default.Timeline),
    ProjectExtraBlockItem("Materials & Financial", Icons.Default.LocalGroceryStore),
    ProjectExtraBlockItem("Workforce \n&\n Scheduling Insights", Icons.Default.WorkHistory),
    ProjectExtraBlockItem("Progress Monitoring & Deliverables", Icons.Default.AccessTime),
)

@Preview
@Composable
private fun ProjectExtras() {

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            ProjectExtrasBlock(projectExtraItems[0])
            ProjectExtrasBlock(projectExtraItems[1])
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            ProjectExtrasBlock(projectExtraItems[2])
            ProjectExtrasBlock(projectExtraItems[3])
        }
    }

}

@Composable
fun ProjectExtrasBlock(item: ProjectExtraBlockItem) {

    Card(
        modifier = Modifier
            .size(120.dp),
        border = BorderStroke(1.dp, colorResource(R.color.green_fade))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.skin_color))
                .padding(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                modifier = Modifier.size(40.dp),
                imageVector = item.icon,
                contentDescription = "",
            )
            Text(
                text = item.name,
                fontSize = 16.sp,
                fontFamily = lateef_regular_font,
                textAlign = TextAlign.Center
            )
        }
    }

}









