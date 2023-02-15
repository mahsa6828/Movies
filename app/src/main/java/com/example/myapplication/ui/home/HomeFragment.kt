package com.example.myapplication.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.ui.home.adapter.GenresAdapter
import com.example.myapplication.ui.home.adapter.LastMoviesAdapter
import com.example.myapplication.ui.home.adapter.TopMoviesAdapter
import com.example.myapplication.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val viewModel : HomeViewModel by viewModels()

    @Inject
    lateinit var topMoviesAdapter: TopMoviesAdapter

    @Inject
    lateinit var genresAdapter: GenresAdapter

    @Inject
    lateinit var lastMoviesAdapter: LastMoviesAdapter

    private val pagerSnapHelper : PagerSnapHelper by lazy { PagerSnapHelper() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        viewModel.topMovies(3)
//        viewModel.genresList()
//        viewModel.lastMovies()
        viewModel.callApis(3)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            // get top movies
            viewModel.topMovies.observe(viewLifecycleOwner){response ->
                topMoviesAdapter.differ.submitList(response.data)
                rcTopMovies.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                rcTopMovies.adapter = topMoviesAdapter
            }

            pagerSnapHelper.attachToRecyclerView(rcTopMovies)
            indicator.attachToRecyclerView(rcTopMovies,pagerSnapHelper)

            // get genres List
            viewModel.genresList.observe(viewLifecycleOwner){
                genresAdapter.differ.submitList(it)
                rcGenres.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                rcGenres.adapter = genresAdapter
            }

            // get last movies
            viewModel.lastMovies.observe(viewLifecycleOwner){response ->
                lastMoviesAdapter.setData(response.data)
                rcLastMovies.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                rcLastMovies.adapter = lastMoviesAdapter
            }
            // loading
            viewModel.loading.observe(viewLifecycleOwner){
                if (it){
                    progressHome.visibility = View.VISIBLE
                    scrollBarHome.visibility = View.GONE
                }
                else
                {
                    progressHome.visibility = View.GONE
                    scrollBarHome.visibility = View.VISIBLE
                }
            }

            //click
            lastMoviesAdapter.setOnItemClickListener {
                val direction  = HomeFragmentDirections.actionToDetailFragment(it.id)
                findNavController().navigate(direction)
            }


        }

    }

}