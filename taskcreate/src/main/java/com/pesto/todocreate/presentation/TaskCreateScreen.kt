package com.pesto.todocreate.presentation

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.pesto.core.domain.model.ProfileDomain
import com.pesto.core.presentation.UiEvent
import com.pesto.core.presentation.AppBar
import com.pesto.core.presentation.CustomDropDownMenu
import com.pesto.core.presentation.DatePickerWithDialog
import com.pesto.core.presentation.TimePickerWithDialog
import com.pesto.core.presentation.asString
import com.pesto.core.util.AlarmReceiver
import com.pesto.core.util.ThemeColors
import com.pesto.todocreate.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.util.Locale

// Created by Nagaraju Deshetty on 07/05/


@Composable
@Preview
fun TaskCreateScreenPreview() {
//    TaskCreateScreen(
//        onNavigation = {},onSnackBarMessage={
//
//    })
}
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCreateScreen(
    onNavigation: (String) -> Unit,
    onSnackBarMessage:(String)->Unit
) {
    val viewModel = hiltViewModel<TaskViewModel>()
    val  context = LocalContext.current
    ProgressDialogBox(viewModel = viewModel)
    setAlarm(context,viewModel)
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.NavigateUp -> {
                    onNavigation("Back")
                }
                is UiEvent.ShowSnackBar -> {
                    onSnackBarMessage(event.uiText.asString(context))
                }
                is UiEvent.Message -> {
                }
                else -> {}
            }

        }
    }
    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(id = R.string.create_task_title),
                searchClick = {},
                backClick = {},
                isSearchEnable = false,
                isFilterEnable = false,
                isProfileEnable = false,
                filter = {},
                gotoProfile = {},
                profile = ProfileDomain()

            )
        }
    ) { it ->
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
                    if (viewModel.titleState.value.error != null) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = viewModel.titleState.value.error!!,
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
                    if (viewModel.descState.value.error !=null) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = viewModel.descState.value.error!!,
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
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.dateSelectedState.value.text,
                onValueChange = {
                    viewModel.dateSelectedState.value.text = it
                },
                readOnly = true,
                enabled = false,
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledContainerColor = Color.Transparent,
                    disabledBorderColor = MaterialTheme.colorScheme.outline,
                    disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledPrefixColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledSuffixColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),

                maxLines= 1,
                textStyle = TextStyle(
                    color = Black,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Light,
                    fontSize = with(LocalDensity.current) { 14.sp }

                ),

                trailingIcon = {
                    DatePickerWithDialog(
                        onSelected = {
//                            viewModel.dateSelectedState.value.text = it
                            viewModel.onEvent(TaskEvent.EnteredDueDate(it))
                        })
                },
                supportingText = {
                    if (viewModel.dateSelectedState.value.error  != null) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = viewModel.dateSelectedState.value.error!!,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.timeSelectedState.value.text,
                onValueChange = {
                    viewModel.timeSelectedState.value.text = it
                },
                readOnly = true,
                enabled = false,
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledContainerColor = Color.Transparent,
                    disabledBorderColor = MaterialTheme.colorScheme.outline,
                    disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledPrefixColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledSuffixColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),

                maxLines= 1,
                textStyle = TextStyle(
                    color = Black,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Light,
                    fontSize = with(LocalDensity.current) { 14.sp }

                ),

                trailingIcon = {
                    TimePickerWithDialog(
                        onSelected = {
                            viewModel.onEvent(TaskEvent.EnteredAlarmTime(it))
                        })
                },
                supportingText = {
                    if (viewModel.timeSelectedState.value.error  != null) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = viewModel.timeSelectedState.value.error!!,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    onClick = {
                        viewModel.onEvent(TaskEvent.AddTask)
//                      viewModel.generateRandomTask()

                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ThemeColors.buttonsBackgroundColor
                    )
                ) {
                    Text(text = "ADD TODO")
                }
            }
        }

    }
}
fun setAlarm(context: Context,viewModel: TaskViewModel) {
    val alarmState = viewModel.alarmState.value
    if(alarmState) {
        val dateTimeString = "${viewModel.dateSelectedState.value.text} ${viewModel.timeSelectedState.value.text}"
        Log.d("dateTimeString",dateTimeString)
        val dateFormat = SimpleDateFormat("EEEE, dd MMMM, yyyy hh:mm a", Locale.getDefault())
        Log.d("dataTimeString format = ",dateFormat.format(dateFormat.parse(dateTimeString)))
        val alarmDateTime = dateFormat.parse(dateTimeString)

        // Set up AlarmManager
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
//        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)


        // Set the alarm
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmDateTime.time, pendingIntent)
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


