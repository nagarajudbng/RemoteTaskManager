package com.pesto.todocreate.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.pesto.core.presentation.UiEvent
import com.pesto.core.domain.states.FieldStatus
import com.pesto.core.presentation.AppBar
import com.pesto.core.presentation.CustomDropDownMenu
import com.pesto.core.presentation.asString
import com.pesto.todocreate.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

// Created by Nagaraju Deshetty on 07/05/


@Composable
@Preview
fun TaskCreateScreenPreview() {
    TaskCreateScreen(
        onNavigation = {},onSnackBarMessage={

    })
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCreateScreen(
    onNavigation: (String) -> Unit,
    onSnackBarMessage:(String)->Unit
) {
    var viewModel = hiltViewModel<TaskViewModel>()
    var  context = LocalContext.current
    ProgressDialogBox(viewModel = viewModel)
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.NavigateUp -> {
//                    sharedViewModel.messageState.value = event.message
                    onNavigation("Back")
                }
                is UiEvent.ShowSnackBar -> {
                    onSnackBarMessage(event.uiText.asString(context))
                }
                is UiEvent.Message -> {
//                    sharedViewModel.messageState.value = event.message
                }
                else -> {}
            }

        }
    }
    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(id = R.string.create_task_title),
                searchClick = {
                },
                backClick = {},
                isSearchEnable = false
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding(),
                    start = 16.dp,
                    end = 16.dp
                )
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.titleState.value.text,
                onValueChange = {
                    viewModel.onEvent(TaskEvent.EnteredTitle(it))
                },

                textStyle = TextStyle(
                    color = Black,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Light,
                    fontSize = with(LocalDensity.current) { 14.sp }

                ),
                maxLines = 1,
                label = {
                    Text(
                        text = "Title",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Light,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,

                        )
                },
                supportingText = {
                    if (viewModel.titleState.value.error == FieldStatus.FieldEmpty) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Title Required",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.descState.value.text,
                onValueChange = {
                    viewModel.onEvent(TaskEvent.EnteredDescription(it))
                },
                maxLines= 1,
                textStyle = TextStyle(
                    color = Black,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Light,
                    fontSize = with(LocalDensity.current) { 14.sp }

                ),
                label = {
                    Text(
                        text = "Description",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Light,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,

                        )
                },
                supportingText = {
                    if (viewModel.descState.value.error  == FieldStatus.FieldEmpty) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Description Required",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomDropDownMenu(
                modifier = Modifier,
                label = "Jell",
                selected = viewModel.statusState.value.text,
                list = listOf("Done","To Do","In Progress",),
                errorStatus = viewModel.statusState.value.error,
                onSelected = {
                    viewModel.onEvent(TaskEvent.EnteredStatus(it))
                }
            )
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth()
                        .padding(bottom = 16.dp),
                    onClick = {
                        viewModel.onEvent(TaskEvent.AddTask)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF396803)
                    )
                ) {
                    Text(text = "ADD TODO")
                }
            }
        }

    }
}

@Composable
fun ProgressDialogBox(viewModel: TaskViewModel){
    var showDialog = viewModel.dialogState.value

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
                    .background(White, shape = RoundedCornerShape(8.dp))
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
@Composable
fun DialogTime(viewModel: TaskViewModel){
    var timeLeft by remember { mutableStateOf(3) }

    LaunchedEffect(key1 = timeLeft) {
        while (timeLeft > 0) {
            delay(1000L)
            timeLeft--
            if(timeLeft==0){
                viewModel.onEvent(TaskEvent.DialogueEvent(false))
            }
        }
    }
}


