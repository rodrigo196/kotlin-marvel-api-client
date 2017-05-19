package br.com.bulgasoftwares.feedreader.view

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.Toast
import br.com.bulgasoftwares.feedreader.R
import br.com.bulgasoftwares.feedreader.controller.FeedListAdapter
import br.com.bulgasoftwares.feedreader.model.bean.Character
import br.com.bulgasoftwares.feedreader.model.bean.Response
import br.com.bulgasoftwares.feedreader.model.bussines.MarvelBO
import br.com.bulgasoftwares.feedreader.model.network.RestApi
import kotlinx.android.synthetic.main.content_home.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

class HomeActivity : AppCompatActivity() {

    private var subscriptions = CompositeSubscription()
    private var bo = MarvelBO(RestApi())
    private var response: Response? = null

    companion object {
        private val KEY_CHARACTERS_LIST = "charactersList"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val feedList : RecyclerView = findViewById(R.id.feed_list) as RecyclerView
        val linearLayout = LinearLayoutManager(this)
        feedList.layoutManager = linearLayout
        feedList.clearOnScrollListeners()
        feedList.addOnScrollListener(InfiniteScrollListener({requestCharacters()}, linearLayout))

        val adapter  = FeedListAdapter(feedList = ArrayList<Character>()){
            Toast.makeText(this, "Clicked item " + it.name, Toast.LENGTH_SHORT).show()
        }

        feedList.adapter = adapter

        toolbar.title = "Marvel characters"

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_CHARACTERS_LIST)) {
            response = savedInstanceState.get(KEY_CHARACTERS_LIST) as Response
            adapter.clearAndAddCharacters(response!!.data.results.toMutableList())
        } else {
            requestCharacters()
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (response != null && response!!.data.results.isNotEmpty()) {
            response!!.data.results = (feed_list.adapter as FeedListAdapter).feedList.toMutableList()
            outState.putSerializable(KEY_CHARACTERS_LIST, response)
        }
    }

    override fun onResume() {
        super.onResume()
        subscriptions = CompositeSubscription()
    }

    override fun onPause() {
        super.onPause()
        subscriptions.clear()
    }

    private fun requestCharacters(){
        val offset = response?.data?.offset ?: 0
        val count = response?.data?.count ?: 0
        val subscription = bo.getCharacters((offset + count).toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { retrieved ->
                            response = retrieved
                            (feed_list.adapter as FeedListAdapter).addCharacters(response?.data?.results!!.toMutableList())
                        },
                        { e ->
                            Log.e("MarvelBO", e.message)
                            Snackbar.make(feed_list, e.message ?: "", Snackbar.LENGTH_LONG).show()
                        }
                )
        subscriptions.add(subscription)
    }
}