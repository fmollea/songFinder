package com.mosh.songfinder.presentation.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mosh.songfinder.data.dao.AppDataBase
import com.mosh.songfinder.data.repository.SongRepository
import com.mosh.songfinder.databinding.SongFinderActivityBinding
import com.mosh.songfinder.domain.Search
import com.mosh.songfinder.domain.Song
import com.mosh.songfinder.presentation.ui.adapters.SearchAdapter
import com.mosh.songfinder.presentation.ui.adapters.SongAdapter
import com.mosh.songfinder.presentation.viewmodels.SongViewModel
import com.mosh.songfinder.presentation.viewmodels.SongViewModelFactory
import com.mosh.songfinder.presentation.viewmodels.coroutine.CoroutineContextProvider

class SongFinderActivity : AppCompatActivity() {

    private lateinit var viewModel : SongViewModel
    private lateinit var adapterSong : SongAdapter
    private lateinit var adapterSearch : SearchAdapter
    private lateinit var binding : SongFinderActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SongFinderActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        observeViewModel()

        viewModel.getSongsFromServer("in+utero")
    }

    private fun initView() {
        val db = AppDataBase(this)
        val repository = SongRepository(db)
        val contextProvider = CoroutineContextProvider()
        val factory = SongViewModelFactory(repository, contextProvider)
        viewModel = ViewModelProviders.of(this, factory).get(SongViewModel::class.java)

        supportActionBar?.title = "List of Songs"

        adapterSong = SongAdapter(this, listOf())
        binding.rvListSongs.layoutManager = LinearLayoutManager(this)
        binding.rvListSongs.adapter = adapterSong

        adapterSearch = SearchAdapter(this, listOf())
        binding.rvListSearchs.layoutManager = LinearLayoutManager(this)
        binding.rvListSearchs.adapter = adapterSearch
    }

    private fun observeViewModel() {
        val sonObserver = Observer<SongViewModel.SongViewState> {
            when (it) {
                is SongViewModel.SongViewState.Loading -> showLoading()
                is SongViewModel.SongViewState.SuccessSong -> drawListSong(it.data)
                is SongViewModel.SongViewState.SuccessSearch -> drawListSearch(it.data)
                is SongViewModel.SongViewState.Error -> print("errorcito papá")
            }
        }

        viewModel.getStateLiveData().observe(this, sonObserver)
    }

    private fun showLoading() {
        binding.pbLoading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.pbLoading.visibility = View.GONE
    }

    private fun drawListSong(list: List<Song>) {
        adapterSong.items = list
        adapterSong.notifyDataSetChanged()
        binding.rvListSongs.visibility = View.VISIBLE
        hideLoading()
    }

    private fun drawListSearch(list: List<Search>) {
        adapterSearch.items = list
        adapterSearch.notifyDataSetChanged()
        binding.rvListSearchs.visibility = View.VISIBLE
        hideLoading()
    }
}
