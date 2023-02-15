package com.example.myapplication.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.myapplication.databinding.ItemHomeLastMoviesBinding
import com.example.myapplication.databinding.ItemHomeMoviesTopBinding
import com.example.myapplication.db.MovieEntity
import com.example.myapplication.model.home.Data
import javax.inject.Inject


class FavoriteAdapter @Inject constructor() : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    private lateinit var binding: ItemHomeLastMoviesBinding
    var moviesList = emptyList<MovieEntity>()

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
        fun bindItems(item:MovieEntity){
            binding.apply {
                movieNameTxt.text = item.title
                movieRateTxt.text = item.rate
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
    private var onItemClickListener : ((MovieEntity) -> Unit)? = null

    fun setOnItemClickListener(listener: (MovieEntity) -> Unit){
        onItemClickListener = listener
    }

    fun setData(data:List<MovieEntity>){
        val moviesDiffUtils = MoviesDiffUtils(moviesList,data)
        val diffUtils = DiffUtil.calculateDiff(moviesDiffUtils)
        moviesList = data
        diffUtils.dispatchUpdatesTo(this)

    }

    class MoviesDiffUtils(private val oldItem:List<MovieEntity>,private val newItem:List<MovieEntity>) : DiffUtil.Callback(){
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