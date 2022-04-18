package com.example.prodlist.ktutil.flow

import kotlinx.coroutines.flow.*

suspend fun <R> withResult(action: suspend () -> R) = try {
    Result.Success(action.invoke())
} catch (error: Exception) {
    Result.Error(error)
}

fun <A, B> combine(
    flow1: Flow<Result<A>>,
    flow2: Flow<Result<B>>,
): Flow<Result<Pair<A, B>>> = flow1.combine(flow2) { a, b -> a to b }
    .mapSuccessDataToPairResult()

fun <A, B, C> combine(
    flow1: Flow<Result<A>>,
    flow2: Flow<Result<B>>,
    flow3: Flow<Result<C>>,
): Flow<Result<Triple<A, B, C>>> = combine(flow1, flow2, flow3) { a, b, c -> Triple(a, b, c) }
    .mapSuccessDataToTripleResult()

fun <T> Flow<Result<T>>.onErrorOrCatch(action: suspend (Throwable) -> Unit) = this
    .onEach { if (it is Result.Error) action.invoke(it.exception) }
    .catch { throwable -> action.invoke(throwable) }

fun <T> Flow<Result<T>>.onError(action: suspend (Throwable) -> Unit) = this
    .onEach { if (it is Result.Error) action.invoke(it.exception) }

fun <T> Flow<Result<T>>.throwIfError() = this
    .onEach { if (it is Result.Error) throw it.exception }

fun <T> Flow<Result<T>>.onSuccess(action: suspend (T) -> Unit) = this
    .onEach { if (it is Result.Success) action.invoke(it.data) }

fun <T> Flow<Result<T>>.filterSuccess() = this
    .filterIsInstance<Result.Success<T>>()

fun <T, R> Flow<Result<T>>.flatMapSuccessDataLatest(action: suspend (T) -> Flow<Result<R>>) = this
    .flatMapLatest {
        when (it) {
            is Result.Error -> flowOf(Result.Error(it.exception))
            is Result.Success -> action.invoke(it.data)
            is Result.Loading -> flowOf(Result.Loading)
        }
    }

fun <T, R> Flow<Result<T>>.mapSuccessData(action: suspend (T) -> Result<R>) = this
    .map {
        when (it) {
            is Result.Error -> Result.Error(it.exception)
            is Result.Success -> action.invoke(it.data)
            is Result.Loading -> Result.Loading
        }
    }

fun <T> Flow<Result<T>>.mapError(action: suspend (Exception) -> Exception) = this
    .map {
        when (it) {
            is Result.Error -> Result.Error(action.invoke(it.exception))
            is Result.Success -> Result.Success(it.data)
            is Result.Loading -> Result.Loading
        }
    }

private fun <A, B> Flow<Pair<Result<A>, Result<B>>>.mapSuccessDataToPairResult() = transformLatest { (aResult, bResult) ->
    when {
        aResult is Result.Success && bResult is Result.Success -> emit(withResult { aResult.data to bResult.data })
        aResult is Result.Error -> emit(Result.Error(aResult.exception))
        bResult is Result.Error -> emit(Result.Error(bResult.exception))
        aResult is Result.Loading -> emit(Result.Loading)
        bResult is Result.Loading -> emit(Result.Loading)
    }
}

private fun <A, B, C> Flow<Triple<Result<A>, Result<B>, Result<C>>>.mapSuccessDataToTripleResult() = transformLatest { (aResult, bResult, cResult) ->
    when {
        aResult is Result.Success && bResult is Result.Success && cResult is Result.Success -> emit(withResult { Triple(aResult.data, bResult.data, cResult.data) })
        aResult is Result.Error -> emit(Result.Error(aResult.exception))
        bResult is Result.Error -> emit(Result.Error(bResult.exception))
        cResult is Result.Error -> emit(Result.Error(cResult.exception))
        aResult is Result.Loading -> emit(Result.Loading)
        bResult is Result.Loading -> emit(Result.Loading)
        cResult is Result.Loading -> emit(Result.Loading)
    }
}

fun <T> Flow<Result<T?>>.ignoreSuccessDataNull() = this
    .filterNot { result -> result is Result.Success && result.data == null }
    .mapSuccessData { data -> Result.Success(data!!) }

fun <T> Flow<Result.Success<T>>.mapToData() = this
    .map { it.data }

fun <T> Flow<Result<T>>.onLoading(action: suspend () -> Unit) = this
    .onEach { if (it is Result.Loading) action.invoke() }

fun <T> Result<T>.successOrThrow() = when (this) {
    is Result.Success -> this.data
    is Result.Loading -> throw Exception("Loading can not be cast as Success.")
    is Result.Error -> throw this.exception
}

suspend fun <T> Flow<Result<T>>.firstSuccessOrThrow() = throwIfError()
    .filterSuccess()
    .take(1)
    .mapToData()
    .single()