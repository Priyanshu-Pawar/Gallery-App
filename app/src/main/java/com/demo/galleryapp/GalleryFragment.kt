package com.demo.galleryapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.galleryapp.adapters.FolderAdapter
import com.demo.galleryapp.adapters.ImagesAdapter
import com.demo.galleryapp.models.Folder
import com.demo.galleryapp.models.Model
import java.io.File

class GalleryFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var imagesAdapter: ImagesAdapter
    private lateinit var folderAdapter: FolderAdapter
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
        val pictureList = mainViewModel.getImages()
        arguments?.let {
            position = it.getInt(ARG_POSITION, 0)
        }
        if (position == 0) {
            recyclerView.layoutManager =
                GridLayoutManager(requireContext(), 3, LinearLayoutManager.VERTICAL, false)
            imagesAdapter = ImagesAdapter(requireContext(), pictureList)
            recyclerView.adapter = imagesAdapter
        } else {

            val folders: List<Folder> =
                pictureList.groupBy { File(it.name).parent }.map { (path, models) ->
                    Folder(path!!, models as ArrayList<Model>)
                }
            recyclerView.layoutManager =
                GridLayoutManager(requireContext(), 3, LinearLayoutManager.VERTICAL, false)
            folderAdapter = FolderAdapter(requireContext(), folders)
            recyclerView.adapter = folderAdapter
        }
        return view
    }

}
