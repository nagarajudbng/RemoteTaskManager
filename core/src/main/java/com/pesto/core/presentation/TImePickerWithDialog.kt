package com.pesto.core.presentation

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.room.util.joinIntoString
import com.pesto.core.util.DateUtils
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


// Created by Nagaraju on 23/05/24.

@SuppressLint("RestrictedApi")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerWithDialog(
    onSelected:(String)->Unit,
    modifier: Modifier = Modifier,
    showModeToggle: Boolean = false,
) {
    var selectedTime by remember { mutableStateOf("") }
    val timePickerState = rememberTimePickerState()
    val formatter = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }

    var showTimePicker by remember { mutableStateOf(false) }
    Column {
//        Button(onClick = { showDialog = true }) {
//            Text("Select Time")
//        }
        if(!showTimePicker) {
            IconButton(onClick = {
                showTimePicker = true
                Log.d("TImePicker","Clicked")
            }) {
                Icon(imageVector = Icons.Filled.Notifications, contentDescription = "")
            }
        }
        Column(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            if (showTimePicker) {

                DatePickerDialog(
                    onDismissRequest = { showTimePicker = false },
                    confirmButton = {
                        Button(
                            onClick = {
                                showTimePicker = false
                                val cal = Calendar.getInstance()
                                cal.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                                cal.set(Calendar.MINUTE, timePickerState.minute)
                                cal.isLenient = false

                                Log.d("Timepicker ",formatter.format(cal.time) )
                            onSelected(formatter.format(cal.time))
                            }
                        ) {
                            Text(text = "OK")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = { showTimePicker = false }
                        ) {
                            Text(text = "Cancel")
                        }
                    }
                ) {
                    TimePicker(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        state = timePickerState,
                    )
                }
            }
        }
//        Text("Selected Time: $selectedTime")
    }
}