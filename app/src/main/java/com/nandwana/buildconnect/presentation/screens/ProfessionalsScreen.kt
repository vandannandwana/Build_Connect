package com.nandwana.buildconnect.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Message
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.nandwana.buildconnect.R
import com.nandwana.buildconnect.data.model.ProfessionalModel
import com.nandwana.buildconnect.presentation.viewmodels.ProfessionalsScreenViewModel

@Composable
fun ProfessionalsScreen(modifier: Modifier = Modifier, navHostController: NavHostController) {

    val professionalViewModel = hiltViewModel<ProfessionalsScreenViewModel>()
    var engineersCount by remember {
        mutableIntStateOf(0)
    }
    val engineerState = professionalViewModel.engineers.collectAsStateWithLifecycle().value
    var colHeight by remember {
        mutableStateOf(420.dp)
    }
    LaunchedEffect(engineerState.engineers) {
        if(engineerState.engineers.isNotEmpty()){
            engineersCount = engineerState.engineers.size
            colHeight = 220.dp* engineersCount + 300.dp
        }
    }

    LazyColumn(
        modifier=Modifier
            .fillMaxWidth()
            .height(colHeight),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        item {
            Text(
                text = "Popular Engineers Nearby You",
                fontFamily = FontFamily(Font(R.font.lateef_regular)),
                fontSize = 34.sp,
                fontWeight = FontWeight.Light
            )
        }

        if(engineerState.engineers.isNotEmpty()){
            items(engineerState.engineers.size){
                EngineerCard(engineerState.engineers[it])
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }


}

@Composable
private fun EngineerCard(engineer: ProfessionalModel) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .background(Color.White)
    ) {

        AsyncImage(
            model = engineer.workImages[0],
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .align(Alignment.TopCenter),
            contentScale = ContentScale.Crop
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .background(Color.White)
                .align(Alignment.BottomCenter)
                .border(1.dp, Color.Gray),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.4f)
                    .border(1.dp, Color.Gray)
            ) {
                Text(
                    "200+",
                    modifier = Modifier.padding(horizontal = 24.dp),
                    fontFamily = lateef_regular_font
                )
                Text(
                    "Projects", modifier = Modifier.padding(horizontal = 24.dp),
                    fontFamily = lateef_regular_font
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.3f)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Message,
                    "message_icon",
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
                Text(
                    "Message", modifier = Modifier.padding(horizontal = 24.dp),
                    fontFamily = lateef_regular_font
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.4f)
                    .border(1.dp, Color.Gray)
            ) {
                Text(
                    engineer.yearsOfExperience.toString()
                    ,modifier = Modifier.padding(horizontal = 24.dp),
                    fontFamily = lateef_regular_font,
                    overflow = TextOverflow.Clip
                )
                Text(
                    "Years of Experience", modifier = Modifier.padding(horizontal = 24.dp),
                    fontFamily = lateef_regular_font,
                    overflow = TextOverflow.Ellipsis
                )
            }


        }


        Column(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("", fontFamily = lateef_regular_font, fontSize = 18.sp)
            AsyncImage(
                model = engineer.profilePhoto,
                contentDescription = "",
                modifier = Modifier.height(58.dp).clip(CircleShape)
            )
            Text(engineer.name, fontFamily = lateef_regular_font, fontSize = 18.sp)

        }


    }

}