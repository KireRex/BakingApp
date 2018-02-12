package com.scheffer.erik.bakingapp.fragments

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.scheffer.erik.bakingapp.R
import com.scheffer.erik.bakingapp.models.Step
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.step_detail.*

const val PLAYER_POSITION_KEY = "player-position"

class StepDetailFragment : Fragment() {

    lateinit var step: Step
    private var exoPlayer: SimpleExoPlayer? = null
    private var playerPosition = C.TIME_UNSET

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        step = arguments.getParcelable(STEP_EXTRA_KEY)
        activity.actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.step_detail, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        step_detail_text.text = step.description
        if (step.thumbnailURL.isEmpty()) {
            step_image.visibility = View.GONE
        } else {
            step_image.visibility = View.VISIBLE
            Picasso.with(context)
                    .load(step.thumbnailURL)
                    .into(step_image)
        }

        playerPosition = savedInstanceState?.getLong(PLAYER_POSITION_KEY, C.TIME_UNSET)
                ?: C.TIME_UNSET

        loadVideo()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(PLAYER_POSITION_KEY, playerPosition)
    }

    private fun loadVideo() {
        if (exoPlayer == null) {
            exoPlayer = ExoPlayerFactory.newSimpleInstance(DefaultRenderersFactory(activity),
                                                           DefaultTrackSelector(),
                                                           DefaultLoadControl())

            step_video.player = exoPlayer
            if (playerPosition != C.TIME_UNSET) {
                exoPlayer!!.seekTo(playerPosition)
            }
            exoPlayer!!.prepare(
                    ExtractorMediaSource(Uri.parse(step.videoURL),
                                         DefaultDataSourceFactory(
                                                 this.activity,
                                                 Util.getUserAgent(this.activity,
                                                                   "BakingApp")),
                                         DefaultExtractorsFactory(), null, null))
            exoPlayer!!.playWhenReady = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    override fun onPause() {
        super.onPause()
        releasePlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    private fun releasePlayer() {
        exoPlayer?.let {
            playerPosition = exoPlayer!!.currentPosition
            exoPlayer!!.stop()
            exoPlayer!!.release()
            exoPlayer = null
        }
    }

    companion object {
        const val STEP_EXTRA_KEY = "step"
    }
}
