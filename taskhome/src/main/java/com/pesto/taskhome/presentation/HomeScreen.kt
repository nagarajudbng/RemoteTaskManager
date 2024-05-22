package com.pesto.taskhome.presentation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pesto.core.domain.model.Task
import com.pesto.core.presentation.AppBar
import com.pesto.core.presentation.DatePickerWithDialog
import com.pesto.core.presentation.UiEvent
import com.pesto.core.presentation.asString
import com.pesto.taskhome.R
import kotlinx.coroutines.flow.collectLatest

// Created by Nagaraju Deshetty on 07/05/24.


@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen(
        onNavigation = {},
        onSnackBarMessage = {})
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigation: (String) -> Unit,
    onSnackBarMessage: (String) -> Unit
) {
    var viewModel = hiltViewModel<HomeTaskViewModel>()
    var context = LocalContext.current
    val drawerState = remember { mutableStateOf(DrawerValue.Closed) }

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
            TopBarView(onNavigation = {
                                      onNavigation(it)
            },
                viewModel)
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onNavigation("CreateTask")
                },
                containerColor = Color(0xFF396803)
//                Modifier.background(Color(0xFF396803))
            ) {
                Icon(Icons.Filled.Add, "")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize().background(Color(0xFFE9E9E9))
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                )
        ) {
            ShowTodoList(viewModel)
        }
    }
}


@Composable
fun TopBarView(
    onNavigation:(String)->Unit,
    viewModel: HomeTaskViewModel
) {
    val showSearch = viewModel.topBarState.value
    if (!showSearch) {
        AppBar(
            title = stringResource(id = R.string.app_bar_title),
            searchClick = {
                viewModel.onSearchEvent(SearchEvent.TopSearchSelected(true))
            },
            backClick = {},
            isSearchEnable = true,
            isFilterEnable = true,
            isProfileEnable = true,
            filter = {
                viewModel.onSearchEvent(SearchEvent.OnFilter(it))
            },
            gotoProfile = {
                onNavigation(it)
            }
        )
    } else {
        SearchBar(
            Modifier.padding(horizontal = 16.dp),
            onSearchTextEntered = {
                Log.d("Search","onSearchTextEntered")

                viewModel.onSearchEvent(SearchEvent.OnSearchQuery(it))
            },
            onSearchStart = {
                Log.d("Search","onSearchStart")
                viewModel.onSearchEvent(SearchEvent.OnSearchStart(it))
            },
            onFocusChange = {
                Log.d("Search","onFocusChange")
                viewModel.onSearchEvent((SearchEvent.OnFocusChange(it)))
            },
            onBackPressed = {
                Log.d("Search","onBackPressed")
//                viewModel.onSearchEvent(SearchEvent.OnSearchQuery(""))
//                viewModel.onSearchEvent(SearchEvent.OnClearPressed)
            },
            onClearPressed = {
                Log.d("Search","onClearPressed")
                viewModel.onSearchEvent(SearchEvent.OnClearPressed)
            },
            viewModel.searchQuery.value,
            viewModel.focusState.value
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowTodoList(viewModel: HomeTaskViewModel) {

    val todoList = viewModel.todoList.value
    Log.d("Search ShowTodoList","ShowTodoList "+todoList.size)

    if (todoList.isNotEmpty()) {

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp)

        )
        {

            items(todoList.size) { item ->
                ListItem(
                    todoList[item],
                    action = { task, status ->
                         viewModel.onEvent(TaskUpdateEvent.EnteredActionUpdate(task, status))
                    }
                )
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Press the + button to add a TODO item"
            )
        }
    }
}

@Preview
@Composable
fun ListItemPreview() {
//    ListItem(Task(title = "Hello", description = "Description", status = ""))
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ListItem(
    task: Task,
    action: (Task, String) -> Unit
) {
    val dialogState = remember { mutableStateOf(false) }
    val updateState = remember { mutableStateOf("") }
    if(dialogState.value){
        DatePickerWithDialog(
            onSelected = {
                dialogState.value = false
                task.dueDate = it
                action(task, updateState.value)
            },
            showModeToggle = true
        )
    }

    Card(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth()
            .height(80.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White //Card background color
//            contentColor = Color.White  //Card content color,e.g.text
        )
//            .background(Color.White)
//        shape = MaterialTheme.shapes.medium,
    ) {
        Row(
            modifier = Modifier.fillMaxSize()

        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)

                    .align(Alignment.CenterVertically)
            ) {


                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 10.dp),

                    ) {
                        var spanStyle:SpanStyle
                        if (task.isDueDateOver) {
                            spanStyle = SpanStyle(textDecoration = TextDecoration.LineThrough)
                        } else {
                            spanStyle = SpanStyle()
                        }
                        Text(
                            modifier = Modifier.padding(start = 10.dp),
                            text = AnnotatedString.Builder(task.title.toUpperCase())
                                    .apply {
                                        addStyle(
                                            style = spanStyle,
                                            start = 0,
                                            end = task.title.length
                                        )
                                    }
                                    .toAnnotatedString(),
                            maxLines = 1,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Light,
                            style = TextStyle(
                                fontSize = 16.sp,
                                shadow = Shadow(
                                    color = Color.Black,
                                    offset = Offset(-.0f, 0.0f),
                                    blurRadius = 0f
                                )
                            )

                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            modifier = Modifier.padding(start = 10.dp),
                            text = AnnotatedString.Builder(task.description)
                                .apply {
                                    addStyle(
                                        style = spanStyle,
                                        start = 0,
                                        end = task.description.length
                                    )
                                }
                                .toAnnotatedString(),
                            maxLines = 1,
                            color = Color.Black,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Light,
                            style = TextStyle(
                                fontSize = 12.sp,
                                shadow = Shadow(
                                    color = Color.LightGray,
                                    offset = Offset(0.0f, 0.0f),
                                    blurRadius = 0f
                                )
                            )
                        )

                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.Start
                        )
                        {


                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFF9FCA6D)),
                                textAlign = TextAlign.Start,
                                text = " Due Date : ${task.dueDate}",
                                color = Color.White,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Light,
                                style = TextStyle(
                                    fontSize = 8.sp,
                                    shadow = Shadow(
                                        color = Color.Black,
                                        offset = Offset(0.0f, 0.0f),
                                        blurRadius = 0f
                                    )
                                )
                            )
                        }

                }
            }
            Box(modifier = Modifier.weight(1f).fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        modifier = Modifier,
                        text = task.status,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Light,
                        style = TextStyle(
                            fontSize = 12.sp,
                            shadow = Shadow(
                                color = Color.Black, offset = Offset(0.0f, 0.0f), blurRadius = 0f
                            )
                        )
                    )
                    PopUpMenuButton(
                        modifier = Modifier.wrapContentSize(),
                        options = popUpMenu,
                        imageVector = Icons.Filled.MoreVert,
                        action = {
                            if((task.isDueDateOver) &&(it == "To Do" || it == "In Progress")){
                                dialogState.value = true
                                updateState.value = it
                            } else {
                                action(task, it)
                            }
                        },
                        iconTint = Color.Black
                    )
                }



                    Text(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .background(Color(0xFF9FCA6D)),

                        textAlign = TextAlign.End,
                        text = " ",
                        color = Color.White,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Light,
                        style = TextStyle(
                            fontSize = 8.sp,
                            shadow = Shadow(
                                color = Color.Black,
                                offset = Offset(0.0f, 0.0f),
                                blurRadius = 0f
                            )
                        )
                    )
            }
        }
    }
}

var popUpMenu = listOf(
    PopUpMenuItem(id = "1", label = "Done"),
    PopUpMenuItem(id = "2", label = "To Do"),
    PopUpMenuItem(id = "3", label = "In Progress"),
    PopUpMenuItem(id = "4", label = "Delete")
)
data class PopUpMenuItem(
    val id: String,
    val label: String
)
@Composable
fun PopUpMenuButton(
    options: List<PopUpMenuItem>,
    action: (String) -> Unit,
    modifier: Modifier,
    iconTint: Color,
    imageVector:ImageVector,

) {

    var expanded by remember { mutableStateOf(false) }

    Column() {

        Box() {
            IconButton(onClick = {
                expanded = !expanded
            }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = null,
                    modifier = Modifier.wrapContentSize()
                )
            }
        }

        Box(modifier = modifier) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .widthIn(min = 120.dp, max = 240.dp)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                options.forEachIndexed { _, item ->
                    DropdownMenuItem(
                        text = {
                            Text(text = item.label)
                        },
                        onClick = {
                            expanded = false
                            action(item.label)
                        }
                    )
                }
            }
        }
    }

}
