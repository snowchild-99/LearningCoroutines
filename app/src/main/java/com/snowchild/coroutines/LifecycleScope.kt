package com.snowchild.coroutines

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LifecycleScope : AppCompatActivity() {
    private val TAG = "LifecycleScope"
    private lateinit var button : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycler_scope)
        button = findViewById(R.id.btn)
        button.setOnClickListener {
            lifecycleScope.launch {
                while (true) {
                    delay(500L)
                    Log.d(TAG, "Running task")
                }
            }

                GlobalScope.launch {
                    delay(5000L)
                    Intent(this@LifecycleScope,MainActivity::class.java).also {
                        startActivity(it)
                        finish()
                    }

            }
        }
    }
}