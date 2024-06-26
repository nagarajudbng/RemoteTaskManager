package com.pesto.todocreate.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pesto.core.domain.model.Task
import com.pesto.core.domain.states.TaskResult
import com.pesto.core.presentation.UiEvent
import com.pesto.core.presentation.Validations
import com.pesto.todocreate.domain.usecase.AlarmTimeValidationUseCase
import com.pesto.todocreate.domain.usecase.DescriptionValidationUseCase
import com.pesto.todocreate.domain.usecase.DueDateValidationUseCase
import com.pesto.todocreate.domain.usecase.StatusValidationUseCase
import com.pesto.todocreate.domain.usecase.TaskCreateUseCase
import com.pesto.todocreate.domain.usecase.TitleValidationUseCase
import com.pesto.todocreate.domain.util.InputStatus
import com.single.core.states.StandardTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random


@HiltViewModel
class TaskViewModel @Inject constructor(
    private val titleValidationUseCase: TitleValidationUseCase,
    private val descriptionValidationUseCase: DescriptionValidationUseCase,
    private val statusValidationUseCase: StatusValidationUseCase,
    private val dueDateValidationUseCase: DueDateValidationUseCase,
    private val alarmTimeValidationUseCase: AlarmTimeValidationUseCase,
    private val taskCreateUseCase: TaskCreateUseCase
) : ViewModel() {


    private val _dateState = mutableStateOf(false)
    val dateState = _dateState

    private val _dateSelectedState = mutableStateOf(StandardTextFieldState("Due date"))
    val dateSelectedState = _dateSelectedState

    private val _timeSelectedState = mutableStateOf(StandardTextFieldState("Alarm Time"))
    val timeSelectedState = _timeSelectedState

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

    private val _alarmState = mutableStateOf(false)
    val alarmState = _alarmState

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
            is TaskEvent.EnteredDueDate -> {
                _dateSelectedState.value = dateSelectedState.value.copy(
                    text = event.date
                )
            }
            is TaskEvent.EnteredAlarmTime ->{
                _timeSelectedState.value = timeSelectedState.value.copy(
                    text = event.time
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
                    val titleResult = titleValidationUseCase(_titleState.value.text)
                    var errorMessage:String = when(titleResult){
                        InputStatus.EMPTY-> "Title Required"
                        InputStatus.LENGTH_TOO_SHORT-> "Title Minimum 10 Characters length"
                        else -> {""}
                    }
                    _titleState.value = titleState.value.copy(
                        error = errorMessage
                    )


                    val descriptionResult = descriptionValidationUseCase(_descState.value.text)
                    errorMessage = when(descriptionResult){
                        InputStatus.EMPTY-> "Description Required"
                        InputStatus.LENGTH_TOO_SHORT-> "Description Too Short"
                        else -> {""}
                    }
                    _descState.value = descState.value.copy(
                        error = errorMessage
                    )


                    val statusResult = statusValidationUseCase(_statusState.value.text)
                    errorMessage = when(statusResult){
                        InputStatus.EMPTY-> "Status Required"
                        else -> {""}
                    }
                    _statusState.value = statusState.value.copy(
                        error = errorMessage
                    )

                    val dueDateResult = dueDateValidationUseCase(_dateSelectedState.value.text)
                    errorMessage = when(dueDateResult){
                        InputStatus.EMPTY-> "Due Date Required"
                        else -> {""}
                    }
                    _dateSelectedState.value = dateSelectedState.value.copy(
                        error = errorMessage
                    )

                    val alarmResult = alarmTimeValidationUseCase(_timeSelectedState.value.text)
                    errorMessage = when(alarmResult){
                        InputStatus.EMPTY-> "Alarm Time Required"
                        else -> {""}
                    }
                    _timeSelectedState.value = timeSelectedState.value.copy(
                        error = errorMessage
                    )




                    if(titleResult == InputStatus.VALID
                        && descriptionResult == InputStatus.VALID
                        && statusResult == InputStatus.VALID
                        && dueDateResult == InputStatus.VALID
                        && alarmResult == InputStatus.VALID
                        ){

                        val task = Task(
                            title = _titleState.value.text,
                            description = _descState.value.text,
                            status = _statusState.value.text,
                            dueDate = _dateSelectedState.value.text,
                            alarmTime = _timeSelectedState.value.text
                        )

                        dialogState.value = true
                        taskCreateUseCase.insert(task)
                        alarmState.value = true
//                        generateRandomTask()
//                        val taskResult = taskCreateUseCase.insert(task = task)
//                        taskResult.result?.let {
//                            if (it > 0) {
//                            }
//                        }
                    }

                }
            }
        }

    }

     private fun insert(task: Task) {
        viewModelScope.launch {
            taskCreateUseCase.insert(task)
        }
    }

    fun generateRandomTask(){
        val list = listOf(
            "Hit the gym",
            "Pay bills",
            "Meet George",
            "Buy eggs",
            "Read a book",
            "Organize office",
            "Write in journal",
            "Meditate (repeat daily)",
            "Floss teeth (repeat daily)",
            "Build a morning routine"
        )
        val dateList = listOf(
            "Thursday, 16 May, 2024",
            "Thursday, 5 May, 2024",
            "Thursday, 15 July, 2024",
            "Thursday, 20 May, 2024",
            "Thursday, 23 May, 2024",
            "Thursday, 24 May, 2024",
            "Thursday, 25 May, 2024",
            "Thursday, 26 May, 2024",
            "Thursday, 27 May, 2024"
        )
        val timeList = listOf(
            "09:15 PM",
            "07:30 AM",
            "08:45 PM",
            "04:00 PM",
            "04:15 AM",
            "04:30 PM",
            "04:45 PM",
            "05:00 AM",
            "05:00 PM",
        )
        val statusList = listOf("To Do","In Progress","Done")
        for(i in 0..20){
            val task = Task(title = list.get(Random.nextInt(list.size-1)),description = list.random(), status = statusList.get(Random.nextInt(statusList.size)), dueDate = dateList.random(), alarmTime = timeList.random())
            insert(task)
        }
    }
}
