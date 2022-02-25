package com.jerry.clean_architecture_mvvm.presenation.content.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jerry.clean_architecture_mvvm.databinding.ViewholderContentBinding

import com.jerry.clean_architecture_mvvm.domain.entities.Content


class ContentAdapter(val onItemClickListener:OnItemClickListener)
    : RecyclerView.Adapter<ContentAdapter.ContentViewHolder>() {

    var contents = mutableListOf<Content>()
    var contentsFiltered = mutableListOf<Content>()


    interface OnItemClickListener{
        fun onItemClicked(item : Content)
    }

    fun setContentList(contents: List<Content>?) {
        if (contents!=null) {
            this.contents = contents.toMutableList()
            this.contentsFiltered = contents.toMutableList()
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        return from(parent)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(
            contents[position]
        )

        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClicked(contents[position])
        }
    }

    override fun getItemCount(): Int {
        return contents.size
    }


    class ContentViewHolder(private val binding: ViewholderContentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var content: Content? = null

        fun bind(content: Content?) {
            this.content = content

            content?.let {
                binding.tvTitle.text = content.title
                binding.tvSubTitle.text =content.subtitle
                binding.tvDate.text = content.date
            }
        }
    }

    companion object {
        fun from(parent: ViewGroup): ContentViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ViewholderContentBinding.inflate(layoutInflater, parent, false)
            return ContentViewHolder(binding)
        }
    }

}