package com.demo.galleryapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.galleryapp.MainViewModel
import com.demo.galleryapp.MainViewModelFactory
import com.demo.galleryapp.R
import com.demo.galleryapp.adapters.ImagesAdapter

class GalleryFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var imagesAdapter: ImagesAdapter
    private var position: Int = 0
    private lateinit var mainViewModel: MainViewModel

    // Use this method to create new instances of the GalleryFragment with arguments
    companion object {
        private const val ARG_POSITION = "position"

        fun newInstance(position: Int): GalleryFragment {
            val fragment = GalleryFragment()
            val args = Bundle()
            args.putInt(ARG_POSITION, position)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(R.layout.fragment_gallery, container, false)

        recyclerView = view.findViewById(R.id.recycler_view)

        //VIEW MODEL CALL
        mainViewModel = ViewModelProvider(
            this, MainViewModelFactory(requireActivity().application)
        )[MainViewModel::class.java]

//        val pictureList = loadAllImages(requireContext())

//        val pictureList = mainViewModel.getImages()
        arguments?.let {
            position = it.getInt(ARG_POSITION, 0)
        }


        mainViewModel.gData.observe(viewLifecycleOwner){
            if (position == 0) {
                recyclerView.layoutManager =
                    GridLayoutManager(requireContext(), 4, LinearLayoutManager.VERTICAL, false)
                imagesAdapter = ImagesAdapter(requireContext(), it)
                recyclerView.adapter = imagesAdapter
            }
        }
        return view
    }

}
