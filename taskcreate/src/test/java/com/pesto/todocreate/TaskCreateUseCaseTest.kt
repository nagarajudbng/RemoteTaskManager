package com.pesto.todocreate

import com.pesto.core.data.mapper.toTaskEntity
import com.pesto.core.data.repository.TaskLocalRepositoryImpl
import com.pesto.core.data.repository.TaskRemoteRepositoryImpl
import com.pesto.core.domain.model.Task
import com.pesto.todocreate.domain.usecase.TaskCreateUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

// Created by Nagaraju on 13/05/24.

class TaskCreateUseCaseTest {


    @Mock
    private lateinit var localRepositoryImpl: TaskLocalRepositoryImpl

    @Mock
    private lateinit var remoteRepositoryImpl: TaskRemoteRepositoryImpl

    @InjectMocks
    private lateinit var todoCreateUseCase: TaskCreateUseCase
    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun insertTaskTest(): Unit = runBlocking {
        val task = Task(id = 1, title = "Task1", description = "Description1", status = "TO DO",dueDate = "Saturday, 25 May, 2024",alarmTime = "14:15 PM")
        val id = 1L
        `when`(localRepositoryImpl.insert(task.toTaskEntity())).thenReturn(id)
        todoCreateUseCase.insert(task)
        verify(remoteRepositoryImpl).insert(id,task.toTaskEntity())
    }

}