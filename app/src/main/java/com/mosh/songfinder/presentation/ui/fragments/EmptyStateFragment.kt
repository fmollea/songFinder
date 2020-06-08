package com.mosh.songfinder.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.mosh.songfinder.databinding.FragmentEmptyStateBinding
import com.mosh.songfinder.presentation.ui.activities.SongFinderActivity
import com.mosh.songfinder.utils.Utils

class EmptyStateFragment : Fragment() {

    private var binding: FragmentEmptyStateBinding? = null
    fun getBinding() = binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmptyStateBinding.inflate(inflater, container, false)
        return getBinding().root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as SongFinderActivity).title = "Finder of songs"

        getBinding().searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (Utils.isConnected(requireContext())) {
                    query?.let {
                        val action: NavDirections = EmptyStateFragmentDirections.actionEmptyStateFragmentToSongListFragment(it)
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}