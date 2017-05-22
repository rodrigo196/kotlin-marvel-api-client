package br.com.bulgasoftwares.feedreader.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import br.com.bulgasoftwares.feedreader.R
import br.com.bulgasoftwares.feedreader.extensions.load
import br.com.bulgasoftwares.feedreader.model.bean.Character
import br.com.bulgasoftwares.feedreader.model.bean.Response
import br.com.bulgasoftwares.feedreader.model.bussines.MarvelBO
import br.com.bulgasoftwares.feedreader.model.network.RestApi
import kotlinx.android.synthetic.main.content_detail.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

class DetailActivity : AppCompatActivity() {

    private var subscriptions = CompositeSubscription()
    private var response: Response? = null

    private val bo = MarvelBO(RestApi())

   companion object{
        val CHARACTER_OBJECT_KEY =
                "br.com.bulgasoftwares.feedreader.view.DetailActivity.CHARACTER_OBJECT_KEY"
   }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        val character = intent.extras.get(CHARACTER_OBJECT_KEY) as? Character
        toolbar.title = character?.name
        setSupportActionBar(toolbar)


        val subscription = bo.getCharacter(character?.id.toString())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe( { retrieved ->
                                response = retrieved
                                with(response?.data?.results?.get(0)){
                                    image.load("${this?.thumbnail?.path}.${this?.thumbnail?.extension}")
                                    description.text = if (!this?.description.isNullOrEmpty()) this?.description
                                        else "Without description!"
                                }
                            },
                            { e ->
                                Log.e("MarvelBO", e.message)
                            })

        subscriptions.add(subscription)
    }

    override fun onResume() {
        super.onResume()
        subscriptions = CompositeSubscription()
    }

    override fun onPause() {
        super.onPause()
        subscriptions.clear()
    }

}
