package com.nandwana.buildconnect.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.axis.AxisData
import co.yml.charts.axis.DataCategoryOptions
import co.yml.charts.common.model.Point
import co.yml.charts.common.utils.DataUtils
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData
import co.yml.charts.ui.barchart.models.BarChartType
import co.yml.charts.ui.barchart.models.BarStyle
import com.nandwana.buildconnect.R
import com.nandwana.buildconnect.data.model.project_models.MaterialModel
import com.nandwana.buildconnect.data.model.project_models.ProjectModel
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MaterialsScreen(
    project:ProjectModel
) {

    val steps = 5

    val pointsData = listOf(
        Point(0f, 40f),
        Point(1f, 90f),
        Point(2f, 0f),
        Point(3f, 60f),
        Point(4f, 10f),
        Point(4f, 10f),
        Point(4f, 10f),
        Point(4f, 10f),
        Point(4f, 10f),
        Point(4f, 10f),
        Point(4f, 10f),
        Point(4f, 10f),
    )

    val xAxisData = AxisData.Builder()
        .axisStepSize(30.dp)
        .steps(pointsData.size - 1)
        .bottomPadding(20.dp)
        .startDrawPadding(40.dp)
        .endPadding(24.dp)
        .labelData { i ->
            when (i) {
                0 -> "  January  "
                1 -> "  February  "
                2 -> "  March  "
                3 -> "  April  "
                4 -> "  May  "
                else -> "  January  "
            }
        }
        .labelAndAxisLinePadding(25.dp)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(steps)
        .labelAndAxisLinePadding(20.dp)
        .axisOffset(20.dp)
        .labelData { i ->
            val yScale = 100 / steps
            (i * yScale).toString()
        }
        .build()

    val barChartData = DataUtils.getBarChartData(
        barChartType = BarChartType.VERTICAL, listSize = pointsData.size, maxRange = 5,
        dataCategoryOptions = DataCategoryOptions()
    )


    val BarChartData = BarChartData(
        chartData = barChartData,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        barStyle = BarStyle(
            barWidth = 30.dp,
            paddingBetweenBars = 24.dp,
        ),
        backgroundColor = Color.Transparent
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text("Materials & Financial", fontSize = 34.sp, fontFamily = lateef_regular_font)

        Row(
            modifier = Modifier
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ){
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .height(120.dp)
                    .weight(0.5f)
                    .border(
                        1.dp,
                        colorResource(R.color.green_fade),
                        shape = RoundedCornerShape(8.dp)
                    ),

            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = "Total Budget ",
                        fontSize = 24.sp,
                        fontFamily = lateef_regular_font
                    )
                    Text(
                        text = "$ ${project.estimatedExpenditure.toFloat().toInt()}",
                        fontSize = 30.sp,
                        fontFamily = lateef_regular_font
                    )
                }
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .height(120.dp)
                    .weight(0.5f)
                    .border(
                        1.dp,
                        colorResource(R.color.green_fade),
                        shape = RoundedCornerShape(8.dp)
                    )

            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = "Spent",
                        fontSize = 24.sp,
                        fontFamily = lateef_regular_font
                    )

                    val spent_color = if(project.totalExpenditureNow<project.estimatedExpenditure) Color.Green else Color.Red

                    Text(
                        text = "$ ${project.totalExpenditureNow.toFloat().toInt()}",
                        fontSize = 30.sp,
                        fontFamily = lateef_regular_font,
                        color = spent_color
                    )

                    val total_percentage = ((project.totalExpenditureNow.toFloat() / project.estimatedExpenditure.toFloat()).times(100f)).absoluteValue

                    Text(
                        text = "${total_percentage.toInt()} % of budget",
                        fontSize = 18.sp,
                        fontFamily = lateef_regular_font
                    )

                }
            }
        }


        Row(
            modifier = Modifier
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ){
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .height(120.dp)
                    .weight(0.5f)
                    .border(
                        1.dp,
                        colorResource(R.color.green_fade),
                        shape = RoundedCornerShape(8.dp)
                    ),

                ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = "Total Staff",
                        fontSize = 24.sp,
                        fontFamily = lateef_regular_font
                    )
                    Text(
                        text = " ${project.manpower.totalWorkers}",
                        fontSize = 30.sp,
                        fontFamily = lateef_regular_font
                    )
                }
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .height(120.dp)
                    .weight(0.5f)
                    .border(
                        1.dp,
                        colorResource(R.color.green_fade),
                        shape = RoundedCornerShape(8.dp)
                    )

            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = "On Duty",
                        fontSize = 24.sp,
                        fontFamily = lateef_regular_font
                    )

                    Text(
                        text = " ${project.manpower.totalWorkers}",
                        fontSize = 30.sp,
                        fontFamily = lateef_regular_font
                    )

                    val total_percentage = ((project.totalExpenditureNow.toFloat() / project.estimatedExpenditure.toFloat()).times(100f)).absoluteValue

                    Text(
                        text = " ${project.manpower.workHours} hours/day",
                        fontSize = 18.sp,
                        fontFamily = lateef_regular_font,

                    )

                }
            }
        }


        Text("Project Spending", fontSize = 24.sp, fontFamily = lateef_regular_font)

        BarChart(
            barChartData = BarChartData,
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
        )

        Text("Resource Overview", fontSize = 24.sp, fontFamily = lateef_regular_font)


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {

            LazyColumn {
                stickyHeader {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colorResource(R.color.skin_color))
                            .padding(8.dp)
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {

                        Text(
                            modifier = Modifier
                                .weight(0.2f),
                            textAlign = TextAlign.Center,
                            text = "Material Name"
                        )
                        Text(
                            modifier = Modifier
                                .weight(0.6f),
                            textAlign = TextAlign.Start,
                            text = "Quantity"
                        )
                        Text(
                            modifier = Modifier
                                .weight(0.2f),
                            textAlign = TextAlign.Center,
                            text = "Cost"
                        )

                    }
                }

                items(project.materials.size) {
                    MaterialItem(project.materials[it])
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.Black))
                }

            }


        }


    }

}

@Composable
fun MaterialItem(material: MaterialModel) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        //Material Name
        Text(
            text = material.name,
            modifier = Modifier
                .weight(0.2f),
            softWrap = true,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis
        )

        //Quantity of Materials

        Column(
            horizontalAlignment = Alignment.Start, modifier = Modifier.weight(0.6f)
        ) {
            Text(text = "${material.remainingQuantity.toInt()}/${material.totalQuantity.toInt()} units")
            val progress = material.remainingQuantity/ material.totalQuantity
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                progress = { progress.toFloat() },
                color = Color.Green,
                trackColor = Color.Red
            )
        }

        //Cost of Material
        Text(
            text = "$ ${material.totalCost.toInt()}",
            modifier = Modifier
                .weight(0.2f),
            softWrap = true,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis
        )


    }


}
