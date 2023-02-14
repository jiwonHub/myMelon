package com.example.mymelon.service

import retrofit2.Call
import retrofit2.http.GET

interface MusicService {
    @GET("/v3/3a368940-833a-4713-a238-9b23e2a3e676")
    fun listMusics(): Call<MusicDTO>
}