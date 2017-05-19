package br.com.bulgasoftwares.feedreader.extensions

val String.md5 : String
    get() {
      return Hash.md5(this)
    }