package com.archestro.grocery.presentation.categoryProducts

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
import com.archestro.grocery.databinding.CategoryFragmentBinding
import com.archestro.grocery.presentation.home.Adapter.Product.ProductsAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CategoryFragment : ScopeFragment() {


    private val viewModel:CategoryViewModel by inject {(parametersOf(args.category))}

    private var _binding:CategoryFragmentBinding?=null
    private val binding get()=_binding!!

    private val args by navArgs<CategoryFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= CategoryFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.title="Products"
        bindUI()
    }

    private fun bindUI()=launch(Dispatchers.Main){

        binding.textViewCategoryName.text=args.category
        val productsList=viewModel.categoryProductLiveData()
        productsList?.observe(viewLifecycleOwner, Observer { entries->
            if(entries==null) return@Observer

            binding.groupLoading.visibility= View.GONE
            productsList.value?.let { initProductsRecyclerView(it) }
        })
    }

    private fun initProductsRecyclerView(items: List<Product>)
    {
        val adapter= ProductsAdapter()
        adapter.submitList(items)
        binding.categoryProductRecycler.layoutManager= GridLayoutManager(context,3)
        binding.categoryProductRecycler.adapter=adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getViewModel(): BaseViewModel? {
        return viewModel
    }

}