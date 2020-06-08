package com.mosh.songfinder.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mosh.songfinder.databinding.FragmentCollectionBinding

class CollectionFragment : Fragment() {

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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}