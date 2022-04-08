package com.project.petcare.data.interfaces

import com.project.petcare.model.breedsapiobjects.Breeds
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface BreedsApi {
    @GET("breeds")
    @Headers(
        "Accept:application/json", "Content-Type:application/json",
        "Authorization: Bearer 70e97f7c-bc03-4d62-95f0-7a92d68994b8"
    )
    suspend fun getBreeds(): Response<List<Breeds>>
}