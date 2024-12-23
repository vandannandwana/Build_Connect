package com.nandwana.buildconnect.presentation.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.indendshape.ShapeCornerRadius
import com.exyte.animatednavbar.utils.noRippleClickable
import com.nandwana.buildconnect.R
import kotlinx.coroutines.launch

@Composable
fun MainScreen(navHostController: NavHostController) {

    var currentPage by rememberSaveable { mutableIntStateOf(0) }
    val navBarItems = remember { NavigationBarItems.entries.toTypedArray() }
    val pagerState = rememberPagerState { NavigationBarItems.entries.size }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(currentPage) {
        coroutineScope.launch {
            pagerState.animateScrollToPage(
                currentPage,
                animationSpec = tween(durationMillis = 700, easing = FastOutSlowInEasing)
            )
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()){
        BoxWithConstraints(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            val height = maxHeight
            val width = maxWidth


            HorizontalPager(pagerState, userScrollEnabled = false) {

                when (it) {
                    0 -> HomeScreen(navHostController = navHostController)
                    1 -> ProfileScreen(navHostController = navHostController)
                    2 -> SearchScreen(navHostController = navHostController)
                }

            }

            BottomNavigationBar(modifier = Modifier.align(Alignment.BottomCenter), currentPage = currentPage, navBarItems = navBarItems) {
                currentPage = it
            }


        }

    }


}


enum class NavigationBarItems(val icon: ImageVector) {

    Person(icon = Icons.Default.Home),
    Search(icon = Icons.Default.Search),
    Profile(icon = Icons.Default.Person)

}

@Composable
fun BottomNavigationBar(
    modifier: Modifier,
    currentPage: Int,
    navBarItems: Array<NavigationBarItems>,
    onItemClick: (page: Int) -> Unit
) {

    AnimatedNavigationBar(
        selectedIndex = currentPage, modifier = modifier
            .padding(24.dp),
        barColor = colorResource(id = R.color.green_fade),
        ballColor = colorResource(id = R.color.green_fade).copy(alpha = 0.7f),
        cornerRadius = ShapeCornerRadius(60f, 60f, 60f, 60f)
    ) {

        navBarItems.forEach { item ->

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .noRippleClickable { onItemClick(item.ordinal) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    tint = if (currentPage == item.ordinal) colorResource(R.color.white) else colorResource(
                        R.color.black
                    )
                )
            }

        }

    }

}