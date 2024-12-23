package com.nandwana.buildconnect.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.nandwana.buildconnect.R
val lateef_regular_font = FontFamily(Font(R.font.lateef_regular))
@Composable
fun LoginScreen(navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    Scaffold(modifier = Modifier.fillMaxSize()) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.skin_color))
                .padding(
                    top = it.calculateTopPadding(),
                    start = it.calculateStartPadding(LayoutDirection.Ltr),
                    end = it.calculateEndPadding(LayoutDirection.Rtl)
                )
        ) {
            val maxHeight = maxHeight
            val maxWidth = maxWidth


            Image(
                modifier = Modifier
                    .padding(top = maxHeight / 24)
                    .align(Alignment.TopCenter),
                painter = painterResource(R.drawable.undraw_my_password),
                contentDescription = ""
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(topStart = 54.dp, topEnd = 54.dp))
                    .background(Color.White)
                    ,
                contentAlignment = Alignment.Center
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth().padding(start = 54.dp, end = 54.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text="Build Connect",
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = lateef_regular_font,
                        color = colorResource(R.color.green_fade)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        "Login to your account",
                        fontSize = 18.sp,
                        fontFamily = lateef_regular_font,
                        modifier = Modifier.align(Alignment.Start)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    LoginTextField(text = username, hint = "Username", onValueChange = { username = it })
                    Spacer(modifier = Modifier.height(16.dp))
                    LoginTextField(text = password, hint = "Password", onValueChange = { password = it })
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        modifier = Modifier
                            .height(64.dp)
                            .fillMaxWidth(),
                        onClick ={
                            Toast.makeText(context,"Login Successful",Toast.LENGTH_SHORT).show()
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.green_fade),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text="Login",
                            fontSize = 18.sp,
                            fontFamily = lateef_regular_font,
                            fontWeight = FontWeight.Thin,
                            letterSpacing = 2.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text="-or sign in with-",
                        fontSize = 18.sp,
                        fontFamily = lateef_regular_font,
                        fontWeight = FontWeight.Thin,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { TODO() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.white),
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(12.dp),
                        border = ButtonDefaults.outlinedButtonBorder
                    ) {
                        Image(painter = painterResource(R.drawable.google_icon), contentDescription = "")
                        Spacer(modifier = Modifier.width(16.dp))
                        Text("Sign in with Google")
                    }

                    Spacer(modifier = Modifier.height(54.dp))

                }


            }


        }
    }


}

@Composable
fun LoginTextField(modifier: Modifier = Modifier, text: String,hint:String, onValueChange: (String) -> Unit) {

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .height(68.dp)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            ),
        singleLine = true,
        value = text,
        placeholder = {
            Text(
                text=hint,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = lateef_regular_font,
                color = colorResource(R.color.green_fade).copy(alpha = 0.6f)
            )
        },
        onValueChange = onValueChange,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = colorResource(R.color.white),
            unfocusedContainerColor = colorResource(R.color.white),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
    )

}