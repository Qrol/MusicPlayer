package com.example.musicplayer

import android.media.MediaMetadataRetriever

class SongData (
    val name: String,
    val author: String,
    val duration: String,
    val path: String
        ){

    fun getAlbumPicture() : ByteArray?{
        var retriever = MediaMetadataRetriever()
        retriever.setDataSource(path)
        val picture = retriever.embeddedPicture
        retriever.release()
        return picture
    }
}