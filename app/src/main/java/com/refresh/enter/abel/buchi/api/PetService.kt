package com.refresh.enter.abel.buchi.api

import com.refresh.enter.abel.buchi.constant.Url.GET_PETS
import retrofit2.http.GET
import com.refresh.enter.abel.buchi.model.PetsResponse
import retrofit2.Call
import retrofit2.http.Path
import retrofit2.http.Query

interface PetService {
    @GET(GET_PETS)
    fun searchPets(
        @Query("limit") limit: Int,
        @Query("type") type: String?,
        @Query("gender") gender: String?,
        @Query("age") age: String?,
        @Query("size") size: String?,
        @Query("good_with_children") good_with_children: Boolean
    ): Call<PetsResponse?>?
}