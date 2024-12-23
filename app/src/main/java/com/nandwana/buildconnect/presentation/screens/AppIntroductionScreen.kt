package com.nandwana.buildconnect.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.nandwana.buildconnect.R
import com.nandwana.buildconnect.presentation.navigation.Screens

data class IntroductionItem(
    val image: Int,
    val title: String,
    val description: String
)

val introductionItems = listOf(
    IntroductionItem(
        image = R.drawable.undraw_under_construction,
        title = "Start Building Your Dream Home!",
        description = "Connect with civil engineers and architects for perfect planning and material solutions."
    ),
    IntroductionItem(
        image = R.drawable.undraw_logistics,
        title = "Material Estimation Made Easy!",
        description = "Get accurate estimates for materials and budgets tailored to your project."
    ),
    IntroductionItem(
        image = R.drawable.undraw_judge_katerina,
        title = "Expert Guidance, Every Step!",
        description = "Chat directly with professionals and receive the best advice for your construction needs."
    ),
    IntroductionItem(
        image = R.drawable.undraw_download,
        title = "One Platform, All Solutions!",
        description = "From planning to execution, your one-stop solution is here."
    ),
)

@Composable
fun AppIntroductionScreen(navController: NavHostController) {

    val lateef_regular_font = FontFamily(Font(R.font.lateef_regular))

    val pagerState = rememberPagerState {
        introductionItems.size
    }


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.skin_color))
                .padding(it)

        ) {
            val maxHeight = maxHeight
            val maxWidth = maxWidth

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = maxHeight / 12),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        modifier = Modifier.height(44.dp),
                        text = "BuildConnect",
                        fontFamily = lateef_regular_font,
                        fontWeight = FontWeight.Bold,
                        fontSize = 44.sp,
                        color = colorResource(R.color.green_fade)

                    )

                }

                Column {
                    IntroductionImages(
                        modifier = Modifier
                            .height(maxHeight/2)
                            .fillMaxWidth()
                            .padding(horizontal = maxWidth/16),
                        pagerState = pagerState
                    )
                    IntroductionImageIndicator(
                        modifier = Modifier.padding(vertical = maxHeight/64),
                        pagerState = pagerState
                    )
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = maxWidth / 8, end = maxWidth / 8, bottom = maxWidth / 8),
                    onClick = { navController.navigate(Screens.LoginScreen.route) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.green_fade)
                    )
                ) {
                    Text(
                        text = "Let's Build It",
                        fontFamily = lateef_regular_font,
                        fontSize = 34.sp,
                        color = colorResource(R.color.white)

                    )
                }


            }


        }
    }

}

@Composable
fun IntroductionImageIndicator(modifier: Modifier = Modifier, pagerState: PagerState) {

    Row(
        modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color =
                if (pagerState.currentPage == iteration) colorResource(id = R.color.white) else Color.Transparent
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .border(2.dp, color = Color.White, shape = CircleShape)
            )
        }
    }

}

@Composable
fun IntroductionImages(modifier: Modifier = Modifier, pagerState: PagerState) {
    val lateef_regular_font = FontFamily(Font(R.font.lateef_regular))
    HorizontalPager(modifier = modifier, state = pagerState) { page ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(introductionItems[page].image),
                contentDescription = ""
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = introductionItems[page].title,
                color = colorResource(R.color.black),
                fontSize = 24.sp,
                fontFamily = lateef_regular_font,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(
                text = introductionItems[page].description,
                color = colorResource(R.color.black),
                fontSize = 20.sp,
                fontFamily = lateef_regular_font,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )


        }

    }

}
