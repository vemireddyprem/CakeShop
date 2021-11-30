package com.prem.cakeshop.data.remote

import com.prem.cakeshop.data.remote.model.CakeInfoDto
import retrofit2.http.GET

interface CakeInfoApi {
    @GET("waracle_cake-android-client")
    suspend fun getCakesList(): List<CakeInfoDto>
}