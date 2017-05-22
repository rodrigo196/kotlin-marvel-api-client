package br.com.bulgasoftwares.feedreader.model.network

import br.com.bulgasoftwares.feedreader.extensions.md5
import java.util.*


class MarvelServerAuthentication {
    val timeStamp: String = Date().time.toString()
    val hash: String
    private val secretKey = "secret key"

    init {
        hash = (timeStamp + secretKey).md5
    }
}