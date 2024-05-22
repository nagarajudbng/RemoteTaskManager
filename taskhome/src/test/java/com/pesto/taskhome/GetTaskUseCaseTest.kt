package com.pesto.taskhome

import org.junit.After
import com.pesto.core.data.repository.TaskLocalRepositoryImpl
import com.pesto.core.data.repository.TaskRemoteRepositoryImpl
import com.pesto.core.domain.model.Task
import com.single.todohome.usecases.GetTaskListUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


// Created by Nagaraju on 22/05/24.

class GetTaskUseCaseTest {

    @Mock
    private lateinit var localRepositoryImpl: TaskLocalRepositoryImpl

    @Mock
    private lateinit var remoteRepositoryImpl: TaskRemoteRepositoryImpl

    @InjectMocks
    private lateinit var getTaskListUseCase: GetTaskListUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testGetTask(): Unit = runBlocking {
        val task = Task(id = 1, title = "Task1", description = "Description1", status = "TO DO",dueDate = "Saturday, 25 May, 2024")
        val query = "task"
        getTaskListUseCase.getTaskList()
        verify(localRepositoryImpl).getTaskList()

    }

    @After
    fun tearDown() {
    }

}