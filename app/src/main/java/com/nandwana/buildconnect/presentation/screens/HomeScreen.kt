package com.nandwana.buildconnect.presentation.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MapsHomeWork
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.exyte.animatednavbar.utils.noRippleClickable
import com.nandwana.buildconnect.R


data class Amenities(
    val name: String,
    val icon: ImageVector
)

@Composable
fun HomeScreen(navHostController: NavHostController) {

    val pagerState = rememberPagerState { 3 }
    var currentPage by rememberSaveable{
        mutableIntStateOf(0)
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

        LazyColumn (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.height(maxHeight)) {

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = CircleShape,
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp,
                    )
                ) {
                    SearchBox(
                        modifier = Modifier.fillMaxWidth(),
                        searchText = "",
                        onValueChange = {}
                    )
                }

                Spacer(modifier = Modifier.padding(12.dp))
            }

            item {
                TopAppBarNavigation(currentPage = currentPage) {
                    currentPage = it
                }
            }

            item {
                HorizontalPager(pagerState, userScrollEnabled = false) {
                    when (it) {

                        0 -> IdeasScreen(navHostController = navHostController)
                        1 -> ProfessionalsScreen(navHostController = navHostController)
                        else -> ProjectsScreen(navHostController = navHostController)


                    }
                }
            }





//                LazyRow {
//
//                    items(popularProjects.size){
//                        ProjectItem(popularProjects[it])
//                    }
//
//                }

//                LazyColumn {
//                    items(popularProjects.size){projectId->
//
//                        Project(projectId=projectId)
//
//                    }
//                }




        }
    }

}


@Composable
fun ProjectsDotsIndicator(modifier: Modifier = Modifier, pagerState: PagerState) {
    Row(
        modifier
            .wrapContentHeight()
            .padding(bottom = 4.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color =
                if (pagerState.currentPage == iteration) colorResource(R.color.green_fade) else Color.Transparent
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .border(
                        width = 1.dp,
                        color = colorResource(R.color.green_fade),
                        shape = CircleShape
                    )
            )
        }
    }
}



@Composable
fun AmenityItem(
    amenities: Amenities,
    index: Int = 0,
    animatedColor: Color,
    onClick: (index: Int) -> Unit
) {


    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .noRippleClickable { onClick(index) }
            .padding(8.dp)
            .clip(RoundedCornerShape(7.dp))
            .background(animatedColor)
            .padding(horizontal = 12.dp, vertical = 8.dp)

    ) {
        Box {

            Icon(imageVector = amenities.icon, contentDescription = "")

        }
        Text(
            text = amenities.name,
            fontFamily = FontFamily(Font(R.font.lateef_regular)),
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            color = colorResource(R.color.black)
        )
    }

}

@Composable
fun SearchBox(
    modifier: Modifier = Modifier,
    searchText: String,
    placeholder: String = "Search",
    onValueChange: (String) -> Unit
) {
    TextField(
        value = searchText,
        onValueChange = { onValueChange(it) },
        modifier = modifier.clip(CircleShape),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = Color.LightGray.copy(alpha = 0.3f),
            focusedContainerColor = Color.LightGray.copy(alpha = 0.3f)
        ),
        singleLine = true,
        placeholder = {
            Text(
                text = placeholder,
                fontFamily = FontFamily(Font(R.font.lateef_regular)),
                fontSize = 24.sp,
                fontWeight = FontWeight.Light
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "search_icon",
                modifier = Modifier.padding(start = 24.dp, end = 8.dp)
            )
        }
    )
}

val aminities = listOf(
    Amenities("Ideas", Icons.Default.Lightbulb),
    Amenities("Professionals", Icons.Default.Engineering),
    Amenities("YourProjects", Icons.Default.MapsHomeWork),
)

@Composable
fun TopAppBarNavigation(
    modifier: Modifier = Modifier,
    currentPage: Int,
    onClick: (index: Int) -> Unit
) {


    val startColor = colorResource(R.color.green_fade)
    val endColor = colorResource(R.color.skin_color)


    LazyRow {
        items(aminities.size) {
            val animatedColor by animateColorAsState(
                targetValue = if (currentPage == it) startColor else endColor,
                animationSpec = tween(400), label = ""
            )
            AmenityItem(aminities[it], index = it, animatedColor = animatedColor) {
                onClick(it)
            }
        }
    }


}