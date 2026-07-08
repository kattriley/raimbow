package com.cockroach.dance

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import android.widget.VideoView
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val videoView = findViewById<VideoView>(R.id.videoView)
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        val videoFile = findVideoFile()
        if (videoFile != null) {
            videoView.setVideoURI(Uri.fromFile(videoFile))
            videoView.start()
        } else {
            Toast.makeText(this, "Video file not found! Copy the MP4 to your Downloads folder.", Toast.LENGTH_LONG).show()
        }
    }

    private fun findVideoFile(): File? {
        val downloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val dcim = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
        val movies = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)

        for (dir in listOf(downloads, dcim, movies)) {
            val files = dir.listFiles { f -> f.name.endsWith(".mp4", ignoreCase = true) }
            if (files != null) {
                for (f in files) {
                    if (f.name.contains("cockroach", ignoreCase = true) || f.name.contains("rainbow", ignoreCase = true)) {
                        return f
                    }
                }
                if (files.isNotEmpty()) return files.first()
            }
        }

        val extDirs = getExternalFilesDirs(null)
        for (dir in extDirs) {
            val files = dir.listFiles { f -> f.name.endsWith(".mp4", ignoreCase = true) }
            if (files != null && files.isNotEmpty()) return files.first()
        }

        return null
    }
}
