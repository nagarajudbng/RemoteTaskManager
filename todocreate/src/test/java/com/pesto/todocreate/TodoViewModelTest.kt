package com.pesto.todocreate

import com.pesto.core.data.source.local.entity.Task
import com.pesto.todocreate.domain.usecase.TodoCreateUseCase
import com.pesto.todocreate.presentation.TodoViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations


// Created by Nagaraju on 13/05/24.

class TodoViewModelTest {

    @Mock
    private lateinit var todoCreateUseCase: TodoCreateUseCase

    @InjectMocks
    private lateinit var todoViewModel: TodoViewModel

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun insertTaskTest(): Unit = runBlocking {
        val task = Task(id = 1, title = "Task1", description = "Description1", status = "TO DO")

        todoViewModel.insert(task)
    }
}