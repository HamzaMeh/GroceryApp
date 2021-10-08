package com.archestro.grocery.presentation.home.Adapter.Categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import com.archestro.grocery.R
import com.archestro.grocery.base.BaseAdapter
import com.archestro.grocery.data.source.remote.model.response.category.Category
import com.archestro.grocery.databinding.FragmentCategoriesItemBinding
import com.archestro.grocery.internal.GlideApp
import com.archestro.grocery.presentation.home.HomeFragmentDirections


class CategoriesAdapter(): BaseAdapter<Category>(
    diffCallback = object : DiffUtil.ItemCallback<Category>()
    {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            TODO("Not yet implemented")
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            TODO("Not yet implemented")
        }

    }) {
    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.fragment_categories_item,
            parent,
            false
        )
    }

    override fun bind(binding: ViewDataBinding, item: Category, position: Int) {
        when(binding){
            is FragmentCategoriesItemBinding ->{
                binding.textViewCategoryText.text=item.name

                GlideApp.with(binding.root)
                    .load(item.image)
                    .error(R.drawable.ic_baseline_error_24)
                    .into(binding.categoryImage)

                binding.categoryItem.setOnClickListener {
                    val action=HomeFragmentDirections.actionHomeFragmentToCategoryFragment(item.name)
                    it.findNavController().navigate(action)

                }
            }
        }
    }

}