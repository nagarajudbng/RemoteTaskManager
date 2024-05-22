package com.pesto.profile.presentation

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.coil.rememberCoilPainter
import com.pesto.core.presentation.UiEvent
import com.pesto.core.util.ThemeColors
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

//import coil.compose.AsyncImage
//import coil.compose.rememberAsyncImagePainter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onNavigation: (String) -> Unit
) {
    val viewModel = hiltViewModel<ProfileViewModel>()
    //var userProfile by remember { mutableStateOf(userProfile) }
//    val state by viewModel.state.collectAsState()
    var url = viewModel.imageURI.value.uri
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    ProgressDialogBox(viewModel)
    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest {event->
            when(event){
                is UiEvent.ShowSnackBar->{
//                    var uiText = event.uiText
//                    var repose =  when (uiText) {
//                        is UiText.DynamicString -> uiText.value
//                        is UiText.StringResource -> uiText.id
//                    }
//                    if (showSnackBar != null) {
//                        showSnackBar(repose.toString())
//                    }
                }
                is UiEvent.NavigateUp->{
                    onNavigation("Logout")
//                    if(viewModel.isAdmin()) {
//                        var id = "65155432637d335d4e5753c5"
//                        navController?.navigate("${NavigationItem.OnRestaurantBusinessDetail.route}/${id}") {
//                            popUpTo(NavigationItem.Login.route) {
//                                inclusive = true
//                            }
//                        }
//                    } else if(viewModel.isSuperAdmin()){
//                        navController?.navigate(NavigationItem.RestaurantsList.route) {
//                            popUpTo(NavigationItem.Login.route) {
//                                inclusive = true
//                            }
//                        }
//                    }
                }

                else -> {}
            }

        }
    }






    val getContent = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()) { uri: Uri? ->

        uri?.let {
            //Permissions to read the uri
//            context.contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION )

            //userProfile = userProfile.copy(profileImageUri = it)
            viewModel.onEvent(ProfileEvent.EnteredImageURI(it))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Profile Image
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(ThemeColors.buttonsBackgroundColor)
                .clickable {
                    getContent.launch("image/*") // Calls getContent
                },
            contentAlignment = Alignment.Center
        ) {
//            if (state.image != null) {
            Log.d("URl ", url.toString())

                Image(
                    // I replace this line
                    //painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    painter = rememberCoilPainter(
                        request = viewModel.imageURI.value.uri, // or url
                        fadeIn = true // Optionally, you can enable a crossfade animation
                    ),
//                    imageVector = Icons.Filled.AccountBox,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
//            } else {
//                Icon(
//                    imageVector = Icons.Outlined.MailOutline,
//                    contentDescription = null,
//                    modifier = Modifier.size(48.dp),
//                    tint = MaterialTheme.colorScheme.onPrimary
//                )
//            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Name Field
        OutlinedTextField(
            value = viewModel.userName.value.text,
            onValueChange = {
                viewModel.onEvent(ProfileEvent.EnteredUserName(it))
            },
            label = { Text(text = "Name") },
            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Email Field
        OutlinedTextField(
            value = viewModel.email.value.text,
            onValueChange = {
                viewModel.onEvent(ProfileEvent.EnteredEmail(it))
            },
            label = { Text("Email") },
            leadingIcon = { Icon(
                imageVector = Icons.Default.MailOutline,
                contentDescription = null)
                          },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Save Button
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .height(60.dp)
            ) {

                Button(
                    onClick = {
                        viewModel.onEvent(ProfileEvent.Save)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ThemeColors.buttonsBackgroundColor
                    ),
                    modifier = Modifier
                        .width(130.dp)
                        .height(50.dp)
                        .align(Alignment.CenterStart)
                ) {
                    Icon(imageVector = Icons.Default.Done, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Save")
                }
                Button(
                    onClick = {
                        viewModel.onEvent(ProfileEvent.Logout)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ThemeColors.buttonsBackgroundColor
                    ),
                    modifier = Modifier
                        .width(130.dp)
                        .height(50.dp)
                        .align(Alignment.CenterEnd)
                ) {
                    Icon(imageVector = Icons.Default.Done, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Logout")
                }
            }
        }

    }
}


@Composable
fun ProgressDialogBox(viewModel: ProfileViewModel){
    var showDialog = viewModel.dialogState.value
    Log.d("ProgressDialogBox","Show ${showDialog}")
    if (showDialog) {
        DialogTime(viewModel)
        Dialog(
            onDismissRequest = { showDialog = false },
            DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Box(
                contentAlignment= Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
@Composable
fun DialogTime(viewModel: ProfileViewModel){
    var timeLeft by remember { mutableStateOf(3) }

    LaunchedEffect(key1 = timeLeft) {
        while (timeLeft > 0) {
            delay(1000L)
            timeLeft--
            if(timeLeft==0){
                viewModel.onEvent(ProfileEvent.DialogueEvent(false))
            }
        }
    }
}
