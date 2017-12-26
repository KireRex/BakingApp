package com.scheffer.erik.bakingapp;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.scheffer.erik.bakingapp.models.Step;

public class StepDetailFragment extends Fragment {
    public static final String STEP_EXTRA_KEY = "step";

    private Step step;
    private SimpleExoPlayer exoPlayer;
    private SimpleExoPlayerView playerView;

    public StepDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        step = getArguments().getParcelable(STEP_EXTRA_KEY);
        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(step.getShortDescription());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_detail, container, false);

        ((TextView) rootView.findViewById(R.id.step_detail_text)).setText(step.getDescription());
        playerView = rootView.findViewById(R.id.step_video);

        loadVideo();
        return rootView;
    }

    private void loadVideo() {
        if (exoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this.getActivity()),
                                                           trackSelector,
                                                           loadControl);
            playerView.setPlayer(exoPlayer);
            exoPlayer.prepare(
                    new ExtractorMediaSource(Uri.parse(step.getVideoURL()),
                                             new DefaultDataSourceFactory(
                                                     this.getActivity(),
                                                     Util.getUserAgent(this.getActivity(),
                                                                       "BakingApp")),
                                             new DefaultExtractorsFactory(),
                                             null,
                                             null));
            exoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    private void releasePlayer() {
        exoPlayer.stop();
        exoPlayer.release();
        exoPlayer = null;
    }
}
