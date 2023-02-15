package com.example.myapplication.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDetailBinding
import com.example.myapplication.db.MovieEntity
import com.example.myapplication.model.home.Data
import com.example.myapplication.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    private var movieId:Int = 0
    private val args : DetailFragmentArgs by navArgs()

    private val viewModel : DetailViewModel by viewModels()

    @Inject
    lateinit var imagesAdapter: ImagesAdapter

    @Inject
    lateinit var entity : MovieEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = args.movieId
        viewModel.detailsMovie(movieId)
        viewModel.isExists(movieId)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            //get Data
            viewModel.detailsMovie.observe(viewLifecycleOwner){respone->
                imgBigPoster.load(respone.poster)
                normalImg.load(respone.poster){
                    crossfade(800)
                    crossfade(true)
                }
                movieNameTxt.text = respone.title
                movieRateTxt.text = respone.imdb_rating
                movieDateTxt.text = respone.released
                movieTimeTxt.text = respone.runtime
                summaryInfo.text = respone.plot
                actorInfo.text = respone.actors
                imagesAdapter.differ.submitList(respone.images)
                rcImages.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                rcImages.adapter = imagesAdapter

                // click favorite icon
                imgFavorite.setOnClickListener {
                    entity.id=movieId
                    entity.poster = respone.poster
                    entity.title = respone.title
                    entity.rate = respone.imdb_rating
                    entity.year = respone.year
                    entity.country = respone.country
                    viewModel.favoriteMovie(movieId,entity)
                }
            }
            //loading
            viewModel.loading.observe(viewLifecycleOwner){
                if (it){
                    progresDetail.visibility = View.VISIBLE
                    detailFragmentScrollView.visibility = View.GONE
                }
                else
                {
                    progresDetail.visibility = View.GONE
                    detailFragmentScrollView.visibility = View.VISIBLE
                }
            }

            viewModel.isFavorite.observe(viewLifecycleOwner){
                if (it){
                    imgFavorite.setColorFilter(ContextCompat.getColor(requireContext(), R.color.scarlet))
                }
                else
                {
                    imgFavorite.setColorFilter(ContextCompat.getColor(requireContext(), R.color.philippineSilver))
                }
            }
            //back img
            imgBack.setOnClickListener {
                findNavController().navigateUp()
            }

        }
    }

}