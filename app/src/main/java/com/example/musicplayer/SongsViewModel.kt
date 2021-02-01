package com.example.musicplayer

import android.app.Application
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.ViewModel

class SongsViewModel (): ViewModel(){
    private var songsList : ArrayList<SongData> = ArrayList()
    fun setList(newSongList: ArrayList<SongData>) {
        songsList = newSongList
    }
    fun getSong(id: Int): SongData = songsList[id]
    fun getSongs(): ArrayList<SongData> = songsList
}