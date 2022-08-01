package com.example.guru_app


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView


class prepare_ex_stretching : YouTubeBaseActivity() {

    val videoId = "RpAH7ts--eY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prepare_ex_stretching)
        val youtubeView = findViewById<YouTubePlayerView>(R.id.youTubePlayerView_stretching)

        youtubeView.initialize("develop", object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider,
                player: YouTubePlayer,
                wasRestored: Boolean
            ) {
                if (!wasRestored) {
                    player.cueVideo(videoId)
                }
            }

            override fun onInitializationFailure(
                provider: YouTubePlayer.Provider?,
                result: YouTubeInitializationResult?
            ) {

            }
        })
    }
}