package com.example.myapplication.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFavoriteBinding
import com.example.myapplication.ui.home.adapter.LastMoviesAdapter
import com.example.myapplication.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private lateinit var binding : FragmentFavoriteBinding

    @Inject
    lateinit var favoriteAdapter: FavoriteAdapter

    private val viewModel : FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentFavoriteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel.loadFavoriteList()
            viewModel.favoriteList.observe(viewLifecycleOwner){
                favoriteAdapter.setData(it)
                rcFavorite.layoutManager = LinearLayoutManager(requireContext())
                rcFavorite.adapter = favoriteAdapter

            }
            viewModel.empty.observe(viewLifecycleOwner){
                if (it)
                {
                    emtyItemLayout.visibility = View.VISIBLE
                    rcFavorite.visibility = View.GONE
                }
                else
                {
                    emtyItemLayout.visibility = View.GONE
                    rcFavorite.visibility = View.VISIBLE
                }
            }

            //click
            favoriteAdapter.setOnItemClickListener {
                val direction  = FavoriteFragmentDirections.actionToDetailFragment(it.id)
                findNavController().navigate(direction)
            }

        }
    }

}