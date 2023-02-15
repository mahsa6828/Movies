package com.example.myapplication.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSearchBinding
import com.example.myapplication.ui.home.adapter.LastMoviesAdapter
import com.example.myapplication.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
 @AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding

    @Inject
    lateinit var searchAdapter : LastMoviesAdapter

    private val viewModel : SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            searchEdt.addTextChangedListener {
                val search = it.toString()
                if (search.isNotEmpty()){
                    viewModel.searchMovies(search)
                }

            }
            //get movies
            viewModel.searchMovies.observe(viewLifecycleOwner){
                searchAdapter.setData(it.data)
                rcSearch.adapter = searchAdapter
                rcSearch.layoutManager = LinearLayoutManager(requireContext())
            }
            //loading
            viewModel.loading.observe(viewLifecycleOwner){
                if (it){
                    progresSearch.visibility = View.VISIBLE
                }
                else
                {
                    progresSearch.visibility = View.GONE
                }
            }

            // empty layout
            viewModel.emptyList.observe(viewLifecycleOwner){
                if (it){
                    emtyItemLayout.visibility = View.VISIBLE
                    rcSearch.visibility = View.GONE
                }
                else
                {
                    emtyItemLayout.visibility = View.GONE
                    rcSearch.visibility = View.VISIBLE
                }
            }
            //click
            searchAdapter.setOnItemClickListener {
                val direction  = SearchFragmentDirections.actionToDetailFragment(it.id)
                findNavController().navigate(direction)
            }
        }
    }

}