package fragment;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

/**
 * This fragment will contain the videos of steps along with detailed Step of recipe preparation .
 */
public class StepsDetailFragment extends Fragment {


    private TextView mDetailedTextView;
    private String mVideoURL;
    private String mDescription;
    private SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer player;
    private String mStepUrl;
    private ImageView mStepThumbnail;
    private Context context;
    private long currentPlayerPosition = 0;

    public StepsDetailFragment() {
        // Required empty public constructor
    }


    public void provideContext(Context context) {
        this.context = context;
    }

    public void provideData(String videoURL, String detailedDescription, String imageThumbnail) {
        this.mVideoURL = videoURL;
        this.mDescription = detailedDescription;
        this.mStepUrl = imageThumbnail;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_steps_detail, container, false);
        mPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.playerView);
        mDetailedTextView = (TextView) view.findViewById(R.id.detailed_description);
        mStepThumbnail = (ImageView) view.findViewById(R.id.stepThumbnail);

        if (savedInstanceState != null) {
            currentPlayerPosition = savedInstanceState.getLong("PLAYER_POSITION");
            mDescription = savedInstanceState.getString("STEP_DESCRIPTION");
            mStepUrl = savedInstanceState.getString("IMAGE_URL");
            mVideoURL = savedInstanceState.getString("VIDEO_URL");
        }
        mDetailedTextView.setText(mDescription);

        //Show only if there is thumbnail URL
        if (mStepUrl != null) {
            Uri imageUrl = Uri.parse(mStepUrl);

            Picasso.with(context).load(imageUrl).placeholder(R.drawable.step_thambnail).into(mStepThumbnail);
            mStepThumbnail.setVisibility(View.VISIBLE);
        }

        if (!mVideoURL.isEmpty()) {
            setupExoPlayer();
        }
        return view;
    }


    private void setupExoPlayer() {

        // 1. Create a default TrackSelector
        Handler mainHandler = new Handler();

        // Measures bandwidth during playback. Can be null if not required.
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        // 2. Create the player
        player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);

        // Bind the player to the view.
        mPlayerView.setPlayer(player);

        // Measures bandwidth during playback. Can be null if not required.
        DefaultBandwidthMeter bandwidthMeasure = new DefaultBandwidthMeter();
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, "Baking Time"), bandwidthMeasure);
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
            outState.putString("VIDEO_URL", mVideoURL);
            outState.putString("STEP_DESCRIPTION", mDescription);
            outState.putString("IMAGE_URL", mStepUrl);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
