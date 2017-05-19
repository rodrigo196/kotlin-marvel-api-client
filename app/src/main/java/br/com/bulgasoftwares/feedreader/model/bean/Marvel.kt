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

class Character(val id: Int, val name: String, val description : String,
                val modified : Date, val resourceUri: String, val thumbnail: Image)
    : Serializable

class Image(val path: String, val extension: String)
    : Serializable
