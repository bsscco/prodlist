package com.example.prodlist.ktutil.log

interface CrashReportHelper {

    fun logAndReport(error: Throwable)
}