package com.pesto.todocreate

import com.pesto.core.domain.model.Task
import com.pesto.todocreate.domain.usecase.TaskCreateUseCase
import com.pesto.todocreate.presentation.TaskViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


// Created by Nagaraju on 13/05/24.

class TaskViewModelTest {

    @Mock
    private lateinit var todoCreateUseCase: TaskCreateUseCase

    @InjectMocks
    private lateinit var todoViewModel: TaskViewModel

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun insertTaskTest(): Unit = runBlocking {
        val task = Task(id = 1, title = "Task1", description = "Description1", status = "TO DO")

        todoViewModel.insert(task)
        verify(todoCreateUseCase).insert(task)
    }
//    @Test
//    fun deleteTaskTest(): Unit = runBlocking {
//        val task = Task(id = 1, title = "Task1", description = "Description1", status = "TO DO")
//
//        todoViewModel.delete(task)
//        verify(todoCreateUseCase).delete(task)
//
//    }
//    @Test
//    fun updateTaskTest(): Unit = runBlocking {
//        val task = Task(id = 1, title = "Task1", description = "Description1", status = "TO DO")
//
//        todoViewModel.update(task)
//        verify(todoCreateUseCase).update(task)
//    }
}