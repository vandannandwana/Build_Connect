package com.nandwana.buildconnect.presentation.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Construction
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nandwana.buildconnect.R
import com.nandwana.buildconnect.presentation.viewmodels.ProjectsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProjectScreen(projectId: String) {

    val projectsViewModel = hiltViewModel<ProjectsViewModel>()

    val projectState = projectsViewModel.project.collectAsStateWithLifecycle().value

    val pagerState = rememberPagerState { 3 }
    var currentPage by remember {
        mutableIntStateOf(0)
    }
    var project by remember {
        mutableStateOf(projectState)
    }


    LaunchedEffect(projectState.project) {
        CoroutineScope(Dispatchers.IO).launch {
            projectsViewModel.getProject(projectId)
        }
        project = projectState
    }

    LaunchedEffect(currentPage) {
        pagerState.animateScrollToPage(
            currentPage,
            animationSpec = tween(durationMillis = 700, easing = FastOutSlowInEasing)
        )
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        val maxHeight = maxHeight
        val maxWidth = maxWidth

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) { ip ->
            val ip = ip
            if (project.project != null) {
                project.project!!.let {project->

                    LazyColumn(
                        modifier = Modifier
                            .padding(ip)
                            .padding(horizontal = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        item {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = project.projectName,
                                fontSize = 34.sp,
                                fontFamily = lateef_regular_font,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }

                        stickyHeader {
                            Spacer(modifier = Modifier.fillMaxWidth().height(2.dp).background(Color.Black))
                            ProjectTopAppBarNavigation(currentPage = currentPage) {
                                currentPage = it
                            }

                            Spacer(modifier = Modifier.height(24.dp))
                        }

                        item {
                            HorizontalPager(pagerState, userScrollEnabled = false) {
                                when (it) {

                                    0 -> ProjectProgressScreen(project =project)
                                    1 -> MaterialsScreen(project = project)
                                    else -> ProjectProgressScreen(project = project)


                                }
                            }
                        }


                    }
                }

            }
        }


    }


}

val projectAmenities = listOf(
    Amenities(icon = Icons.Default.GraphicEq, name = "Progress"),
    Amenities(icon = Icons.Default.Construction, name = "Material")
)

@Composable
fun ProjectTopAppBarNavigation(
    modifier: Modifier = Modifier,
    currentPage: Int,
    onClick: (index: Int) -> Unit
) {


    val startColor = colorResource(R.color.green_fade)
    val endColor = colorResource(R.color.skin_color)


    LazyRow {
        items(projectAmenities.size) {
            val animatedColor by animateColorAsState(
                targetValue = if (currentPage == it) startColor else endColor,
                animationSpec = tween(400), label = ""
            )
            AmenityItem(projectAmenities[it], index = it, animatedColor = animatedColor) {
                onClick(it)
            }
        }
    }
}