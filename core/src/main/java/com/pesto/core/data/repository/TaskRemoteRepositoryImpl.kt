package com.pesto.core.data.repository

import android.util.Log
import androidx.work.workDataOf
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pesto.core.data.dto.TaskDB
import com.pesto.core.data.mapper.toTaskDB
import com.pesto.core.data.source.local.entity.TaskEntity
import com.pesto.core.domain.repository.RemoteRepository
import com.pesto.core.domain.repository.RowId
import javax.inject.Inject
import javax.inject.Named

class TaskRemoteRepositoryImpl @Inject constructor(
    @Named("Task") private val  databaseReference: DatabaseReference
):RemoteRepository {
    override suspend fun insert(id:Long, task: TaskEntity): RowId {

        val taskDB = task.toTaskDB(id.toString())
        databaseReference.child(id.toString()).setValue(taskDB)
        return id
    }
    override suspend fun delete(task: TaskEntity) {
        databaseReference.child(task.id.toString()).setValue(null)
    }
    override suspend fun update(task: TaskEntity) {
        databaseReference.child(task.id.toString()).setValue(task.toTaskDB(task.id.toString()))
    }
}
