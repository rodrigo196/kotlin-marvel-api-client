package br.com.bulgasoftwares.feedreader.view

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import br.com.bulgasoftwares.feedreader.R
import br.com.bulgasoftwares.feedreader.controller.CharacterListAdapter
import br.com.bulgasoftwares.feedreader.extensions.analytics
import br.com.bulgasoftwares.feedreader.extensions.launchActivity
import br.com.bulgasoftwares.feedreader.model.bean.Character
import br.com.bulgasoftwares.feedreader.model.bean.Response
import br.com.bulgasoftwares.feedreader.model.bussines.MarvelBO
import br.com.bulgasoftwares.feedreader.model.network.RestApi
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_home.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

class HomeActivity : AppCompatActivity() {

    private var subscriptions = CompositeSubscription()
    private var response: Response? = null

    private val bo = MarvelBO(RestApi())


    companion object {
        private val KEY_CHARACTERS_LIST = "charactersList"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        toolbar.title = "Marvel characters"
        setSupportActionBar(toolbar)

        val adapter = CharacterListAdapter(feedList = ArrayList<Character>()) {

            launchActivity<DetailActivity> { putExtra(DetailActivity.CHARACTER_OBJECT_KEY, it) }

            val bundle = Bundle().apply {
                putString(FirebaseAnalytics.Param.ITEM_ID, it.id.toString())
                putString(FirebaseAnalytics.Param.ITEM_NAME, it.name)
                putString(FirebaseAnalytics.Param.CONTENT_TYPE, "character")
            }

            analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
        }


        val linearLayout = LinearLayoutManager(this)
        with(feed_list) {
            layoutManager = linearLayout
            clearOnScrollListeners()
            addOnScrollListener(InfiniteScrollListener({ requestCharacters() }, linearLayout))
            this.adapter = adapter
            setHasFixedSize(true)
        }


        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_CHARACTERS_LIST)) {
            response = savedInstanceState.get(KEY_CHARACTERS_LIST) as Response
            adapter.clearAndAddCharacters(response?.data?.results?.toMutableList())
        } else {
            requestCharacters()
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        response?.data?.results = (feed_list.adapter as CharacterListAdapter)
                .feedList.toMutableList()
        outState.putSerializable(KEY_CHARACTERS_LIST, response)
    }

    override fun onResume() {
        super.onResume()
        subscriptions = CompositeSubscription()
    }

    override fun onPause() {
        super.onPause()
        subscriptions.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        val menuItem = menu?.findItem(R.id.action_search)
        search_view.setMenuItem(menuItem)
        return true
    }

    private fun requestCharacters() {

        val offset = response?.data?.offset ?: 0
        val count = response?.data?.count ?: 0

        val subscription = bo.getCharacters((offset + count).toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { retrieved ->
                            response = retrieved
                            (feed_list.adapter as CharacterListAdapter)
                                    .addCharacters(response?.data?.results?.toMutableList())
                        },
                        { e ->
                            Log.e("MarvelBO", e.message)
                            Snackbar.make(feed_list, e.message ?: "", Snackbar.LENGTH_LONG).show()
                        }
                )
        subscriptions.add(subscription)
    }
}