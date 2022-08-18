package com.example.android.wearable.composestarter.presentation.utils

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.promise.Promise
import io.ktor.client.call.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

inline fun <reified T> httpPromise(
    cancellableManager: CancellableManager?,
    coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    crossinline block: suspend () -> io.ktor.client.statement.HttpResponse
) = suspendBlockToPromise(cancellableManager, coroutineScope) { block().body<T>() }

inline fun <reified T> suspendBlockToPromise(
    cancellableManager: CancellableManager?,
    coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Unconfined),
    crossinline block: suspend () -> T
) = Promise.create<T>(cancellableManager) { resolve, reject ->
    coroutineScope.launch {
        val result: T = try {
            block()
        } catch (e: Throwable) {
            reject(e)
            return@launch
        }
        resolve(result)
    }.also { job ->
        cancellableManager?.add { job.cancel() }
    }
}
