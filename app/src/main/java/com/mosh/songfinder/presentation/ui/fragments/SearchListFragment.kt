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
import com.mosh.songfinder.databinding.FragmentSearchListBinding
import com.mosh.songfinder.domain.Search
import com.mosh.songfinder.presentation.ui.adapters.SearchAdapter
import com.mosh.songfinder.presentation.viewmodels.SongViewModel
import com.mosh.songfinder.presentation.viewmodels.SongViewModelFactory
import com.mosh.songfinder.presentation.viewmodels.coroutine.CoroutineContextProvider

class SearchListFragment : Fragment() {

    private lateinit var viewModel : SongViewModel
    private lateinit var adapterSearch : SearchAdapter
    private var binding : FragmentSearchListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    fun getBinding() = binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

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
    }

    private fun initView() {
        adapterSearch = SearchAdapter(listOf())
        getBinding().rvListSearches.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        getBinding().rvListSearches.adapter = adapterSearch
    }

    private fun observeViewModel() {
        val sonObserver = Observer<SongViewModel.SongViewState> {
            when (it) {
                is SongViewModel.SongViewState.Loading -> showLoading()
                is SongViewModel.SongViewState.SuccessSearch -> drawListSearch(it.data)
                is SongViewModel.SongViewState.Error -> navToEmptyState(it.throwable)
            }
        }

        viewModel.getStateLiveData().observe(viewLifecycleOwner, sonObserver)
    }

    private fun drawListSearch(list: List<Search>) {
        adapterSearch.items = list
        adapterSearch.notifyDataSetChanged()
        getBinding().rvListSearches.visibility = View.VISIBLE
        hideLoading()
    }

    private fun navToEmptyState(e: Throwable) {
        findNavController().navigate(R.id.emptyStateFragment)
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