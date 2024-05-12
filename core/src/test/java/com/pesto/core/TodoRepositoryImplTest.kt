package com.pesto.core

import com.pesto.core.data.repository.TodoRepositoryImpl
import com.pesto.core.data.source.local.AppDatabase
import com.pesto.core.data.source.local.dao.TaskDao
import com.pesto.core.data.source.local.entity.Task
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


// Created by Nagaraju on 12/05/24.

class TodoRepositoryImplTest {


    @InjectMocks
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
    fun insertTaskTest(): Unit {
        var task = Task(id = 1, title = "task1",description = "description1",status = "To Do")
        `when`(appDatabase.taskDao).thenReturn(taskDao)
        repository.insert(task)
        verify(taskDao).insert(task)
    }


    @Test
    fun deleteTaskTest()= runBlocking {
        var task = Task(id = 1, title = "task1",description = "description1",status = "To Do")
        `when`(appDatabase.taskDao).thenReturn(taskDao)
        repository.delete(task)
        verify(taskDao).delete(task)
    }


    @Test
    fun updateTaskTest()= runBlocking {
        var task = Task(id = 1, title = "task1",description = "description1",status = "To Do")
        `when`(appDatabase.taskDao).thenReturn(taskDao)
        repository.update(task)
        verify(taskDao).update(task)
    }
}