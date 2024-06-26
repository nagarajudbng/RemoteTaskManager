package com.pesto.core.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import com.google.accompanist.coil.rememberCoilPainter
import com.pesto.core.R
import com.pesto.core.domain.model.ProfileDomain
import com.pesto.core.util.ThemeColors


// Created by Nagaraju Deshetty on 07/05/24.


const val searchBoxHeight = 60f
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String,
    modifier: Modifier = Modifier,
    searchClick: () -> Unit,
    backClick: () -> Unit,
    filter:(String) ->Unit,
    isSearchEnable: Boolean,
    isFilterEnable:Boolean,
    isProfileEnable:Boolean,
    gotoProfile:(String)->Unit,
    profile: ProfileDomain
) {
        TopAppBar(
            modifier = modifier
                .heightIn(searchBoxHeight.dp),

            navigationIcon = {
                Box(modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 5.dp)) {
                    if(isProfileEnable) {

//                        Image(
//                            // I replace this line
//                            //painter = painterResource(id = R.drawable.ic_launcher_foreground),
//                            painter = rememberCoilPainter(
//                                request = viewModel.imageURI.value.uri, // or url
//                                fadeIn = true // Optionally, you can enable a crossfade animation
//                            ),
////                    imageVector = Icons.Filled.AccountBox,
//                            contentDescription = null,
//                            contentScale = ContentScale.Crop,
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .clip(CircleShape)
//                        )
                        Image(

                            painter = rememberCoilPainter(
                                request = profile.image, // or url
                                fadeIn = true // Optionally, you can enable a crossfade animation
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .fillMaxWidth()
                                .align(Alignment.CenterStart)
                                .clickable {
                                    gotoProfile("Profile")
                                }
                                .border(
                                    BorderStroke(
                                        6.dp,
                                        Brush.sweepGradient(
                                            listOf(
                                                Color(0xFF76C868),
                                                Color(0xFF76C868)
                                            )
                                        )
                                    ),
                                    CircleShape
                                )
                        )
                    }
                }
            },
            title = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 10.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = title,
                        modifier = Modifier,
                        textAlign = TextAlign.Center,
                        color = White,
                        style = MaterialTheme.typography.bodyMedium,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Light,
                        fontSize = with(LocalDensity.current) { 16.sp },

                        )
                }
            },

            colors = TopAppBarDefaults.smallTopAppBarColors(ThemeColors.statusBarColor),
            actions = {
                Row(
                    modifier=Modifier.fillMaxHeight().padding(end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                )
                {

                    if (isSearchEnable) {
                        Box(
                            modifier = Modifier
                        ) {
                            IconButton(onClick = searchClick) {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = "",
                                    tint = White
                                )
                            }
                        }
                    }
                    if(isFilterEnable){
                        Box(
                            modifier = Modifier
                        ) {
                            PopUpMenuButton(
                                modifier = Modifier.align(Alignment.Center),
                                options = popUpMenu,
                                imageVector = ImageVector.vectorResource(id = R.drawable.outline_filter_list_24),
                                action = {
                                    filter(it)
                                },
                                iconTint = Color.Black
                            )
                        }
                    }
                }
            }


        )
}

@Preview
@Composable
fun HomeAppBarPreview() {
    AppBar(
        title = "Romantic Comedy",
        searchClick = { },
        backClick = { },
        filter = {},
        isSearchEnable = false,
        isFilterEnable = false,
        isProfileEnable = false,
        gotoProfile = {},
        profile = ProfileDomain()
    )
}