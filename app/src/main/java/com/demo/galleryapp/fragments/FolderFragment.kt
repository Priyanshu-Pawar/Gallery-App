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
import com.demo.galleryapp.adapters.FolderAdapter
import com.demo.galleryapp.models.Folder
import com.demo.galleryapp.models.Model
import java.io.File

class FolderFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var folderAdapter: FolderAdapter
    private lateinit var mainViewModel: MainViewModel
    private var position: Int = 1

    companion object {
        private const val ARG_POSITION_FOLDER = "position"
        fun newInstance(position: Int): FolderFragment {
            val fragment = FolderFragment()
            val args = Bundle()
            args.putInt(ARG_POSITION_FOLDER, position)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_folder, container, false)

        recyclerView = view.findViewById(R.id.recycler_view_folder)

        mainViewModel = ViewModelProvider(
            this, MainViewModelFactory(requireActivity().application)
        )[MainViewModel::class.java]

        arguments?.let {
            position = it.getInt(ARG_POSITION_FOLDER, 1)
        }

        mainViewModel.gData.observe(viewLifecycleOwner){
            if (position == 1) {
//                val pictureList = mainViewModel.getImages()
                val folders: List<Folder> =
                    it.groupBy { File(it.name).parent }.map { (path, models) ->
                        Folder(path!!, models as ArrayList<Model>)
                    }
                recyclerView.layoutManager =
                    GridLayoutManager(requireContext(), 3, LinearLayoutManager.VERTICAL, false)
                folderAdapter = FolderAdapter(requireActivity(), folders)
                recyclerView.adapter = folderAdapter
            }
        }
        return view
    }

}