package br.com.bulgasoftwares.feedreader.model.bussines

import android.util.Log
import br.com.bulgasoftwares.feedreader.model.bean.Response
import br.com.bulgasoftwares.feedreader.model.network.RestApi
import javax.inject.Singleton
import rx.Observable

@Singleton
class MarvelBO(val api: RestApi) {

    fun getCharacters(after: String, limit: String = "30") : Observable<Response> {

        return Observable.create {
            subscriber ->
            Log.d("MarvelBO", "Getting after $after + limit $limit")
            val callResponse = api.getCharacters(after, limit)
            val response = callResponse.execute()

            if (response.isSuccessful){
                val dataResponse = response.body()

                subscriber.onNext(dataResponse)
                subscriber.onCompleted()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }

    }

}