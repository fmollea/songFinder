package com.mosh.songfinder.presentation.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mosh.songfinder.databinding.SongFinderActivityBinding

class SongFinderActivity : AppCompatActivity() {

    private lateinit var binding : SongFinderActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SongFinderActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
