package br.com.bulgasoftwares.feedreader.experiments

import me.toptas.rssconverter.RssFeed
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url


interface RssService {
    /**
     * No baseUrl defined. Each RSS Feed will be consumed by it's Url
     * @param url RSS Feed Url
     * *
     * @return Retrofit Call
     */
    @GET
    fun getRss(@Url url: String): Call<RssFeed>
}