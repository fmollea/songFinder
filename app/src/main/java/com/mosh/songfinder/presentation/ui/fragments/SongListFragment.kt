package com.mosh.songfinder.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mosh.songfinder.R
import com.mosh.songfinder.data.dao.AppDataBase
import com.mosh.songfinder.data.repository.SongRepository
import com.mosh.songfinder.databinding.FragmentSongListBinding
import com.mosh.songfinder.domain.Song
import com.mosh.songfinder.presentation.ui.adapters.SongAdapter
import com.mosh.songfinder.presentation.viewmodels.SongViewModel
import com.mosh.songfinder.presentation.viewmodels.SongViewModelFactory
import com.mosh.songfinder.presentation.viewmodels.coroutine.CoroutineContextProvider

class SongListFragment : Fragment() {

    private lateinit var viewModel : SongViewModel
    private lateinit var adapterSong : SongAdapter
    private var binding : FragmentSongListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeViewModel()
        viewModel.getSongsFromServer("in+utero")
    }

    private fun initView() {
        val db = AppDataBase(requireActivity().applicationContext)
        val repository = SongRepository(db)
        val contextProvider = CoroutineContextProvider()
        val factory = SongViewModelFactory(repository, contextProvider)
        viewModel = ViewModelProviders.of(this, factory).get(SongViewModel::class.java)


        adapterSong = SongAdapter(requireActivity().applicationContext, listOf())
        binding!!.rvListSongs.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        binding!!.rvListSongs.adapter = adapterSong
    }

    private fun observeViewModel() {
        val sonObserver = Observer<SongViewModel.SongViewState> {
            when (it) {
                is SongViewModel.SongViewState.Loading -> showLoading()
                is SongViewModel.SongViewState.SuccessSong -> drawListSong(it.data)
                is SongViewModel.SongViewState.Error -> navToEmptyState()
            }
        }

        viewModel.getStateLiveData().observe(requireActivity(), sonObserver)
    }

    private fun navToEmptyState() {
        findNavController().navigate(R.id.emptyStateFragment)
    }

    private fun showLoading() {
        binding!!.lottieLoading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding!!.lottieLoading.visibility = View.GONE
    }

    private fun drawListSong(list: List<Song>) {
        adapterSong.items = list
        adapterSong.notifyDataSetChanged()
        binding!!.rvListSongs.visibility = View.VISIBLE
        hideLoading()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}