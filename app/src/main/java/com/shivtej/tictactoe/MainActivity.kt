package com.shivtej.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.shivtej.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun startGame(view: View) {
        val intent = Intent(this, GameActivity::class.java)
        val player1 = binding.player1.text.toString().trim()
        val player2 = binding.player2.text.toString().trim()
        if(player1 != "" && player2 != ""){
            intent.putExtra("player1", player1)
            intent.putExtra("player2", player2)
            startActivity(intent)
        }else{
            Toast.makeText(this, "Please Enter players name", Toast.LENGTH_SHORT).show()
        }

    }
}