package com.example.musicplayer.songlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHost
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.SongData
import com.example.musicplayer.SongsViewModel
import com.example.musicplayer.SongsViewModelFactory
import com.example.musicplayer.databinding.SongsRvFragmentBinding

class SongsRVFragment :Fragment(){
    private lateinit var viewModel: SongsViewModel
    private lateinit var binding: SongsRvFragmentBinding

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: RecyclerView.Adapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SongsRvFragmentBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, SongsViewModelFactory(this.context)).get(SongsViewModel::class.java)

        viewManager = LinearLayoutManager(this.context)
        viewAdapter = SongsRVAdapter(viewModel, ::onDetails)
        binding.list.apply {
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        return binding.root
    }

    private fun onDetails(id: Int){
        Log.i("OnDetails: id", id.toString())
        val action = SongsRVFragmentDirections.actionSongsRVFragmentToSongDetailsFragment(id)
        NavHostFragment.findNavController(this).navigate(action)
    }
}