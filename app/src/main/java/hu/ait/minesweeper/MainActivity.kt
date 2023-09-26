package hu.ait.minesweeper

import androidx.appcompat.app.AppCompatActivity
import hu.ait.minesweeper.databinding.ActivityMainBinding
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


}