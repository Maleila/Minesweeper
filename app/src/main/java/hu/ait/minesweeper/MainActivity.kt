package hu.ait.minesweeper

import androidx.appcompat.app.AppCompatActivity
import hu.ait.minesweeper.databinding.ActivityMainBinding
import android.os.Bundle
import android.os.SystemClock

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        resetTimer()

        binding.resetbtn.setOnClickListener() {
            binding.minesweeper.resetGame()
        }
    }

    fun isFlagModeOn() : Boolean {
        return binding.cbFlagMode.isChecked
    }

    fun resetCB() {
        binding.cbFlagMode.isChecked = false
    }

    fun resetTimer() {
        binding.timer.setBase(SystemClock.elapsedRealtime())
        binding.timer.start()
    }

    fun stopTimer() {
        binding.timer.stop()
    }
}