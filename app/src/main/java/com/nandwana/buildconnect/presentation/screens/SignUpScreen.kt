package com.nandwana.buildconnect.presentation.screens

import android.util.Log
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.nandwana.buildconnect.R
import com.nandwana.buildconnect.data.model.TemporaryUser
import com.nandwana.buildconnect.data.model.UserModel
import com.nandwana.buildconnect.presentation.navigation.Screens
import com.nandwana.buildconnect.presentation.viewmodels.SignUpViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.sign

@Composable
fun SignUpScreen(navHostController:NavHostController) {

    val signUpViewModel = hiltViewModel<SignUpViewModel>()
    val context = LocalContext.current
    val signUpState = signUpViewModel.userRegister.collectAsStateWithLifecycle().value
    val emailSubmitted = signUpViewModel.emailSubmitted.collectAsStateWithLifecycle().value
    val isOtpVerified = signUpViewModel.isOtpVerified.collectAsStateWithLifecycle().value

    if(signUpState.userRegisterSuccess) {
        Toast.makeText(context,"User Logged In Successfully",Toast.LENGTH_SHORT).show()
        navHostController.popBackStack()
        signUpState.userRegisterSuccess = false
        navHostController.navigate(Screens.HomeScreen.route)
    }

    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repassword by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }

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
                        "Create your account",
                        fontSize = 18.sp,
                        fontFamily = lateef_regular_font,
                        modifier = Modifier.align(Alignment.Start)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    SignUpTextField(text = email, hint = "Enter Email", onValueChange = { email = it })
                    if(emailSubmitted.emailSubmissionSuccess){
                        SignUpTextField(text = otp, hint = "Enter Otp", onValueChange = {otp = it})
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    if(isOtpVerified.otpVerificationSuccess){
                        SignUpTextField(text = name, hint = "Enter Name", onValueChange = { name = it })
                        Spacer(modifier = Modifier.height(16.dp))
                        SignUpTextField(text = password, hint = "Enter Password", onValueChange = { password = it })
                        Spacer(modifier = Modifier.height(16.dp))
                        SignUpTextField(text = repassword, hint = "Re-Enter Password", onValueChange = { repassword = it })
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    Button(
                        modifier = Modifier
                            .height(64.dp)
                            .fillMaxWidth(),
                        onClick ={

                            if (email.isNotEmpty() && !emailSubmitted.emailSubmissionSuccess) {
                                CoroutineScope(Dispatchers.IO).launch {
                                    signUpViewModel.submitEmail(TemporaryUser(email= email,otp=""))
                                }
                            }
                            else if(otp.isNotEmpty() && emailSubmitted.emailSubmissionSuccess && !isOtpVerified.otpVerificationSuccess) {
                                CoroutineScope(Dispatchers.IO).launch {
                                    signUpViewModel.verifyOtp(TemporaryUser(email= email,otp=otp))
                                }
                            }else if(emailSubmitted.emailSubmissionSuccess && isOtpVerified.otpVerificationSuccess && name.isNotEmpty() && password.isNotEmpty() && repassword.isNotEmpty() && (password == repassword)){

                                CoroutineScope(Dispatchers.IO).launch {
                                    signUpViewModel.registerUser(UserModel(otp = "", name = name, email = email, password = password))

                                }



                            }
                            else {
                                Toast.makeText(context, "Please fill all the required fields", Toast.LENGTH_LONG)
                                    .show()
                            }

                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.green_fade),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text=if(!emailSubmitted.emailSubmissionSuccess)"Get Otp";else if (!isOtpVerified.otpVerificationSuccess) "Verify Otp"; else "Sign Up",
                            fontSize = 18.sp,
                            fontFamily = lateef_regular_font,
                            fontWeight = FontWeight.Thin,
                            letterSpacing = 2.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text="-or sign up with-",
                        fontSize = 18.sp,
                        fontFamily = lateef_regular_font,
                        fontWeight = FontWeight.Thin,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {  },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.white),
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(12.dp),
                        border = ButtonDefaults.outlinedButtonBorder
                    ) {
                        Image(painter = painterResource(R.drawable.google_icon), contentDescription = "")
                        Spacer(modifier = Modifier.width(16.dp))
                        Text("Sign up with Google")
                    }

                    Spacer(modifier = Modifier.height(54.dp))

                }


            }


        }
    }

}

@Composable
fun SignUpTextField(modifier: Modifier = Modifier, text: String,hint:String, onValueChange: (String) -> Unit) {

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .height(50.dp)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            ),
        singleLine = true,
        value = text,
        placeholder = {
            Text(
                modifier=Modifier.padding(bottom = 2.dp),
                text=hint,
                fontSize = 16.sp,
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