package com.example.musicplayer.songlist

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.R
import com.example.musicplayer.SongData
import com.example.musicplayer.SongsViewModel
import com.example.musicplayer.databinding.SongItemBinding

class SongsRVAdapter(
    private val songsViewModel: SongsViewModel,
    private val listener: (Int) -> Unit
) : RecyclerView.Adapter<SongsRVAdapter.ViewHolder>(){

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
            val binding = SongItemBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SongItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val songItem: SongData = songsViewModel.getSong(position)
        holder.binding.apply {
            songNameTV.text = songItem.name
            songAuthTV.text = songItem.author

            val picture = songItem.getAlbumPicture()
            if(picture != null)
            {
                val bitmap = BitmapFactory.decodeByteArray(picture, 0, picture.size)
                songImageIV.setImageBitmap(bitmap)
            }
            //else songImageIV.setImageResource(R.drawable.default_music)
        }

        holder.itemView.setOnClickListener{ listener(position) }
    }

    override fun getItemCount(): Int {
        return songsViewModel.getSongs().size
    }
}