package com.example.prodlist.remote.error

internal class RemoteDataSourceException(
    errorCode: String,
    errorMessage: String,
) : Exception("[$errorCode] $errorMessage")