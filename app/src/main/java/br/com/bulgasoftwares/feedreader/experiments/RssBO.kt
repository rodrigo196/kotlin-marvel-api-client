package br.com.bulgasoftwares.feedreader.experiments

import me.toptas.rssconverter.RssFeed
import rx.Observable


class RssBO {

    fun getPodCastFeed(url: String): Observable<RssFeed> {
        return Observable.create {
            subscriber ->

            val rssApi = RssApi()
            val response = rssApi.getFeed(url).execute()

            if (response.isSuccessful) {
                subscriber.onNext(response.body())
                subscriber.onCompleted()
            } else {
                subscriber.onError(Throwable(response.message()))
            }

        }
    }
}