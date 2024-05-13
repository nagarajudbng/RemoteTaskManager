package com.pesto.taskhome.presentation

import com.pesto.core.domain.model.Task


// Created by Nagaraju Deshetty on 07/05/


sealed class TaskUpdateEvent {
    data class EnteredActionUpdate(val task:Task,val action:String): TaskUpdateEvent()
    data class EnteredSort(val type:String): TaskUpdateEvent()
    data class EnteredFilter(val status:String): TaskUpdateEvent()
    data class DialogueEvent(val isDismiss:Boolean): TaskUpdateEvent()
    data object Update: TaskUpdateEvent()
}