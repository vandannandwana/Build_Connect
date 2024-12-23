package com.nandwana.buildconnect.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.nandwana.buildconnect.R
import com.nandwana.buildconnect.presentation.navigation.Screens

@Composable
fun GetStartedScreen(navController: NavHostController) {

    val lateef_regular_font = FontFamily(Font(R.font.lateef_regular))
    val lateef_bold_font = FontFamily(Font(R.font.lateef_regular))


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
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Column(
                    modifier = Modifier.padding(top = maxHeight / 12, end = maxWidth / 6),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {

                    Text(
                        modifier = Modifier.height(35.dp),
                        text = "Welcome to",
                        fontFamily = lateef_regular_font,
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Light,
                        color = colorResource(R.color.green_fade)
                    )
                    Text(
                        modifier = Modifier.height(44.dp),
                        text = "BuildConnect",
                        fontFamily = lateef_regular_font,
                        fontWeight = FontWeight.Bold,
                        fontSize = 44.sp,
                        color = colorResource(R.color.green_fade)

                    )

                }

                Image(
                    painter = painterResource(R.drawable.undraw_sweet_home),
                    contentDescription = ""
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = maxWidth / 8, end = maxWidth / 8, bottom = maxWidth / 8),
                    onClick = { navController.navigate(Screens.AppIntroductionScreen.route) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.green_fade)
                    )
                ) {
                    Text(
                        text = "Get Started",
                        fontFamily = lateef_regular_font,
                        fontSize = 34.sp,
                        color = colorResource(R.color.white)

                    )
                }


            }


        }
    }

}