package com.mosh.songfinder.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mosh.songfinder.R
import com.mosh.songfinder.data.dao.AppDataBase
import com.mosh.songfinder.data.repository.SongRepository
import com.mosh.songfinder.databinding.FragmentSearchListBinding
import com.mosh.songfinder.domain.Search
import com.mosh.songfinder.presentation.ui.activities.SongFinderActivity
import com.mosh.songfinder.presentation.ui.adapters.SearchAdapter
import com.mosh.songfinder.presentation.viewmodels.SongViewModel
import com.mosh.songfinder.presentation.viewmodels.SongViewModelFactory
import com.mosh.songfinder.presentation.viewmodels.coroutine.CoroutineContextProvider
import com.mosh.songfinder.utils.Utils

class SearchListFragment : Fragment() {

    private lateinit var viewModel : SongViewModel
    private lateinit var adapterSearch : SearchAdapter
    private var binding : FragmentSearchListBinding? = null
    fun getBinding() = binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val db = AppDataBase(requireActivity().applicationContext)
        val repository = SongRepository(db)
        val contextProvider = CoroutineContextProvider()
        val factory = SongViewModelFactory(repository, contextProvider)
        viewModel = ViewModelProviders.of(this, factory).get(SongViewModel::class.java)

        viewModel.getSearchsFromDB().observe(viewLifecycleOwner, Observer {
            drawListSearch( it.map { item -> item.toSearch() })
        })

        observeViewModel()
        initView()
    }

    private fun observeViewModel() {
        val sonObserver = Observer<SongViewModel.SongViewState> {
            when (it) {
                is SongViewModel.SongViewState.Loading -> showLoading()
                is SongViewModel.SongViewState.SuccessSearch -> drawListSearch(it.data)
                is SongViewModel.SongViewState.Error -> navToErrorState()
            }
        }

        viewModel.getStateLiveData().observe(viewLifecycleOwner, sonObserver)
    }

    private fun initView() {
        (requireActivity() as SongFinderActivity).title = "History"
        adapterSearch = SearchAdapter(this, listOf())
        getBinding().rvListSearches.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        getBinding().rvListSearches.adapter = adapterSearch

        getBinding().searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (Utils.isConnected(requireContext())) {
                    query?.let {
                        val action: NavDirections = SearchListFragmentDirections.actionSearchListFragmentToSongListFragment(it)
                        findNavController().navigate(action)
                    }
                } else {
                    Toast.makeText(context, "Must be connected to the internet", Toast.LENGTH_LONG).show()
                }

                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }

    private fun drawListSearch(list: List<Search>) {
        if (list.isEmpty()) {
            navToEmptyState()
        } else {
            adapterSearch.items = list
            adapterSearch.notifyDataSetChanged()
            getBinding().rvListSearches.visibility = View.VISIBLE
        }
        hideLoading()
    }

    private fun navToEmptyState() {
        findNavController().navigate(R.id.emptyStateFragment)
    }

    private fun navToErrorState() {
        findNavController().navigate(R.id.errorStateFragment)
    }

    private fun showLoading() {
        getBinding().lottieLoading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        getBinding().lottieLoading.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}