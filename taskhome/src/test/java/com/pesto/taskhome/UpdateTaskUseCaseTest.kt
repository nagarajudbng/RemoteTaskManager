package com.pesto.taskhome

import org.junit.After
import com.pesto.core.data.mapper.toTaskEntity
import com.pesto.core.data.mapper.toUpdateTaskEntity
import com.pesto.core.data.repository.TaskLocalRepositoryImpl
import com.pesto.core.data.repository.TaskRemoteRepositoryImpl
import com.pesto.core.domain.model.Task
import com.single.todohome.usecases.DeleteTaskUseCase
import com.single.todohome.usecases.FilterTaskUseCase
import com.single.todohome.usecases.GetTaskListUseCase
import com.single.todohome.usecases.SearchTaskUseCase
import com.single.todohome.usecases.UpdateTaskUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


// Created by Nagaraju on 22/05/24.

class UpdateTaskUseCaseTest {

    @Mock
    private lateinit var localRepositoryImpl: TaskLocalRepositoryImpl

    @Mock
    private lateinit var remoteRepositoryImpl: TaskRemoteRepositoryImpl

    @InjectMocks
    private lateinit var updateTaskUseCase: UpdateTaskUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testUpdate(): Unit = runBlocking {
        val task = Task(id = 1, title = "Task1", description = "Description1", status = "TO DO",dueDate = "Saturday, 25 May, 2024",alarmTime = "14:15 PM")
        updateTaskUseCase.update(task)
        verify(localRepositoryImpl).update(task.toUpdateTaskEntity())
        verify(remoteRepositoryImpl).update(task.toUpdateTaskEntity())
    }

    @After
    fun tearDown() {
    }

}