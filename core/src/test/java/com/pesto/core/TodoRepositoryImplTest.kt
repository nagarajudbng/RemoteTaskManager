package com.pesto.core

import com.pesto.core.data.repository.TodoRepositoryImpl
import com.pesto.core.data.source.local.AppDatabase
import com.pesto.core.data.source.local.dao.TaskDao
import com.pesto.core.data.source.local.entity.Task
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


// Created by Nagaraju on 12/05/24.

class TodoRepositoryImplTest {


    @Mock
    private lateinit var repository: TodoRepositoryImpl

    @Mock
    private lateinit  var appDatabase: AppDatabase

    @Mock
    private lateinit var taskDao: TaskDao

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun insertTaskTest() = runBlocking {
        var task = Task(id = 1, title = "task1",description = "description1",status = "To Do")

        repository.insert(task)
    }


}