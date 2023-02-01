package com.dac.randomquote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dac.randomquote.repository.Response
import com.dac.randomquote.viewmodels.MainViewModelFactory
import com.dac.randomquote.viewmodels.MainViewModels

class MainActivity : AppCompatActivity() {
    lateinit var mainViewModels: MainViewModels

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = (application as QuoteApplication).quoteRepository
        mainViewModels = ViewModelProvider(this, MainViewModelFactory(repository))
            .get(MainViewModels::class.java)

        mainViewModels.quotes.observe(this, Observer {
            when(it){
                is Response.Loading ->{}
                is Response.Success ->{
                    it.data?.let {
                        Log.d("MyRes1",it.results.toString())
                        Toast.makeText(this@MainActivity, it.results.size.toString(),Toast.LENGTH_SHORT).show()
                    }
                }
                is Response.Error ->{
                    Toast.makeText(this@MainActivity, "Some error occured",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}