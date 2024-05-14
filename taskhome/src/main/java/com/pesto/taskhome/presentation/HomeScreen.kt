package com.pesto.taskhome.presentation

import android.media.Image
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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                Icon(Icons.Filled.Add, "")
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
            ShowTodoList(viewModel)
        }
    }
}


@Composable
fun TopBarView(viewModel: HomeTaskViewModel) {
    val showSearch = viewModel.topBarState.value
    if (!showSearch) {
        AppBar(
            title = stringResource(id = R.string.app_bar_title),
            searchClick = {
                viewModel.onSearchEvent(SearchEvent.TopSearchSelected(true))
            },
            backClick = {},
            isSearchEnable = true,
        )
    } else {
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
fun ShowTodoList(viewModel: HomeTaskViewModel) {

    val todoList = viewModel.todoList.value


    if (todoList.isNotEmpty()) {

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp)
        )
        {

            items(todoList.size) { item ->
                ListItem(
                    todoList[item],
                    action = { l, s ->
                        viewModel.onEvent(TaskUpdateEvent.EnteredActionUpdate(l, s))
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

@Composable
fun ListItem(
    task: Task,
    action: (Task, String) -> Unit
) {

    Card(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth()
            .height(80.dp),
        shape = MaterialTheme.shapes.medium,
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
                        Text(
                            modifier = Modifier.padding(start = 10.dp),
                            text = task.title.toUpperCase(),
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
                            text = task.description,
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
                            action(task, it)
                        },
                                iconTint = Color.Black
                    )
//                    IconButton(onClick = {
//
//                    }) {
//                        Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "")
//                    }
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
                /*
                options.forEachIndexed { _, item ->
                    DropdownMenuItem(
                        text = { Text(text = "label") },
                        onClick = {
                        expanded = false
                        action(item.id)
                    }) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                painterResource(id = item.icon),
                                contentDescription = null,
                                tint = iconTint,
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = item.label,
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    shadow = Shadow(
                                        color = Color.Black, offset = Offset(0.0f, 0.0f), blurRadius = 0f
                                    )
                                ),
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                    if (item.hasBottomDivider) {
                        Divider()
                    }
                }

                 */
            }
        }
    }

}