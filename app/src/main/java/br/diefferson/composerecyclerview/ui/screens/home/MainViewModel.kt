package br.diefferson.composerecyclerview.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.diefferson.composerecyclerview.data.ProductsRepository
import br.diefferson.composerecyclerview.data.RetrofitClient
import br.diefferson.composerecyclerview.models.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val repository: ProductsRepository = ProductsRepository(RetrofitClient.getClient())
) : ViewModel() {


    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    private val _isLastPAge = MutableStateFlow(false)
    val isLastPage: StateFlow<Boolean>
        get() = _isLastPAge.asStateFlow()

    private val _products = MutableStateFlow(listOf<Product>())
    val products: StateFlow<List<Product>>
        get() = _products.asStateFlow()

    private var page = 1
    private var totalItems = 0

    fun nexPage() {
        page++
        loadProducts()
    }

    fun refresh() {
        page = 1
        viewModelScope.launch {
            _products.emit(emptyList())
            _isRefreshing.emit(true)
        }
        loadProducts()
    }

    private fun isLastPage(): Boolean {
        return totalItems <= page * pageSize
    }

    fun loadProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getProducts(limit = pageSize, skip = (page - 1) * 20)
            withContext(Dispatchers.Main) {
                val list = _products.value.toMutableList()

                if(page == 1){
                    list.clear();
                }

                list.addAll(response.products)

                _products.emit(list)

                totalItems = response.total
                _isLastPAge.emit(isLastPage())
                _isRefreshing.emit(false)
            }
        }
    }

    companion object {
        const val pageSize = 20
    }
}