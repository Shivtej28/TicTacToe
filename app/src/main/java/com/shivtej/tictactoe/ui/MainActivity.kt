package com.shivtej.tictactoe.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shivtej.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val navController =findNavController(R.id.nav_host_fragment)
    }
}
