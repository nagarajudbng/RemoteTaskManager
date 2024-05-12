package com.pesto.taskhome.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pesto.core.domain.model.Task
import com.pesto.core.presentation.AppBar
import com.pesto.core.presentation.UiEvent
import com.pesto.core.presentation.asString
import com.pesto.taskhome.R
import kotlinx.coroutines.flow.collectLatest

// Created by Nagaraju Deshetty on 07/05/24.
@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen(
        onNavigation = {},
        onSnackBarMessage = {})
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigation: (String) -> Unit,
    onSnackBarMessage:(String)->Unit
) {
    var viewModel = hiltViewModel<HomeTaskViewModel>()
    var  context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.getTaskList()
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    onSnackBarMessage(event.uiText.asString(context))
                }
                else -> {}
            }

        }
    }
    Scaffold(
        topBar = {
            TopBarView(viewModel)
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onNavigation("CreateTask")
                },
                containerColor = Color(0xFF396803)
//                Modifier.background(Color(0xFF396803))
            ) {
                Icon(Icons.Filled.Add,"")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                )
        ) {
            showTodoList(viewModel)
        }
    }
}
@Composable
fun TopBarView(viewModel: HomeTaskViewModel){
    var showSearch = viewModel.topBarState.value
    if(!showSearch) {
        AppBar(
            title = stringResource(id = R.string.app_bar_title),
            searchClick = {
                viewModel.onSearchEvent(SearchEvent.TopSearchSelected(true))
            },
            backClick = {},
            isSearchEnable = true
        )
    }else{
    SearchBar(
        Modifier.padding(horizontal = 16.dp),
        onSearchTextEntered = {
            viewModel.onSearchEvent(SearchEvent.OnSearchQuery(it))
        },
        onSearchStart = {
            viewModel.onSearchEvent(SearchEvent.OnSearchStart(it))
        },
        onFocusChange = {
            viewModel.onSearchEvent((SearchEvent.OnFocusChange(it)))
        },
        onBackPressed = {
            viewModel.onSearchEvent(SearchEvent.OnSearchQuery(""))
            viewModel.onSearchEvent(SearchEvent.OnClearPressed)
        },
        onClearPressed = {
            viewModel.onSearchEvent(SearchEvent.OnClearPressed)
        },
        viewModel.searchQuery.value,
        viewModel.focusState.value
    )
    }
}
@Composable
fun showTodoList(viewModel: HomeTaskViewModel){

    var todoList = viewModel.todoList.value
    if(todoList.size>0) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp)
        )
        {
            items(todoList.size) { item ->
                ListItem(todoList.get(item))
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Press the + button to add a TODO item"
            )
        }
    }
}

@Preview
@Composable
fun ListItemPreview(){
    ListItem(Task(title = "Hello", description = "Description", status = ""))
}
@Composable
fun ListItem(task: Task){

    Card(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth()
            .height(60.dp),
        shape = MaterialTheme.shapes.medium,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            task.title.let {
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = it,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Light,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}
