package br.com.bulgasoftwares.feedreader.model.network

import br.com.bulgasoftwares.feedreader.extensions.md5
import br.com.bulgasoftwares.feedreader.model.bean.Response
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

class RestApi {

    private val marvelApi: MarvelApi

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://gateway.marvel.com:443")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        marvelApi = retrofit.create(MarvelApi::class.java)
    }

    fun getCharacters(after: String, limit: String) : Call<Response> {
        val timeStamp = Date().time.toString()

        // TODO Put your secret key where.
        val hash = (timeStamp + "your secret key").md5
        return marvelApi.getCharacters(limit, after, timeStamp, hash)
    }

}
