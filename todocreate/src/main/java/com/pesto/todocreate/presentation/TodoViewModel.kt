package com.pesto.todocreate.presentation

import androidx.lifecycle.ViewModel
import com.pesto.core.data.source.local.entity.Task
import com.pesto.todocreate.domain.usecase.TodoCreateUseCase
import javax.inject.Inject

class TodoViewModel @Inject constructor(
    private val todoCreateUseCase: TodoCreateUseCase
):ViewModel(){



    fun insert(task: Task){
        todoCreateUseCase.insert(task)
    }


}
