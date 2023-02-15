package com.example.myapplication.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemHomeGeneresNameBinding
import com.example.myapplication.model.home.ResponseGenres
import javax.inject.Inject


class GenresAdapter @Inject constructor() : RecyclerView.Adapter<GenresAdapter.ViewHolder>() {
    private lateinit var binding: ItemHomeGeneresNameBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemHomeGeneresNameBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder()
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])

    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun setData(item:ResponseGenres.ResponseGenresItem){
            binding.apply {
                txtName.text = item.name

            }
        }

    }

    private val differCallBack = object : DiffUtil.ItemCallback<ResponseGenres.ResponseGenresItem>(){
        override fun areItemsTheSame(oldItem: ResponseGenres.ResponseGenresItem, newItem: ResponseGenres.ResponseGenresItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ResponseGenres.ResponseGenresItem, newItem: ResponseGenres.ResponseGenresItem): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this,differCallBack)
}