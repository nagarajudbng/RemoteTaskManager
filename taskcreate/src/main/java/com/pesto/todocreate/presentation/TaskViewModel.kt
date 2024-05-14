package com.pesto.todocreate.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pesto.core.domain.model.Task
import com.pesto.core.domain.states.TaskResult
import com.pesto.core.presentation.UiEvent
import com.pesto.todocreate.domain.usecase.TaskCreateUseCase
import com.single.core.states.StandardTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskCreateUseCase: TaskCreateUseCase
) : ViewModel() {


    private val _dateState = mutableStateOf(false)
    val dateState = _dateState

    private val _dateSelectedState = mutableStateOf(StandardTextFieldState())
    val dateSelectedState = _dateSelectedState

    private val _titleState = mutableStateOf(StandardTextFieldState())
    val titleState = _titleState

    private val _descState = mutableStateOf(StandardTextFieldState())
    val descState = _descState

    private val _statusState = mutableStateOf(StandardTextFieldState("Select"))
    var statusState: State<StandardTextFieldState> = _statusState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _dialogState = mutableStateOf(false)
    val dialogState = _dialogState

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery


    fun onEvent(event: TaskEvent) {
        when (event) {

            is TaskEvent.EnteredTitle -> {
                _titleState.value = titleState.value.copy(
                    text = event.title
                )
            }

            is TaskEvent.EnteredDescription -> {
                _descState.value = descState.value.copy(
                    text = event.description
                )
            }

            is TaskEvent.EnteredStatus -> {
                _statusState.value = statusState.value.copy(
                    text = event.status
                )
            }

            is TaskEvent.DialogueEvent -> {
                _dialogState.value = event.isDismiss
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.NavigateUp("Finished"))
                }

            }

            is TaskEvent.AddTask -> {
                viewModelScope.launch {

                    var task = Task(
                        title = _titleState.value.text,
                        description = _descState.value.text,
                        status = _statusState.value.text
                    )
                    var taskResult = TaskResult()
                    taskResult = taskCreateUseCase.validate(task)


                    if (!taskResult.isValid) {
                        _descState.value = descState.value.copy(
                            error = taskResult.description
                        )
                        _titleState.value = titleState.value.copy(
                            error = taskResult.title
                        )
                        _statusState.value = statusState.value.copy(
                            error = taskResult.status
                        )
                    } else {
                        dialogState.value = true
                        taskResult = taskCreateUseCase.insert(task = task)
                        taskResult.result?.let {
                            if (it > 0) {
//                                _dialogState.value = true
                            }
                        }
                    }

                }
            }
        }

    }

    suspend fun insert(task: Task) {
        viewModelScope.launch {
            taskCreateUseCase.insert(task)
        }

    }

}
