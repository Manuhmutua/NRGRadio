package com.manuh.nrg_radio

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.net.toUri

class RadioService : Service() {
    private lateinit var player: MediaPlayer


    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        val url = "http://net1.citrus3.com:8552/;stream.mp3"
        player = MediaPlayer.create(this, url.toUri())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            player.setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
        }

        player.isLooping = true // Set looping
        player.setVolume(100f, 100f)
        player.setOnCompletionListener {
            it.release()
        }

    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if (::player.isInitialized) player.start()
        return START_STICKY
    }


    override fun onDestroy() {
        if (::player.isInitialized) player.stop()
        if (::player.isInitialized) player.release()
    }

    override fun onLowMemory() {

    }

}