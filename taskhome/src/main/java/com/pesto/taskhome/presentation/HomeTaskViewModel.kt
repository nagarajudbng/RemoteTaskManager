package com.pesto.taskhome.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pesto.core.domain.model.ProfileDomain
import com.pesto.core.domain.model.Task
import com.pesto.core.presentation.UiEvent
import com.pesto.profile.domain.usecase.ProfileGetUseCase
import com.single.core.states.StandardTextFieldState
import com.single.todohome.usecases.DeleteTaskUseCase
import com.single.todohome.usecases.FilterTaskUseCase
import com.single.todohome.usecases.GetTaskListUseCase
import com.single.todohome.usecases.SearchTaskUseCase
import com.single.todohome.usecases.UpdateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeTaskViewModel @Inject constructor(
    private var updateTaskUseCase: UpdateTaskUseCase,
    private var deleteTaskUseCase: DeleteTaskUseCase,
    private var getTaskListUseCase: GetTaskListUseCase,
    private var filterTaskUseCase: FilterTaskUseCase,
    private var searchTaskUseCase: SearchTaskUseCase,
    private val profileGetUseCase: ProfileGetUseCase
):ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _todoList = mutableStateOf<List<Task>>(emptyList())
    val todoList = _todoList

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    private val _focusState = mutableStateOf(true)
    val focusState = _focusState

    private val _sortState = mutableStateOf(StandardTextFieldState())
    val sortState = _sortState
    private val _profile = mutableStateOf(ProfileDomain())
    val profile = _profile

    private val _filterState = mutableStateOf(StandardTextFieldState())
    val filterState = _filterState

    private val _topBarState = mutableStateOf(false)
    val topBarState = _topBarState


//    init{
//        getTaskList()
//    }

    fun onSearchEvent(event: SearchEvent) {

        when (event) {
            is SearchEvent.TopSearchSelected -> {
                _topBarState.value = event.selected
            }

            is SearchEvent.OnSearchQuery -> {
                _searchQuery.value = event.query
            }

            is SearchEvent.OnSearchStart -> {
                viewModelScope.launch {
                    _searchQuery.value = event.query
                    searchWithQuery(_searchQuery.value)
                }
            }

            is SearchEvent.OnFocusChange -> {
                focusState.value = event.focus
            }

            is SearchEvent.OnFilter -> {
                viewModelScope.launch {
                    _filterState.value.text = event.query
                    if (_filterState.value.text == "All") {
                        getTaskList()
                    } else {
                        filterTaskUseCase.filter(event.query).collect {
                            _todoList.value = todoListTransformation(it)
                        }
                    }
                }
            }

            is SearchEvent.OnClearPressed -> {
                _searchQuery.value = ""
                _topBarState.value = false
                getTaskList()
            }

        }
    }

    fun getTaskList() {
        viewModelScope.launch {
            getTaskListUseCase.getTaskList().collect {
                _todoList.value = todoListTransformation(it)
            }
        }

    }
    fun todoListTransformation(taskList: List<Task>): List<Task> {
        val updatedList = taskList.map { task ->
            task.copy(isDueDateOver = isDueDateOver(task.dueDate,task.alarmTime))
        }
        return updatedList
    }
    fun onEvent(event: TaskUpdateEvent) {
        when (event) {
            is TaskUpdateEvent.EnteredActionUpdate -> {
                viewModelScope.launch {
                    val task = Task(
                        id = event.task.id,
                        title = event.task.title,
                        description = event.task.description,
                        status = event.action,
                        dueDate = event.task.dueDate,
                        alarmTime = event.task.alarmTime
                    )
                    Log.d("Search update","update "+event.action)
                    if (event.action == "Delete") {
                        deleteTaskUseCase.delete(task)
                    } else {
                        updateTaskUseCase.update(task)
                    }
                    searchWithQuery(_searchQuery.value)
                }
            }

            else -> {}
        }
    }

    private fun searchWithQuery(query: String){
        viewModelScope.launch {
            if (_searchQuery.value.isNotBlank()) {
                searchTaskUseCase.searchQuery(_searchQuery.value).collectLatest { value ->
                    _todoList.value = todoListTransformation(value)
                }
            }
        }
    }

    private fun isDueDateOver(date: String, alarmTime: String): Boolean {
        val dateTimeString = "${date} ${alarmTime}"
        val dateFormat = SimpleDateFormat("EEEE, dd MMMM, yyyy hh:mm a", Locale.getDefault())
        val dateParsed = dateFormat.parse(dateTimeString)
        // Target date
//        val targetDateStr = "Mon May 27 04:20:00 GMT+05:30 2024"
        val targetDateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
        val targetDate = dateParsed//targetDateFormat.parse(dateParsed.toString())

        val currentDate = Date()
        // Convert dates to Unix timestamps
        val currentTimestamp = currentDate.time
        val targetTimestamp = targetDate.time

        // Check if the target date has passed the current date
        val isOverdue = currentTimestamp > targetTimestamp

        // Print results
        println("Current date: $currentDate")
        println("Target date: $targetDate")
        if (isOverdue) {
            return true
        } else {
            return false
        }
    }
  
    fun validateTime(alarmTime: String): Boolean {
        // Parse the alarm time string
        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val alarmDateTime = timeFormat.parse(alarmTime)
        val targetCalendar = Calendar.getInstance().apply {
            time = alarmDateTime
        }

        // Get the current time
        val currentCalendar = Calendar.getInstance()

        // Set the year, month, and day to the current date to ensure only time comparison
        targetCalendar.apply {
            set(Calendar.YEAR, currentCalendar.get(Calendar.YEAR))
            set(Calendar.MONTH, currentCalendar.get(Calendar.MONTH))
            set(Calendar.DAY_OF_MONTH, currentCalendar.get(Calendar.DAY_OF_MONTH))
        }

        // Compare the time only
        return currentCalendar.timeInMillis > targetCalendar.timeInMillis
    }
    fun getProfile(){
        viewModelScope.launch {
            val profileDomain = profileGetUseCase()
            _profile.value = profileDomain
        }
    }
}
