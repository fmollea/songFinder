package com.mosh.songfinder.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.mosh.songfinder.databinding.FragmentErrorStateBinding

class ErrorStateFragment : Fragment() {

    private lateinit var query: String
    private var binding: FragmentErrorStateBinding? = null
    fun getBinding() = binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        query = arguments?.let {
            ErrorStateFragmentArgs.fromBundle(it).query
        } ?: DEFAULT
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentErrorStateBinding.inflate(inflater, container, false)
        return getBinding().root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getBinding().btRerty.setOnClickListener {
            val action: NavDirections = ErrorStateFragmentDirections.actionErrorStateFragmentToSongListFragment(query)
            findNavController().navigate(action)
        }
    }

    companion object {
        private const val DEFAULT = "DEFAULT"
    }
}