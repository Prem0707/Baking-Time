package fragment;


import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.prem.android.bakingtime.R;
import com.squareup.picasso.Picasso;

import models.Step;

/**
 * This fragment will contain the videos of steps along with detailed Step of recipe preparation .
 */
public class StepsVideoFragment extends Fragment {


    private TextView mDetailedTextView;

    private SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer player;

    private ImageView mStepThumbnail;
    private long currentPlayerPosition = 0;
    private Step recipeSteps;

    public StepsVideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_steps_detail, container, false);
        mPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.playerView);
        mDetailedTextView = (TextView) view.findViewById(R.id.detailed_description);
        mStepThumbnail = (ImageView) view.findViewById(R.id.stepThumbnail);

        if (savedInstanceState != null) {
            currentPlayerPosition = savedInstanceState.getLong("PLAYER_POSITION");
            recipeSteps = savedInstanceState.getParcelable("RECIP_STEPS");
        } else {
            if (getArguments() != null) {
                recipeSteps = getArguments().getParcelable("DATA_SENT_TO_VIDEO_FRAG");
            }
        }

        if(recipeSteps != null)
        mDetailedTextView.setText(recipeSteps.getDescription());

        Uri imageUrl = Uri.parse(recipeSteps.getThumbnailURL());
        if (imageUrl != null) {
            Picasso.with(getActivity()).load(imageUrl)
                    .placeholder(R.drawable.step_thambnail)
                    .into(mStepThumbnail);
        }

        String mVieoURL = recipeSteps.getVideoURL();
        if (mVieoURL != null) {
            setupExoPlayer(mVieoURL);
        } else {
            Toast.makeText(getActivity(), "Video URL is empty", Toast.LENGTH_LONG).show();
        }

        return view;
    }


    private void setupExoPlayer(String mVideoURL) {

        // 1. Create a default TrackSelector
        Handler mainHandler = new Handler();

        // Measures bandwidth during playback. Can be null if not required.
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        // 2. Create the player
        player = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);

        // Bind the player to the view.
        mPlayerView.setPlayer(player);

        // Measures bandwidth during playback. Can be null if not required.
        DefaultBandwidthMeter bandwidthMeasure = new DefaultBandwidthMeter();
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getActivity(), "Baking Time"), bandwidthMeasure);
        // Produces Extractor instances for parsing the media data.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource(Uri.parse(mVideoURL),
                dataSourceFactory, extractorsFactory, null, null);
        // Prepare the player with the source.
        player.prepare(videoSource);

        if (currentPlayerPosition != 0)
            player.seekTo(currentPlayerPosition);

        //setPlayWhenReady can be used to start and pause playback
        player.setPlayWhenReady(true);
    }

    @Override
    public void onStop() {
        super.onStop();

        if (player != null) {
            player.stop();
            player.release();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (player != null) {
            outState.putLong("PLAYER_POSITION", player.getCurrentPosition());
            outState.putParcelable("RECIP_STEPS", (Parcelable) recipeSteps);

        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
