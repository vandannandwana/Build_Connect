package com.nandwana.buildconnect.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.nandwana.buildconnect.R
import com.nandwana.buildconnect.data.model.project_models.ProjectModel
import com.nandwana.buildconnect.presentation.navigation.Screens
import com.nandwana.buildconnect.presentation.viewmodels.ProjectsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ProjectsScreen(navHostController: NavHostController,modifier: Modifier = Modifier) {

    val projectsViewModel = hiltViewModel<ProjectsViewModel>()

    var projectsCount by remember {
        mutableIntStateOf(0)
    }
    val projectsState = projectsViewModel.projects.collectAsStateWithLifecycle().value

    var colHeight by remember{
        mutableStateOf(420.dp)
    }

    LaunchedEffect(projectsState.projects) {
        if(projectsState.projects.isNotEmpty()){
            projectsCount = projectsState.projects.size
            colHeight = 200.dp* projectsCount + 300.dp
        }
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(colHeight),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(projectsState.projects.isNotEmpty()){
            items(projectsState.projects.size){
                ProjectsPreview(projectsState.projects[it]){projectId->
                    CoroutineScope(Dispatchers.IO).launch {
                        projectsViewModel.getProject(projectId)
                    }
                    navHostController.navigate(Screens.ProjectScreen.route+"/$projectId")
                }
            }
        }


    }
}

@Composable
private fun ProjectsPreview(project:ProjectModel,onClick:(projectId:String)->Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.elevatedCardElevation(10.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.skin_color))
                .padding(12.dp)
                .clickable {
                    onClick(project.projectId)
                },
            verticalArrangement = Arrangement.SpaceBetween

        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = project.projectName,
                    fontSize = 32.sp,
                    fontFamily = lateef_regular_font
                )

                AsyncImage(
                    model = "https://images.pexels.com/photos/259588/pexels-photo-259588.jpeg?cs=srgb&dl=landscape-sky-clouds-259588.jpg&fm=jpg",
                    contentDescription = "Construction Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(45.dp)
                )


            }

            Text(
                text = "Description Wil be shown Here",
                fontSize = 16.sp,
                fontFamily = lateef_regular_font
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location Icon"
                )
                Text(
                    text = "Location Wil be shown Here",
                    fontSize = 16.sp,
                    fontFamily = lateef_regular_font
                )
            }

            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height((0.6f).dp)
                    .background(Color.Black.copy(alpha = 0.6f))
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Row {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = "Calendar Icon"
                    )
                    Text(
                        text = "15 Jan 2024 to 21 Dec 2024",
                        fontSize = 16.sp,
                        fontFamily = lateef_regular_font
                    )
                }


                AsyncImage(
                    model = "https://wallpapers.com/images/file/cartoon-profile-pictures-znyqac8g02ndd3v0.jpg",
                    contentDescription = "engineer picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(25.dp)
                )

            }

        }
    }
}
