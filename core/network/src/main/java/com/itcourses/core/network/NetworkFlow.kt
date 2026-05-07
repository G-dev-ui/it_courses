package com.itcourses.core.network

import com.itcourses.core.common.AppResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <T> networkFlow(block: suspend () -> T): Flow<AppResult<T>> = flow {
    try {
        emit(AppResult.Success(block()))
    } catch (t: Throwable) {
        emit(AppResult.Error(t))
    }
}

