package com.archestro.grocery.presentation.productDetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.archestro.grocery.R
import com.archestro.grocery.base.BaseViewModel
import com.archestro.grocery.base.ScopeFragment
import com.archestro.grocery.data.source.remote.model.response.product.Product
import com.archestro.grocery.databinding.ProductDetailFragmentBinding
import com.archestro.grocery.internal.GlideApp
import com.archestro.grocery.presentation.home.Adapter.Product.ProductsAdapter
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class ProductDetail : ScopeFragment() {


    private val viewModel: ProductDetailViewModel by inject { parametersOf(args.productID)  }

    private val args by navArgs<ProductDetailArgs>()

    private var _binding:ProductDetailFragmentBinding?=null
    private val binding get()=_binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= ProductDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.title="Detail"
        bindUI()
    }

    private fun bindUI()=launch {

        viewModel.productDetailLiveData.observe(viewLifecycleOwner, Observer { entry->
            if(entry==null) return@Observer

            binding.groupLoading.visibility=View.GONE

            binding.productTitle.text=entry.title
            binding.productDescription.text=entry.description
            binding.productPrice.text= entry.price.toString()
            binding.textViewRating.text= entry.rating?.rate.toString()

            GlideApp.with(binding.root)
                .load(entry.image)
                .error(R.drawable.ic_baseline_error_24)
                .into(binding.productImage)
        })

    }

    override fun getViewModel(): BaseViewModel? {
        return viewModel
    }


}