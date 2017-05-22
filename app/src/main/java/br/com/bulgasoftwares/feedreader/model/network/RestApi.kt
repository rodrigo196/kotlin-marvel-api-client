package br.com.bulgasoftwares.feedreader.model.network

import br.com.bulgasoftwares.feedreader.model.bean.Response
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestApi {

    private val marvelApi: MarvelApi


    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://gateway.marvel.com:443")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        marvelApi = retrofit.create(MarvelApi::class.java)
    }

    fun getCharacters(after: String, limit: String): Call<Response> {
        val msa = MarvelServerAuthentication()
        return marvelApi.getCharacters(limit, after, msa.timeStamp, msa.hash)
    }

    fun getCharacter(characterId: String): Call<Response> {
        val msa = MarvelServerAuthentication()
        return marvelApi.getCharacter(characterId, msa.timeStamp, msa.hash)
    }

}
