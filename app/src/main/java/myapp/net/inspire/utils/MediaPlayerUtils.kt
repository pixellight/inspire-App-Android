package myapp.net.inspire.utils

import android.content.Context
import android.media.MediaPlayer


/**
 * Created by QPay on 2/14/2019.
 */
class MediaPlayerUtils {

    companion object {
        var mediaPlayer: MediaPlayer? = null
        fun playWaterSound(context: Context, uri: String) {

            mediaPlayer = android.media.MediaPlayer.create(context, uri.toInt())
            if (mediaPlayer!!.isPlaying) {
                mediaPlayer!!.stop()
                mediaPlayer!!.reset()
                mediaPlayer!!.release()
            }
            mediaPlayer!!.start()
            mediaPlayer!!.setOnCompletionListener(MediaPlayer.OnCompletionListener { mp ->
                mp.stop()
                mp.reset()
                mp.release()
            })


        }


        fun stopPlaying() {
            try {
                if (mediaPlayer != null) {
                    mediaPlayer!!.stop()
                    mediaPlayer!!.release()
                    mediaPlayer = null
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}