package br.diefferson.composerecyclerview.data

import br.diefferson.composerecyclerview.models.ProductsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsApi {

    @GET("products")
    suspend fun groupProducts(
        @Query("limit") limit: Int = 20,
        @Query("skip") skip: Int = 0
    ): ProductsResponse

}