package com.pesto.todocreate.presentation



// Created by Nagaraju Deshetty on 07/05/


sealed class TaskEvent {
    data class EnteredTitle(val title:String): TaskEvent()
    data class EnteredDescription(val description:String): TaskEvent()
    data class EnteredDueDate(val date:String): TaskEvent()
    data class EnteredAlarmTime(val time:String): TaskEvent()
    data class EnteredStatus(val status:String): TaskEvent()
    data class DialogueEvent(val isDismiss:Boolean): TaskEvent()
    data object AddTask: TaskEvent()
}