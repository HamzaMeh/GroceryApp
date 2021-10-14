package com.archestro.grocery.presentation.home.Adapter.Product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import com.archestro.grocery.R
import com.archestro.grocery.base.BaseAdapter
import com.archestro.grocery.data.source.remote.model.response.product.Product
import com.archestro.grocery.databinding.FragmentProductsItemBinding
import com.archestro.grocery.internal.GlideApp
import com.archestro.grocery.presentation.home.HomeFragmentDirections


class ProductsAdapter(private val onClickListener: (product: Product) -> Unit): BaseAdapter<Product>(


    diffCallback = object : DiffUtil.ItemCallback<Product>()
    {

        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            TODO("Not yet implemented")
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            TODO("Not yet implemented")
        }

    }) {
    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.fragment_products_item,
            parent,
            false
        )
    }

    override fun bind(binding: ViewDataBinding, item: Product, position: Int) {
        when(binding){
            is FragmentProductsItemBinding ->{
                binding.textViewPrice.text= item.price.toString()
                binding.textViewProductTitle.text=item.title
                binding.textViewRating.text=item.rating.rate.toString()

                GlideApp.with(binding.root)
                    .load(item.image)
                    .error(R.drawable.ic_baseline_error_24)
                    .into(binding.ProductImage)

               /* binding.productCard.setOnClickListener{
                    val action=HomeFragmentDirections.actionHomeFragmentToProductDetail(item.id)
                    it.findNavController().navigate(action)
                }
                */
                binding.productCard.setOnClickListener { onClickListener.invoke(item) }

            }
        }
    }

}