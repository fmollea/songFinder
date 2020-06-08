package com.mosh.songfinder.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mosh.songfinder.R
import com.mosh.songfinder.data.dao.AppDataBase
import com.mosh.songfinder.data.repository.SongRepository
import com.mosh.songfinder.databinding.FragmentSongListBinding
import com.mosh.songfinder.domain.Search
import com.mosh.songfinder.domain.Song
import com.mosh.songfinder.presentation.ui.adapters.SongAdapter
import com.mosh.songfinder.presentation.viewmodels.SongViewModel
import com.mosh.songfinder.presentation.viewmodels.SongViewModelFactory
import com.mosh.songfinder.presentation.viewmodels.coroutine.CoroutineContextProvider
import com.mosh.songfinder.utils.Utils
import androidx.navigation.fragment.findNavController
import com.mosh.songfinder.presentation.ui.activities.SongFinderActivity

class SongListFragment : Fragment() {

    private lateinit var viewModel : SongViewModel
    private lateinit var adapterSong : SongAdapter
    private lateinit var query: String
    private var binding : FragmentSongListBinding? = null
    fun getBinding() = binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        query = arguments?.let {
            SongListFragmentArgs.fromBundle(it).query
        } ?: DEFAULT
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongListBinding.inflate(inflater, container, false)
        return getBinding().root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val db = AppDataBase(requireActivity().applicationContext)
        val repository = SongRepository(db)
        val contextProvider = CoroutineContextProvider()
        val factory = SongViewModelFactory(repository, contextProvider)

        viewModel = ViewModelProviders.of(this, factory).get(SongViewModel::class.java)
        observeViewModel()
        initView()
    }

    private fun initView() {
        (requireActivity() as SongFinderActivity).title = "List of songs"
        adapterSong = SongAdapter(this, listOf())
        getBinding().rvListSongs.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        getBinding().rvListSongs.adapter = adapterSong

        getBinding().searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(term: String?): Boolean {
                if (Utils.isConnected(requireContext())) {
                    term?.let {
                        query = it
                        searchSongs(query)
                    }
                } else {
                    Toast.makeText(context, "Must be connected to the internet", Toast.LENGTH_LONG).show()
                    navToSearchList()
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
        getSongs()
    }

    private fun getSongs() {
        if (query != DEFAULT) {
            if (Utils.isConnected(requireActivity().applicationContext)) {
                viewModel.getSongsFromServer(Utils.obtainTerm(query))
            } else {
                viewModel.getSongsFromDB(query)
            }
        } else {
            navToSearchList()
        }
    }

    private fun observeViewModel() {
        val sonObserver = Observer<SongViewModel.SongViewState> {
            when (it) {
                is SongViewModel.SongViewState.Loading -> showLoading()
                is SongViewModel.SongViewState.SuccessSong -> drawListSong(it.data)
                is SongViewModel.SongViewState.Error -> navToErrorState(it.throwable)
            }
        }
        viewModel.getStateLiveData().observe(viewLifecycleOwner, sonObserver)
    }

    private fun searchSongs(query: String) {
        if (query.isNotEmpty()) {
            viewModel.getSongsFromServer(Utils.obtainTerm(query))
        }
    }

    private fun showLoading() {
        getBinding().rvListSongs.visibility = View.GONE
        getBinding().lottieLoading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        getBinding().lottieLoading.visibility = View.GONE
    }

    private fun drawListSong(list: List<Song>) {
        if (list.isEmpty()) {
            Toast.makeText(requireActivity().applicationContext, "Songs not found", Toast.LENGTH_LONG).show()
        } else {
            saveSearch(list)
            adapterSong.items = list
            adapterSong.notifyDataSetChanged()
        }
        hideLoading()
        getBinding().rvListSongs.visibility = View.VISIBLE
    }

    private fun saveSearch(list: List<Song>) {
        viewModel.insertSearchToDB(Search(query))
        saveSongs(list, query)
    }

    private fun saveSongs(list: List<Song>, query: String) {
        viewModel.insertSongToDB(list, query)
    }

    private fun navToErrorState(e: Throwable) {
        if (Utils.isNetworkError(e)) {
            findNavController().navigate(R.id.searchListFragment)
        } else {
            val action = SongListFragmentDirections.actionSongListFragmentToErrorStateFragment(query)
            this.findNavController().navigate(action)
        }
    }

    private fun navToSearchList() {
        findNavController().navigate(R.id.searchListFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        private const val DEFAULT = "DEFAULT"
    }
}