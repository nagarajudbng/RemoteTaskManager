package com.single.todohome.usecases

import android.util.Log
import com.pesto.core.data.mapper.toUpdateTaskEntity
import com.pesto.core.data.repository.TaskLocalRepositoryImpl
import com.pesto.core.data.repository.TaskRemoteRepositoryImpl
import com.pesto.core.domain.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// Created by Nagaraju Deshetty on 07/05/24.
class DeleteTaskUseCase @Inject constructor(
    private var localRepositoryImpl: TaskLocalRepositoryImpl,
    private var remoteRepositoryImpl: TaskRemoteRepositoryImpl
) {

    suspend fun delete(task: Task) {
        Log.d("Search HomeTaskUseCase","delete")
        localRepositoryImpl.delete(task.toUpdateTaskEntity())
        remoteRepositoryImpl.delete(task.toUpdateTaskEntity())
    }

}