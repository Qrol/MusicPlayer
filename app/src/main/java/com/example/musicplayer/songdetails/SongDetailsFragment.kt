package com.example.musicplayer.songdetails

import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.musicplayer.SongData
import com.example.musicplayer.SongsViewModel
import com.example.musicplayer.SongsViewModelFactory
import com.example.musicplayer.databinding.SongDetailsFragmentBinding
import kotlin.properties.Delegates

class SongDetailsFragment:Fragment() {

    private lateinit var viewModel: SongsViewModel
    private lateinit var binding: SongDetailsFragmentBinding
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var songData: SongData
    private var songId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            songId = it.getInt("songId")
        }

        requireActivity().onBackPressedDispatcher.addCallback(this){
            val action = SongDetailsFragmentDirections.actionSongDetailsFragmentToSongsRVFragment()
            NavHostFragment.findNavController(this@SongDetailsFragment).navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = SongDetailsFragmentBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, SongsViewModelFactory(this.context)).get(SongsViewModel::class.java)
        songData = viewModel.getSong(songId!!)
        updateView()

        return binding.root
    }

    private fun updateView(){
        if(songId == null) return
        val context = this.context?: return
        val songData: SongData = viewModel.getSong(songId!!)
        binding.apply {
            titleTV.text = songData.name
            authorTV.text = songData.author


            val picture = songData.getAlbumPicture()
            if(picture != null)
            {
                val bitmap = BitmapFactory.decodeByteArray(picture, 0, picture.size)
                imageView.setImageBitmap(bitmap)
            }
            playB.setOnClickListener { playPause() }
            prevB.setOnClickListener { changeSong(Math.floorMod(songId!! - 1, viewModel.getSongs().size)) }
            nextB.setOnClickListener { changeSong(Math.floorMod(songId!! + 1, viewModel.getSongs().size)) }
            stopB.setOnClickListener { stopSong() }
            plus10.setOnClickListener {setMusicTo(mediaPlayer.currentPosition + 10000)}
            minus10.setOnClickListener {setMusicTo(mediaPlayer.currentPosition - 10000)}
            initializeSong()
            playPause()
        }
    }


    private fun setMusicTo(progress: Int){
        var actualProgress:Int = 0
        actualProgress = Math.min(mediaPlayer.duration, progress)
        actualProgress = Math.max(0, progress)
        mediaPlayer.seekTo(actualProgress)
    }

    private fun playPause(){
        binding.apply {
            if(mediaPlayer.isPlaying){
                mediaPlayer.pause()
                playB.text = "play"
            }
            else{
                mediaPlayer.start()
                playB.text = "pause"
            }
        }
    }

    private fun stopSong(){
        mediaPlayer.reset()
        binding.playB.text = "play"
        initializeSong()
    }

    private fun initializeSong(){
        mediaPlayer = MediaPlayer.create(this.context, Uri.parse(songData.path))
    }

    private fun changeSong(songId: Int){
        mediaPlayer.release()
        val action = SongDetailsFragmentDirections.actionSongDetailsFragmentSelf(songId)
        NavHostFragment.findNavController(this).navigate(action)
    }
}