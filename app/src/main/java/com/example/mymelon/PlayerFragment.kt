package com.example.mymelon

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.mymelon.databinding.FragmentPlayerBinding
import com.example.mymelon.service.MusicDTO
import com.example.mymelon.service.MusicService
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class PlayerFragment:Fragment(R.layout.fragment_player) {

    private var binding: FragmentPlayerBinding? = null
    private var isWatchingPlayListView = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentPlayerBinding = FragmentPlayerBinding.bind(view)
        binding = fragmentPlayerBinding

        getVideoListFromServer()
        initPlayListButton(fragmentPlayerBinding)
    }

    private fun initPlayListButton(fragmentPlayerBinding: FragmentPlayerBinding) {
        fragmentPlayerBinding.playlistImageView.setOnClickListener {
            fragmentPlayerBinding.playerViewGroup.isVisible = isWatchingPlayListView
            fragmentPlayerBinding.playListViewGroup.isVisible = isWatchingPlayListView

            isWatchingPlayListView = !isWatchingPlayListView
        }
    }

    private fun getVideoListFromServer(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(MusicService::class.java)
            .also {
                it.listMusics()
                    .enqueue(object : Callback<MusicDTO>{
                        override fun onResponse(
                            call: Call<MusicDTO>,
                            response: Response<MusicDTO>
                        ) {
                            Log.d("PlayerFragment", "${response.body()}")

                            response.body()?.let {
                                val modelList = it.musics.mapIndexed { index, musicEntity ->
                                    musicEntity.mapper(index.toLong())
                                }
                            }
                        }

                        override fun onFailure(call: Call<MusicDTO>, t: Throwable) {

                        }


                    })
            }
    }

    companion object {
        fun newInstance(): PlayerFragment{
            return PlayerFragment()
        }
    }
}