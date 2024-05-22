package com.pesto.core

import com.pesto.core.data.repository.TaskLocalRepositoryImpl
import com.pesto.core.data.source.local.AppDatabase
import com.pesto.core.data.source.local.dao.TaskDao
import com.pesto.core.data.source.local.entity.TaskEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


// Created by Nagaraju on 12/05/24.

class TaskLocalRepositoryImplTest {


    @InjectMocks
    private lateinit var repository: TaskLocalRepositoryImpl

    @Mock
    private lateinit  var appDatabase: AppDatabase

    @Mock
    private lateinit var taskDao: TaskDao

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun insertTaskTest()= runBlocking {
        val expectedId = 123L
        var task = TaskEntity(id = 1, title = "task1",description = "description1",status = "To Do", dueDate = "Saturday, 25 May, 2024")
        `when`(appDatabase.taskDao).thenReturn(taskDao)
        `when`(taskDao.insert(task)).thenReturn(expectedId)

        val result =  repository.insert(task)
        verify(taskDao).insert(task)
        assertEquals(expectedId, result)
    }


    @Test
    fun deleteTaskTest()= runBlocking {
        var task = TaskEntity(id = 1, title = "task1",description = "description1",status = "To Do",dueDate = "Saturday, 25 May, 2024")
        `when`(appDatabase.taskDao).thenReturn(taskDao)
        repository.delete(task)
        verify(taskDao).delete(task.id)
    }


    @Test
    fun updateTaskTest()= runBlocking {
        var task = TaskEntity(id = 1, title = "task1",description = "description1",status = "To Do",dueDate = "Saturday, 25 May, 2024")
        `when`(appDatabase.taskDao).thenReturn(taskDao)
        repository.update(task)
        verify(taskDao).update(task)
    }
}