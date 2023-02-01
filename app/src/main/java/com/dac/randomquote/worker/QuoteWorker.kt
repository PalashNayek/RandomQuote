package com.dac.randomquote.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.dac.randomquote.QuoteApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuoteWorker(private val context: Context, params : WorkerParameters) : Worker(context,params) {
    override fun doWork(): Result {
        Log.d("MyWorker","Worker Called")
        val repository = (context as QuoteApplication).quoteRepository
        CoroutineScope(Dispatchers.IO).launch {
            repository.getQuotesBackgraound()
        }
        return Result.success()
    }
}