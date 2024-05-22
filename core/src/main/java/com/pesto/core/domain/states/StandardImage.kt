package com.single.core.states

import android.net.Uri
import com.pesto.core.presentation.Error
import com.pesto.core.presentation.Validations

// Created by Nagaraju Deshetty on 08/05/24.


data class StandardImage(
    var uri:Uri? = null,
    val statusText:String? = null
)