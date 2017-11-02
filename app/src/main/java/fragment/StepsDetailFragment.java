package fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prem.android.bakingtime.R;

/**
 * This fragment will contain the videos of steps along with detailed Step of recipe preparation .
 */
public class StepsDetailFragment extends Fragment {


    public StepsDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_steps_detail, container, false);
    }

}
