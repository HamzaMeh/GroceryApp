package com.archestro.grocery.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.archestro.grocery.R
import com.archestro.grocery.base.BaseViewModel
import com.archestro.grocery.base.ScopeFragment
import com.archestro.grocery.data.source.remote.model.response.category.Category
import com.archestro.grocery.data.source.remote.model.response.product.Product
import com.archestro.grocery.databinding.HomeFragmentBinding
import com.archestro.grocery.presentation.home.Adapter.Categories.CategoriesAdapter
import com.archestro.grocery.presentation.home.Adapter.Product.ProductsAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : ScopeFragment() {

    private val homeViewModel:HomeViewModel by viewModel()
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private var categoriesList:List<Category>?=null
    private var productList:List<Product>?=null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=  HomeFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.title=getString(R.string.Home)
        bindUI()
    }

    private fun bindUI()=launch(Dispatchers.Main) {

        homeViewModel.categoryLiveData.observe(viewLifecycleOwner, Observer {
            if(it.isEmpty()) return@Observer
            binding.groupLoading.visibility= View.GONE
            initCategoryRecyclerView(it)
        })
        homeViewModel.productLiveData.observe(viewLifecycleOwner, Observer {
            if(it.isEmpty()) return@Observer
            binding.groupLoading.visibility=View.GONE
            initProductsRecyclerView(it)
        })
    }

    private fun initCategoryRecyclerView(items: List<Category>)
    {

        val adapter= CategoriesAdapter()
        adapter.submitList(items)
        binding.categoryRecycler.layoutManager= GridLayoutManager(context,1, GridLayoutManager.HORIZONTAL,false)
        binding.categoryRecycler.adapter=adapter
    }
    private fun initProductsRecyclerView(items: List<Product>)
    {
        val adapter= ProductsAdapter(){product->
            val action=HomeFragmentDirections.actionHomeFragmentToProductDetail(product.id)
            findNavController().navigate(action)
        }
        adapter.submitList(items)
        binding.productRecycler.layoutManager=GridLayoutManager(context,3)
        binding.productRecycler.adapter=adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun getViewModel(): BaseViewModel? {
        return homeViewModel
    }

}