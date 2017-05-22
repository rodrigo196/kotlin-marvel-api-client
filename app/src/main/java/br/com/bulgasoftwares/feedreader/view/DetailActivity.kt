package br.com.bulgasoftwares.feedreader.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import br.com.bulgasoftwares.feedreader.R
import br.com.bulgasoftwares.feedreader.extensions.load
import br.com.bulgasoftwares.feedreader.model.bean.Character
import kotlinx.android.synthetic.main.content_detail.*

class DetailActivity : AppCompatActivity() {

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
        image.load("${character?.thumbnail?.path}.${character?.thumbnail?.extension}")
        description.text = if (!character?.description.isNullOrEmpty()) character?.description
            else "Without description!"
    }

}
