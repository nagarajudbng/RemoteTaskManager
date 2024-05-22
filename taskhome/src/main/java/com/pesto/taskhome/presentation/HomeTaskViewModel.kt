package com.pesto.taskhome.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pesto.core.domain.model.Task
import com.pesto.core.presentation.UiEvent
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
import javax.inject.Inject

@HiltViewModel
class HomeTaskViewModel @Inject constructor(
    private var updateTaskUseCase: UpdateTaskUseCase,
    private var deleteTaskUseCase: DeleteTaskUseCase,
    private var getTaskListUseCase: GetTaskListUseCase,
    private var filterTaskUseCase: FilterTaskUseCase,
    private var searchTaskUseCase: SearchTaskUseCase,
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
                            todoList.value = it
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
                todoList.value = it
            }
        }

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
                        dueDate = event.task.dueDate
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
                    todoList.value = value
                }
            }
        }
    }

}
