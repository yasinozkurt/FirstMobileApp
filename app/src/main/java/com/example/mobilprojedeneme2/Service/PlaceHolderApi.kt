package com.example.mobilprojedeneme2.Service
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PlaceHolderApi {


    //games pathine get isteği atıldığında çalışan fonksiyon
    @GET("api/games")
    fun getGames(@Query("page_size") page_size:String="10",
        @Query("page") page:String
        ,@Query("key") key: String="3be8af6ebf124ffe81d90f514e59856c"):Call<Games>

    @GET("api/games/{id}?key=3be8af6ebf124ffe81d90f514e59856c")
    fun getDetails(@Path("id")id:Int):Call<GameDetail>

    @GET("api/games")
    fun getSearched(@Query("search") search:String,
                    @Query("key") key:String="3be8af6ebf124ffe81d90f514e59856c") :Call<SearchResult>

}