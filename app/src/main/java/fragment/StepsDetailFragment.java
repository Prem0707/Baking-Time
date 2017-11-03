package fragment;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.prem.android.bakingtime.R;

import static com.prem.android.bakingtime.R.id.playerView;

/**
 * This fragment will contain the videos of steps along with detailed Step of recipe preparation .
 */
public class StepsDetailFragment extends Fragment {


    public StepsDetailFragment() {
        // Required empty public constructor
    }

    private SimpleExoPlayerView mPlayerview;
    private SimpleExoPlayer mExoPlayer;
    private Context context;

    public void  provideContext(Context context){
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_steps_detail, container, false);

        //Initialise the Player view;
        mPlayerview = (SimpleExoPlayerView) view.findViewById(playerView);
        //Initialise the Player
        //initialisePlayer(Uri.parse());
        return view;
    }

    /**
     * Initialize Exoplayer
     *
     * @param  mediaUri: the uri of sample to play
     */
    private void initialisePlayer(Uri mediaUri){
     if(mExoPlayer != null){
         //Create an instance of ExoPlayer
         TrackSelector trackSelector = new DefaultTrackSelector();
         LoadControl loadControl = new DefaultLoadControl();
         mExoPlayer = ExoPlayerFactory.newSimpleInstance(context,trackSelector, loadControl );
         mPlayerview.setPlayer(mExoPlayer);

         //Prepare the media Source
         String userAgent = Util.getUserAgent(context, "Baking Time");
         MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(context, userAgent),
                 new DefaultExtractorsFactory(), null, null);
         mExoPlayer.prepare(mediaSource);
         mExoPlayer.setPlayWhenReady(true);

     }
    }
}
