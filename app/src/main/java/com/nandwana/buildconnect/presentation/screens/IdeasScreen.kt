package com.nandwana.buildconnect.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.nandwana.buildconnect.R

@Composable
fun IdeasScreen(modifier: Modifier = Modifier, navHostController: NavHostController) {

    val screen_height = popularProjects.size * 212.dp + 200.dp

    LazyColumn(modifier = Modifier.height(screen_height).fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp), horizontalAlignment = Alignment.CenterHorizontally){

        item {
            Text(
                text = "Popular Project Nearby You",
                fontFamily = FontFamily(Font(R.font.lateef_regular)),
                fontSize = 34.sp,
                fontWeight = FontWeight.Light
            )
        }
        items(popularProjects.size){projectId->

            Project(projectId=projectId)

        }
    }

}



data class Project(
    val name: String,
    val location: String,
    val image: String
)
val popularProject = listOf(
    Project(
        "Project 1",
        "Location 1",
        "https://images.pexels.com/photos/462358/pexels-photo-462358.jpeg?cs=srgb&dl=architectural-design-architecture-blue-sky-462358.jpg&fm=jpg"
    ),
    Project(
        "Project 2",
        "Location 2",
        "https://images.pexels.com/photos/259588/pexels-photo-259588.jpeg?cs=srgb&dl=landscape-sky-clouds-259588.jpg&fm=jpg"
    ),
    Project(
        "Project 3",
        "Location 3",
        "https://th.bing.com/th/id/OIP.qOhLUJ0bLLTCEk0aOI5IPwHaFj?rs=1&pid=ImgDetMain"
    )
)

val popularProjects = listOf(
    popularProject[0].copy(name = "Kitchen"),
    popularProject[1].copy(name = "Garden"),
    popularProject[2].copy(name = "Outside"),
)


@Composable
fun Project(modifier: Modifier = Modifier, projectId: Int) {

    val pagerState = rememberPagerState { popularProjects.size }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Black
        ),
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        )
    ) {
        Column {
            Box {
                HorizontalPager(pagerState, modifier = Modifier.fillMaxWidth()) { image ->

                    ProjectItem(popularProject[image])

                }
                ProjectsDotsIndicator(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    pagerState = pagerState
                )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                text = popularProjects[projectId].name,
                fontSize = 24.sp,
                fontFamily = lateef_regular_font,
                textAlign = TextAlign.Center
            )
        }


    }

}

@Composable
fun ProjectItem(project: Project) {

    Box(
        Modifier
            .fillMaxWidth()
            .height(170.dp)
    ) {

        AsyncImage(
            model = project.image,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
//                .clip(
//                    RoundedCornerShape(
//                        topStart = 12.dp,
//                        topEnd = 12.dp,
//                        bottomStart = 24.dp,
//                        bottomEnd = 24.dp
//                    )
//                )
        )

    }


}