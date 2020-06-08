package com.mosh.songfinder.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mosh.songfinder.R
import com.mosh.songfinder.data.repository.CollectionRepository
import com.mosh.songfinder.databinding.FragmentCollectionBinding
import com.mosh.songfinder.domain.CollectionSong
import com.mosh.songfinder.presentation.ui.adapters.SongCollectionAdapter
import com.mosh.songfinder.presentation.viewmodels.CollectionViewModel
import com.mosh.songfinder.presentation.viewmodels.CollectionViewModelFactory
import com.mosh.songfinder.presentation.viewmodels.coroutine.CoroutineContextProvider
import kotlinx.android.synthetic.main.item_song_row.view.*

class CollectionFragment : Fragment() {

    private lateinit var viewModel: CollectionViewModel
    private lateinit var adapterCollection: SongCollectionAdapter
    private var idCollection: Int = -1
    private var binding: FragmentCollectionBinding? = null
    fun getBinding() = binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idCollection = arguments?.let {
            CollectionFragmentArgs.fromBundle(it).idCollection
        } ?: -1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCollectionBinding.inflate(inflater, container, false)
        return getBinding().root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val repository = CollectionRepository()
        val contextProvider = CoroutineContextProvider()
        val factory = CollectionViewModelFactory(repository, contextProvider)

        viewModel = ViewModelProviders.of(this, factory).get(CollectionViewModel::class.java)
        observeViewModel()
        initView()
    }

    private fun observeViewModel() {
        val collectionObserver = Observer<CollectionViewModel.CollectionViewState> {
            when(it) {
                is CollectionViewModel.CollectionViewState.Loading -> showLoading()
                is CollectionViewModel.CollectionViewState.Success -> drawScreen(it.data)
                is CollectionViewModel.CollectionViewState.Error -> navToErrorState(it.throwable)
            }
        }
        viewModel.getStateLiveData().observe(viewLifecycleOwner, collectionObserver)
    }

    private fun initView() {
        adapterCollection = SongCollectionAdapter(requireContext(), listOf())
        getBinding().rvListSongs.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        getBinding().rvListSongs.adapter = adapterCollection

        viewModel.getCollectionFromServer(idCollection)
    }

    private fun showLoading() {
        getBinding().rvListSongs.visibility = View.GONE
        getBinding().cvCollection.visibility = View.GONE
        getBinding().tvTitle.visibility = View.GONE
        getBinding().lottieLoading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        getBinding().lottieLoading.visibility = View.GONE
    }

    private fun drawScreen(data: CollectionSong?) {
        data?.let {
            Glide.with(requireContext())
                .applyDefaultRequestOptions(
                    RequestOptions()
                        .placeholder(R.drawable.ic_default)
                        .error(R.drawable.ic_default))
                .load(it.artUrl100)
                .into(getBinding().ivImage)
            getBinding().tvCollectionName.text = it.collectionName
            getBinding().tvArtistName.text = it.artistName
            getBinding().cvCollection.visibility = View.VISIBLE

            adapterCollection.items = it.songs
            adapterCollection.notifyDataSetChanged()
            getBinding().rvListSongs.visibility = View.VISIBLE
            getBinding().tvTitle.visibility = View.VISIBLE
        }
        hideLoading()
    }

    private fun navToErrorState(throwable: Throwable) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}