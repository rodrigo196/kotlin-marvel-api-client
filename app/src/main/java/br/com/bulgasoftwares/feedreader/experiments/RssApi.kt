package br.com.bulgasoftwares.feedreader.experiments

import me.toptas.rssconverter.RssConverterFactory
import me.toptas.rssconverter.RssFeed
import retrofit2.Call
import retrofit2.Retrofit

class RssApi {
    private val rssService: RssService

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://github.com")
                .addConverterFactory(RssConverterFactory.create())
                .build()

        rssService = retrofit.create(RssService::class.java)
    }

    fun getFeed(feedUrl: String): Call<RssFeed> {
        return rssService.getRss(feedUrl)
    }
}