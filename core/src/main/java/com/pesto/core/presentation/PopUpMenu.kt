package com.pesto.core.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp


// Created by Nagaraju on 15/05/24.

var popUpMenu = listOf(
    PopUpMenuItem(id = "1", label = "Done"),
    PopUpMenuItem(id = "2", label = "To Do"),
    PopUpMenuItem(id = "3", label = "In Progress"),
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
    imageVector: ImageVector,
    ) {

    var expanded by remember { mutableStateOf(false) }

    Box() {

        Box() {
            IconButton(onClick = {
                expanded = !expanded
            }) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = Color.White
                )
            }
        }

        Box(modifier = modifier.padding(top=40.dp)) {
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