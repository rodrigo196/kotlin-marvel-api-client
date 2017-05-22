package br.com.bulgasoftwares.feedreader.model.bean

import java.io.Serializable
import java.util.*

class Response(val code: String, val status: String, val copyright : String,
               val attributionText : String, val attributionHTML: String,
               val etag: String, val data: Data)
    : Serializable

class Data(val offset : Int, val limit: Int, val total: Int, val count: Int,
           var results: MutableList<Character>)
    : Serializable

class Character(val id: Int?, val name: String?, val description : String?,
                val modified : Date?, val resourceUri: String?, val urls: Array<Url>?,
                val thumbnail: Image?, val comics: ComicList?, val stories: StoryList?,
                val events: EventList?, val series: SeriesList?)
    : Serializable

class Image(val path: String, val extension: String)
    : Serializable

class Url(val type: String?, val url: String?) : Serializable

class ComicList(val available: Int?, val returned: Int?, val collectionURI: String?,
                val items: Array<ComicSummary>?) : Serializable

class ComicSummary(val resourceURI: String?, val name: String?) : Serializable

class StoryList(val available: Int?, val returned: Int?, val collectionURI: String?,
                val items: Array<StorySummary>?) : Serializable

class StorySummary(val resourceURI: String?, val name: String?, val type: String?) : Serializable

class EventList(val available: Int?, val returned: Int?, val collectionURI: String?,
                val items: Array<EventSummary>?) : Serializable

class EventSummary(val resourceURI: String?, val name: String?) : Serializable

class SeriesList(val available: Int?, val returned: Int?, val collectionURI: String?,
                val items: Array<SeriesSummary>?) : Serializable

class SeriesSummary(val resourceURI: String?, val name: String?) : Serializable