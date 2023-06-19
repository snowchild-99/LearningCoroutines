package com.snowchild.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.processNextEventInCurrentThread
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.time.withTimeout
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlin.math.log
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {
    private  val TAG = "MainActivity"
    private lateinit var textview : TextView
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textview = findViewById(R.id.textview)
        Log.d(TAG, "From Thread : ${Thread.currentThread().name}")



//        GlobalScope.launch {
//            delay(5000L)
//            Log.d(TAG, "From Thread : ${Thread.currentThread().name}")
//            val response =  doNetworkCall()
//            val response1 =  doNetworkCall1()
//            Log.d(TAG, "Suspend Function: $response")
//            Log.d(TAG, "Suspend Function: $response1")
//        }


            // Switching Context of Coroutine
//        GlobalScope.launch(Dispatchers.IO) {
//                val answer = doNetworkCall()
//            Log.d(TAG, "onCreate: ${Thread.currentThread().name}")
//            withContext(Dispatchers.Main){
//                textview.text = answer
//                Log.d(TAG, "onCreate: ${Thread.currentThread().name}")
//            }
//        }


        //Blocking Main Thread

       // Log.d(TAG, "Before RunBlocking")
//        runBlocking {
//            delay(5000L)
//            launch(Dispatchers.IO) {
//                delay(3000L)
//                Log.d(TAG, "Finished IO Coroutine1")
//
//            }
//            launch(Dispatchers.IO) {
//                delay(3000L)
//                Log.d(TAG, "Finished IO Coroutine2  ")
//
//            }
//            Log.d(TAG, "Start of runblocking: ")
//            delay(2000L)
//        }

       // Log.d(TAG, "After  runblocking: ")

        val job = GlobalScope.launch(Dispatchers.Default) {
            repeat(5) {
                Log.d(TAG, "Job is Running: ")
                delay(1000L)
            }
        }

        runBlocking {
            Log.d(TAG, "Main Thread Paused")
            job.join()
            Log.d(TAG, "Job done")
        }
        Log.d(TAG, "Main Thread Started Again")




        // cancel a Coroutine
//        val job1 = GlobalScope.launch(Dispatchers.Default) {
//            Log.d(TAG, "Long running task started ")
//            for (i in 1..155500){
//                if(isActive) {
//                    Log.d(TAG, "Number: $i")
//                }
//            }
//            Log.d(TAG, "Long running task finished ")
//        }

//        runBlocking {
//            delay(2000L)
//            job1.cancel()
//            Log.d(TAG, "Job Cancelled")
//        }


        //Another way to cancel the Coroutine without blocking the Main Thread
        //we Can use withTimeout suspend function
        // instead of using job.cancel in runBlocking function

         GlobalScope.launch(Dispatchers.Default) {
            Log.d(TAG, "Long running task started ")
            withTimeout(100L) {
                for (i in 1..155500) {
                    if (isActive) {
                        Log.d(TAG, "Number: $i")
                    }
                }
            }
             Log.d(TAG, "Long running job2 cancelled ")
        }

        //async and await
        GlobalScope.launch (Dispatchers.IO){
            val time = measureTimeMillis {
                val response1 = async {
                    doNetworkCall()
                }
                val response2 = async {
                    doNetworkCall1()
                }
                Log.d(TAG, "response1: ${response1.await()}")
                Log.d(TAG, "response2: ${response2.await()}")
            }
            Log.d(TAG, "TimeTook: $time")
        }





    }

         private suspend fun doNetworkCall() : String{
             delay(5000L)
            return "Network Response"
        }

        private suspend fun doNetworkCall1() : String{
             delay(3000L)
            return "Network Response1"
        }

    }