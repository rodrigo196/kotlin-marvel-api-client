package br.com.bulgasoftwares.feedreader.controller

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.bulgasoftwares.feedreader.R
import br.com.bulgasoftwares.feedreader.extensions.ctx
import br.com.bulgasoftwares.feedreader.extensions.load
import br.com.bulgasoftwares.feedreader.model.bean.Character
import kotlinx.android.synthetic.main.feed_item.view.*

class CharacterListAdapter(val feedList: MutableList<Character>, val itemClick: (Character) -> Unit) :
        RecyclerView.Adapter<CharacterListAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindFeed(feedList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.ctx).inflate(R.layout.feed_item, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun getItemCount() = feedList.size

    fun addCharacters(characters: MutableList<Character>?) {
        characters?.let { feedList.addAll(it) }
        notifyDataSetChanged()
    }

    class ViewHolder(view: View, val itemClick: (Character) -> Unit) : RecyclerView.ViewHolder(view) {
        fun bindFeed(character: Character) {

            with(character) {
                itemView.icon.load("${thumbnail?.path}.${thumbnail?.extension}")
                itemView.name.text = name
                itemView.setOnClickListener { itemClick(this) }
            }

        }
    }

    fun clearAndAddCharacters(results: MutableList<Character>?) {
        feedList.clear()
        results?.let { feedList.addAll(it) }
    }
}