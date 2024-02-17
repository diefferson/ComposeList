package br.diefferson.composerecyclerview.models

import br.diefferson.composerecyclerview.models.Product

class ProductsResponse(
    val products: List<Product>,
    val total: Int,
    val skip: Int,
    val limit: Int,
)
