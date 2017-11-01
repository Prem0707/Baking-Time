package fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prem.android.bakingtime.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepsToMakeRecipe extends Fragment {


    public StepsToMakeRecipe() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_steps_to_make_recipe, container, false);
    }

}
