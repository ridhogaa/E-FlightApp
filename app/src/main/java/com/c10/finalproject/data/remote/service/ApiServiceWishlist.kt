package com.c10.finalproject.data.remote.service

import com.c10.finalproject.data.remote.model.wishlist.GetWishlistResponse
import com.c10.finalproject.data.remote.model.wishlist.PostWishlistResponse
import retrofit2.http.*

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface ApiServiceWishlist {

    @GET("api/v1/wishlists")
    suspend fun getWishlists(): GetWishlistResponse

    @POST("api/v1/wishlists/add/{id}")
    suspend fun addWishlist(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): PostWishlistResponse

    @DELETE("api/v1/wishlists/delete/{id}")
    suspend fun deleteWishlist(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    )

}