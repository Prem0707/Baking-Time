package fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.prem.android.bakingtime.R;

/**
 * This fragment will contain the videos of steps along with detailed Step of recipe preparation .
 */
public class StepsDetailFragment extends Fragment {


    private TextView mDetailedTextView;
    private String mVideoURL;
    private String mDescription;
    public StepsDetailFragment() {
        // Required empty public constructor
    }

    private SimpleExoPlayerView mPlayerview;
    private SimpleExoPlayer mExoPlayer;
    private Context context;

    public void  provideContext(Context context){
        this.context = context;
    }

    public void provideData(String videoURL, String detailedDescription){
        this.mVideoURL = videoURL;
        this.mDescription = detailedDescription;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_steps_detail, container, false);
        mDetailedTextView = (TextView) view.findViewById(R.id.detailed_description);
        mDetailedTextView.setText(mDescription);

        if(!mVideoURL.isEmpty()){
            setupExoPlayer();
        }
        return view;
    }

    private void setupExoPlayer(){

        // 1. Create a default TrackSelector
        Handler mainHandler = new Handler();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

         // 2. Create the player
        SimpleExoPlayer player =
                ExoPlayerFactory.newSimpleInstance(context, trackSelector);

        // Bind the player to the view.
        mPlayerview.setPlayer(player);
    }
}
