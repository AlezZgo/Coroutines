package com.example.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coroutines.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.lang.RuntimeException
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        val handler = CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception")
        }

        val scope = CoroutineScope(Dispatchers.IO + Job()).launch(handler) {
            repeat(22){
                runOnUiThread {
                    binding.textView.text = Random.nextInt().toString()
                }
                delay(100)
            }
            this.cancel()

        }

        binding.button.setOnClickListener {
            scope.cancel()
            binding.textView.text = scope.isCancelled.toString()
        }
        setContentView(binding.root)
    }
}