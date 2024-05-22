package com.pesto.taskhome

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pesto.core.domain.model.Task
import com.pesto.taskhome.presentation.HomeTaskViewModel
import com.single.todohome.usecases.DeleteTaskUseCase
import com.single.todohome.usecases.FilterTaskUseCase
import com.single.todohome.usecases.GetTaskListUseCase
import com.single.todohome.usecases.SearchTaskUseCase
import com.single.todohome.usecases.UpdateTaskUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

// Created by Nagaraju on 22/05/24.

class HomeTaskViewModelTest{
    // Set the main coroutines dispatcher for unit testing
    private val testDispatcher = TestCoroutineDispatcher()

    // Set the main coroutine scope for unit testing
    private val testScope = TestCoroutineScope(testDispatcher)

    // Instantiate ViewModel with mocked dependencies using MockK
    @InjectMocks
    private lateinit var viewModel: HomeTaskViewModel

    // Mock the dependencies
    @Mock
    lateinit var updateTaskUseCase: UpdateTaskUseCase

    @Mock
    lateinit var deleteTaskUseCase: DeleteTaskUseCase

    @Mock
    lateinit var getTaskListUseCase: GetTaskListUseCase

    @Mock
    lateinit var filterTaskUseCase: FilterTaskUseCase

    @Mock
    lateinit var searchTaskUseCase: SearchTaskUseCase

    // Set up rule to swap background executor used by the Architecture Components with a different one
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    @Test
    fun `getTaskList should return empty list`() = runBlocking{
        `when`(getTaskListUseCase.getTaskList()).thenReturn(flowOf(emptyList()))
        viewModel.getTaskList()
        assertEquals(emptyList<Task>(), viewModel.todoList.value)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}