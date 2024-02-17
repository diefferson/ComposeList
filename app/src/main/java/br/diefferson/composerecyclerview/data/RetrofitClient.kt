package br.diefferson.composerecyclerview.data

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {
    fun getClient() : Retrofit {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        return  retrofit;

    }
}