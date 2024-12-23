package com.nandwana.buildconnect.presentation.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoneOutline
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Workspaces
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.nandwana.buildconnect.R
import com.nandwana.buildconnect.data.model.project_models.ProjectModel
import com.nandwana.buildconnect.presentation.viewmodels.ProjectsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ProjectProgressScreen(project: ProjectModel, modifier: Modifier = Modifier) {

    val projectsViewModel = hiltViewModel<ProjectsViewModel>()


    val pdfDownloadState by projectsViewModel.pdfDownloadState.collectAsState()


    var progress by remember {
        mutableStateOf(0.0f)
    }
    LaunchedEffect(project) {
        delay(200)
        progress = (project.progress.toFloat()).div(100f) ?: 0.0f
    }

    project.let {

        val animatedProgress by animateFloatAsState(
            targetValue = progress,
            animationSpec = tween(durationMillis = 900)
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )

        {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Project ID: " + it.projectId,
                    fontSize = 18.sp,
                    fontFamily = lateef_regular_font
                )

                Button(
                    onClick = {
                        projectsViewModel.downloadPdf(it.projectId)
                    },
                    shape = RoundedCornerShape(7.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.PictureAsPdf,
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = "Export PDF", fontFamily = lateef_regular_font, fontSize = 14.sp)
                }

            }

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )
            ) {

                Column(
                    modifier = Modifier
                        .background(colorResource(R.color.skin_color_dark))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier.padding(start = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.GraphicEq, contentDescription = "")
                        Spacer(Modifier.width(8.dp))
                        Text(
                            modifier = Modifier,
                            text = "Your Progress",
                            fontSize = 24.sp,
                            fontFamily = lateef_regular_font,
                            textAlign = TextAlign.Center
                        )

                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${(animatedProgress * 100).toInt()}%",
                            fontSize = 70.sp,
                            fontFamily = lateef_regular_font,
                        )
                        Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(130.dp)
                                    .rotate(-90f),
                                progress = { animatedProgress },
                                strokeWidth = 6.dp,
                                trackColor = colorResource(R.color.white),
                                color = colorResource(R.color.green_fade),
                                strokeCap = StrokeCap.Butt,
                            )

                            Text(
                                text = "${(animatedProgress * 100).toInt()}%",
                                color = colorResource(R.color.green_fade),
                                fontSize = 58.sp,
                                softWrap = true,
                                fontFamily = lateef_regular_font,
                            )
                        }
                    }

                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = "Date: 17 December 2024",
                        fontSize = 20.sp,
                        softWrap = true,
                        fontFamily = lateef_regular_font,
                    )


                }

            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.padding(start = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.GraphicEq, contentDescription = "")
                Spacer(Modifier.width(8.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Project Gallery",
                    fontSize = 24.sp,
                    fontFamily = lateef_regular_font,
                    textAlign = TextAlign.Start
                )

            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {

                val images = listOf(
                    listOf(
                        "https://th.bing.com/th/id/OIP.gYBLUssHCk-yx0FDt-NvWwHaFj?rs=1&pid=ImgDetMain",
                        "12 February 2023"
                    ),
                    listOf(
                        "https://live.staticflickr.com/3230/2704886039_75d244ee06_b.jpg",
                        "5 February 2023"
                    ),
                    listOf(
                        "https://1.bp.blogspot.com/_Hsv6bELfMY0/TUkPj4ISE6I/AAAAAAAAAGg/ZsKCumnK8lU/s320/IMG_0804.JPG",
                        "1 February 2023"
                    ),
                    listOf(
                        "https://pinkpeppermintdesign.com/wp-content/uploads/2023/08/new-windows-alumni-house-1-scaled.jpg",
                        "24 January 2023"
                    ),

                    )

                LazyRow {

                    items(images.size) {
                        GalleryImageItem(image = images[it])
                    }


                }
            }



            Spacer(modifier = Modifier.height(24.dp))

            PhasesBlock(
                currentPhase = it.currentPhase,
                upCommingPhases = it.upcomingPhases
            )

//                    Button(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(horizontal = 12.dp, vertical = 8.dp),
//                        onClick = {
//                            projectsViewModel.viewPdf(it.projectId)
//                        },
//                        shape = RoundedCornerShape(7.dp),
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = colorResource(R.color.skin_color),
//                            contentColor = colorResource(R.color.green_fade)
//                        )
//                    ) {
//                        Text(
//                            modifier = Modifier.padding(vertical = 2.dp),
//                            text = "View Progress as PDF",
//                            fontSize = 24.sp,
//                            fontFamily = lateef_regular_font
//                        )
//
//                    }
//
//                    if (pdfDownloadState.isDownloading) {
//                        CircularProgressIndicator()
//                    } else if (pdfDownloadState.file != null) {
//
//                        PdfRendererViewCompose(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(330.dp)
//                                .verticalScroll(
//                                    rememberScrollState()
//                                ),
//                            file = pdfDownloadState.file,
//                        )
//
//                        Button(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(horizontal = 12.dp, vertical = 8.dp),
//                            onClick = {
//                                projectsViewModel.downloadPdf(it.projectId)
//                            },
//                            shape = RoundedCornerShape(7.dp),
//                            colors = ButtonDefaults.buttonColors(
//                                containerColor = colorResource(R.color.skin_color),
//                                contentColor = colorResource(R.color.green_fade)
//                            )
//                        ) {
//                            Text(
//                                modifier = Modifier.padding(vertical = 2.dp),
//                                text = "Download All Progress as PDF",
//                                fontSize = 24.sp,
//                                fontFamily = lateef_regular_font
//                            )
//
//                        }


//                    }


        }
    }

}

@Composable
fun GalleryImageItem(image: List<String>) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            modifier = Modifier
                .height(200.dp)
                .width(300.dp)
                .padding(horizontal = 8.dp)
                .clip(RoundedCornerShape(12.dp)),
            model = image[0],
            contentDescription = "gallery_images",
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = image[1],
            fontSize = 16.sp,
            fontFamily = lateef_regular_font
        )
    }


}

@Composable
private fun PhasesBlock(
    currentPhase: String = "ABCD",
    upCommingPhases: List<String> = listOf("EFGH", "IJKL", "MNOP")
) {
    Column(
        modifier = Modifier
            .background(Color.Transparent)
            .fillMaxSize()
            .padding(12.dp)
    ) {

        Row(
            modifier = Modifier.padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Default.Workspaces, contentDescription = "")
            Spacer(Modifier.width(8.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Your Phases",
                fontSize = 24.sp,
                fontFamily = lateef_regular_font,
                textAlign = TextAlign.Start
            )

        }

//        Text(
//            modifier = Modifier.padding(start = 8.dp),
//            text = "Current Phase: $currentPhase",
//            textAlign = TextAlign.Start,
//            fontSize = 22.sp,
//            fontFamily = lateef_regular_font
//        )
//
//        Text(
//            modifier = Modifier.padding(start = 8.dp).fillMaxWidth(),
//            text = "Upcoming Phases:",
//            textAlign = TextAlign.Center,
//            fontSize = 28.sp,
//            fontFamily = lateef_regular_font
//        )
        Spacer(Modifier.height(24.dp))

        PhasesView(upComingPhases = upCommingPhases)
    }

}


@Composable
fun PhasesView(upComingPhases: List<String>) {

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
    ) {
        items(upComingPhases.size) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Black.copy(alpha = 0.2f))
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.height(60.dp)
            ) {

                Icon(
                    imageVector = Icons.Default.House,
                    contentDescription = "",
                    modifier = Modifier.weight(0.1f)
                )

                Text(
                    upComingPhases[it],
                    textAlign = TextAlign.Start,
                    color = colorResource(R.color.black),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(0.8f),
                )

                Icon(
                    imageVector = Icons.Default.DoneOutline,
                    tint = colorResource(R.color.black),
                    contentDescription = "",
                    modifier = Modifier.weight(0.1f)
                )

            }

        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Black.copy(alpha = 0.2f))
            )
            Text(
                ".\n.\n.",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 34.sp
            )
        }
    }

}


