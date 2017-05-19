package br.com.bulgasoftwares.feedreader.model.network

import br.com.bulgasoftwares.feedreader.model.bean.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface MarvelApi {

    @GET("/v1/public/characters?apikey=656f7423c05cd2ca705607bbc40e56bf")
    fun getCharacters(@Query("limit") limit: String,
                      @Query("offset") after:String,
                      @Query("ts") timeStamp:String,
                      @Query("hash") hash: String)
    : Call<Response>
}