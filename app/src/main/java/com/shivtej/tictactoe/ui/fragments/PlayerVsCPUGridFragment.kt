package com.shivtej.tictactoe.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.shivtej.tictactoe.R
import com.shivtej.tictactoe.databinding.FragmentPlayerVsCpuGridBinding
import com.shivtej.tictactoe.models.Cells
import com.shivtej.tictactoe.utils.Board
import com.shivtej.tictactoe.utils.Board.Companion.COMPUTER
import com.shivtej.tictactoe.utils.Board.Companion.PLAYER
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlayerVsCPUGridFragment : Fragment(R.layout.fragment_player_vs_cpu_grid) {

    private lateinit var binding: FragmentPlayerVsCpuGridBinding
    private val boardCells = Array(3) { arrayOfNulls<ImageView>(3) }
    var playerCount: Int = 0
    var compCount: Int = 0
    var board = Board()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPlayerVsCpuGridBinding.bind(view)

        loadBoard()

        binding.buttonRestart.setOnClickListener {

            board = Board()
            binding.resultTv.text = "Player's Move Will\nStart The Game"
            mapBoardToUi()
        }
    }

    private fun mapBoardToUi() {
        for (i in board.board.indices) {
            for (j in board.board.indices) {
                when (board.board[i][j]) {
                    Board.PLAYER -> {
                        boardCells[i][j]?.setImageResource(R.drawable.circle)
                        boardCells[i][j]?.isEnabled = false
                        binding.resultTv.text = ""
                    }

                    Board.COMPUTER -> MainScope().launch {
                        delay(400L)
                        boardCells[i][j]?.setImageResource(R.drawable.cross)
                        boardCells[i][j]?.isEnabled = false
                    }
                    else -> {

                        boardCells[i][j]?.setImageResource(0)
                        boardCells[i][j]?.isEnabled = true
                    }
                }
            }
        }
    }

    private fun loadBoard() {


        for (i in boardCells.indices) {
            for (j in boardCells.indices) {
                boardCells[i][j] = ImageView(context)
                boardCells[i][j]?.layoutParams = GridLayout.LayoutParams().apply {
                    rowSpec = GridLayout.spec(i)
                    columnSpec = GridLayout.spec(j)
                    width = 250
                    height = 250
                    bottomMargin = 4
                    topMargin = 4
                    rightMargin = 4
                    leftMargin = 4
                }
                context?.let { ContextCompat.getColor(it, R.color.yellow) }?.let {
                    boardCells[i][j]?.setBackgroundColor(
                        it
                    )
                }
                boardCells[i][j]?.setOnClickListener(CellClickListener(i, j))
                binding.layoutBoard.addView(boardCells[i][j])
            }
        }
    }

    inner class CellClickListener(
        private val i: Int,
        private val j: Int
    ) : View.OnClickListener {

        override fun onClick(p0: View?) {

            if (!board.isGameOver) {
                val cell = Cells(i, j)
                board.placeMove(cell, PLAYER)

                board.miniMax(0, COMPUTER)
                board.compMove?.let {
                    board.placeMove(it, COMPUTER)
                }
                mapBoardToUi()
            }

            when {
                board.hasCompWon() -> MainScope().launch {
                    disableGrid(binding.layoutBoard)
                    delay(400L)
                    binding.resultTv.text = "Computer Won"
                    compCount++
                    binding.cpuCount.text = compCount.toString()

                }
                board.hasPlayerWon() -> {
                   disableGrid(binding.layoutBoard)
                    binding.resultTv.text = "Player Won"
                    playerCount++
                    binding.playerCount.text = playerCount.toString()

                }
                board.availableCells.isEmpty() -> {
                    binding.resultTv.text = "Game Draw"
                    disableGrid(binding.layoutBoard)
                }
            }
        }
    }

    private fun disableGrid(layout: GridLayout) {

        // Get all touchable views
        val layoutButtons = layout.touchables

        // loop through them, if they are an instance of Button, disable it.
        for (v in layoutButtons) {
            if (v is ImageView) {
                (v as ImageView).isEnabled = false
            }
        }
    }

}