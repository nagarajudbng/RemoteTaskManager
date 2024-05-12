package com.pesto.todocreate

import com.pesto.core.data.repository.TodoRepositoryImpl
import com.pesto.core.data.source.local.entity.Task
import com.pesto.todocreate.domain.usecase.TodoCreateUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.verify

// Created by Nagaraju on 13/05/24.

class TodoCreateUseCaseTest {


    @Mock
    private lateinit var repository: TodoRepositoryImpl

    @InjectMocks
    private lateinit var todoCreateUseCase: TodoCreateUseCase
    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun insertTaskTest(): Unit = runBlocking {
        var task = Task(id = 1, title = "Task1", description = "Description1", status = "TO DO")

        todoCreateUseCase.insert(task)
        verify(repository).insert(task)
    }

    @Test
    fun deleteTaskTest() = runBlocking {
        var task = Task(id = 1, title = "Task1", description = "Description1", status = "TO DO")
        todoCreateUseCase.delete(task)
        verify(repository).delete(task)
    }

    @Test
    fun updateTaskTest() = runBlocking {
        var task = Task(id = 1, title = "Task1", description = "Description1", status = "TO DO")
        todoCreateUseCase.update(task)
        verify(repository).update(task)
    }
}