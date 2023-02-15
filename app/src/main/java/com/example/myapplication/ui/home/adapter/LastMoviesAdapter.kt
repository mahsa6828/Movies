package com.example.myapplication.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.BundleCompat
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.myapplication.databinding.ItemHomeLastMoviesBinding
import com.example.myapplication.databinding.ItemHomeMoviesTopBinding
import com.example.myapplication.model.home.Data
import javax.inject.Inject
import android.os.Bundle
import android.view.OnReceiveContentListener
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.example.myapplication.R


class LastMoviesAdapter @Inject constructor() : RecyclerView.Adapter<LastMoviesAdapter.ViewHolder>() {
    private lateinit var binding: ItemHomeLastMoviesBinding
    var moviesList = emptyList<Data>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemHomeLastMoviesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder()
    }

    override fun getItemCount(): Int =moviesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(moviesList[position])
        holder.setIsRecyclable(false)


    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun bindItems(item:Data){
            binding.apply {
                movieNameTxt.text = item.title
                movieRateTxt.text = item.imdb_rating
                movieCountryTxt.text = item.country
                movieCalenderTxt.text = item.year
                imgPoster.load(item.poster){
                    crossfade(true)
                    crossfade(800)
                }

                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }

                }

            }
        }

    }

    private var onItemClickListener : ((Data) -> Unit)? = null

    fun setOnItemClickListener(listener: (Data) -> Unit){
        onItemClickListener = listener
    }

    fun setData(data:List<Data>){
        val moviesDiffUtils = MoviesDiffUtils(moviesList,data)
        val diffUtils = DiffUtil.calculateDiff(moviesDiffUtils)
        moviesList = data
        diffUtils.dispatchUpdatesTo(this)

    }

    class MoviesDiffUtils(private val oldItem:List<Data>,private val newItem:List<Data>) : DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldItem.size
        }

        override fun getNewListSize(): Int {
            return newItem.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

    }

}