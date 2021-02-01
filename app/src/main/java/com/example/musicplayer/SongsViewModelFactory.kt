package com.example.musicplayer

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class SongsViewModelFactory(private val context: Context?) : ViewModelProvider.Factory {

    fun initList() : ArrayList<SongData>
    {
        val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val songsList : ArrayList<SongData> = ArrayList()
        var dataModel : Array<String> = arrayOf(
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ARTIST)

        var cursor : Cursor? = this. context?.contentResolver?.query(uri, dataModel,null, null, null)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val title : String = cursor.getString(0)
                val duration : String = cursor.getString(1)
                val path : String = cursor.getString(2)
                val artist : String = cursor.getString(3)

                songsList.add(SongData(title, artist, duration, path))
            }
            cursor.close();
        }
        return songsList

    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SongsViewModel::class.java)){
            val model = SongsViewModel()
            model.setList(initList())
            return model as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }

}