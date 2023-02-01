package com.dac.randomquote.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dac.randomquote.api.QuoteService
import com.dac.randomquote.db.QuoteDatabase
import com.dac.randomquote.models.QuoteList
import com.dac.randomquote.utils.NetworkUtils

class QuoteRepository(
    private val quoteService: QuoteService,
    private val quoteDatabase: QuoteDatabase,
    private val applicationContext: Context
)
{
    private val quotesLiveData = MutableLiveData<Response<QuoteList>>()
    val quotes : LiveData<Response<QuoteList>>
    get() = quotesLiveData

    suspend fun getQuotes(page : Int){
        if (NetworkUtils.isInternetAvailable(applicationContext)){
            try {
                val result = quoteService.getQuotes(page)
                if (result?.body() != null){
                    quoteDatabase.quoteDao().addQuotes(result.body()!!.results)
                    quotesLiveData.postValue(Response.Success(result.body()))
                }else{
                    quotesLiveData.postValue(Response.Error("API Error"))
                }
            }catch (e: Exception){
                quotesLiveData.postValue(Response.Error(e.message.toString()))
            }

        }else{
            try {
                val quotes = quoteDatabase.quoteDao().getQuotes()
                val quoteList = QuoteList(1,1,1,quotes,1,1)
                quotesLiveData.postValue(Response.Success(quoteList))
            }catch (e: Exception){
                Log.d("DBError",e.message.toString())
                //Database Error.................
            }
        }

    }

    suspend fun getQuotesBackgraound(){
        val randomNumber = (Math.random() * 10).toInt()
        val result = quoteService.getQuotes(randomNumber)
        if (result?.body() != null){
            quoteDatabase.quoteDao().addQuotes(result.body()!!.results)
        }
    }

}