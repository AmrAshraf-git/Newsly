package com.ipro.newsly.network

import com.ipro.newsly.core.util.Constants
import com.ipro.newsly.core.util.Constants.API_KEY
import com.ipro.newsly.core.util.Constants.HEADLINES_URL
import com.ipro.newsly.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET(HEADLINES_URL)
    suspend fun getBreakingNews(
        @Query("apiKey")
        apiKey:String=API_KEY,
        @Query("country")
        country:String="us",
        @Query("page")
        pageNumber:Int=1,

    ):Response<NewsResponse>

    @GET(Constants.EVERYTHING_URL)
    suspend fun newsSearch(
        @Query("apiKey")
        apiKey:String=API_KEY,
        @Query("q")
        searchQuery:String,
        @Query("page")
        pageNumber:Int=1,

        ):Response<NewsResponse>
}