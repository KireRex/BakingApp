package com.scheffer.erik.bakingapp;

import android.app.ActionBar;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
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
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailFragment extends Fragment {
    public static final String STEP_EXTRA_KEY = "step";
    private static final String PLAYER_POSITION_KEY = "player-position";

    private Step step;
    private SimpleExoPlayer exoPlayer;
    private long playerPosition = C.TIME_UNSET;

    @BindView(R.id.step_video)
    SimpleExoPlayerView playerView;

    @BindView(R.id.step_detail_text)
    TextView stepDetailText;

    @BindView(R.id.step_image)
    ImageView stepImage;

    public StepDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        step = getArguments().getParcelable(STEP_EXTRA_KEY);
        Activity activity = this.getActivity();
        ActionBar appBarLayout = activity.getActionBar();
        if (appBarLayout != null) {
            appBarLayout.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_detail, container, false);
        ButterKnife.bind(this, rootView);

        stepDetailText.setText(step.getDescription());
        if (step.getThumbnailURL() == null || step.getThumbnailURL().isEmpty()) {
            stepImage.setVisibility(View.GONE);
        } else {
            stepImage.setVisibility(View.VISIBLE);
            Picasso.with(getContext())
                   .load(step.getThumbnailURL())
                   .into(stepImage);
        }

        if (savedInstanceState != null) {
            playerPosition = savedInstanceState.getLong(PLAYER_POSITION_KEY, C.TIME_UNSET);
        }

        loadVideo();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            playerPosition = savedInstanceState.getLong(PLAYER_POSITION_KEY, C.TIME_UNSET);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (exoPlayer != null) {
            outState.putLong(PLAYER_POSITION_KEY, playerPosition);
        }
    }

    private void loadVideo() {
        if (exoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this.getActivity()),
                                                           trackSelector,
                                                           loadControl);
            playerView.setPlayer(exoPlayer);
            if (playerPosition != C.TIME_UNSET) {
                exoPlayer.seekTo(playerPosition);
            }
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

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            playerPosition = exoPlayer.getCurrentPosition();
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }
}
