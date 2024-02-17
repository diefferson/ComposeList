package br.diefferson.composerecyclerview.data

import br.diefferson.composerecyclerview.models.ProductsResponse
import retrofit2.Retrofit

class ProductsRepository(
    private val retrofit: Retrofit,
) {

    private val api: ProductsApi by lazy {
        retrofit.create(ProductsApi::class.java)
    }

    suspend fun getProducts(
        limit: Int = 20,
        skip: Int = 0
    ): ProductsResponse {
        return api.groupProducts(limit, skip)
    }
}