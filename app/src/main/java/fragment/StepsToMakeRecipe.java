package fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prem.android.bakingtime.R;
import com.prem.android.bakingtime.activities.DetailSteps;

import java.util.ArrayList;

import adapters.RecipeStepsAdapter;
import extras.BasicUtility;
import models.Step;
import utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepsToMakeRecipe extends Fragment implements RecipeStepsAdapter.RecViewListener {

    private ArrayList<Step> mSteps;
    private RecyclerView mRecyclerView;
    private RecipeStepsAdapter mRecipeStepsAdapter;
    OnHeadlineSelectedListener mCallback;
    private Step stepsOfMaking;

    public StepsToMakeRecipe() {
        // Required empty public constructor
    }

    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(Step stepsOfRecipe);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (getArguments() != null) {
            mSteps = getArguments().getParcelableArrayList("DATA_TO_STEP_TO_MAKE_RECIPE");
        } else {
            mSteps = savedInstanceState.getParcelableArrayList("STEPS_ARRAY");
        }
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_steps_to_make_recipe, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.ingredient_recyclerview);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecipeStepsAdapter = new RecipeStepsAdapter(this);
        return view;
    }

    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecipeStepsAdapter.setDataset(mSteps);
        if (savedInstanceState != null) {
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable("STEPS_RECYCLER_LAYOUT");
            mRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
        mRecyclerView.setAdapter(mRecipeStepsAdapter);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("STEPS_ARRAY", mSteps);
        //save the current recycler view position
        outState.putParcelable("STEPS_RECYCLER_LAYOUT", mRecyclerView.getLayoutManager().onSaveInstanceState());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStepClicked(int positionOfSelectedStep) {
        stepsOfMaking = mSteps.get(positionOfSelectedStep);
        if (!BasicUtility.tabletMode()) {
            Intent toIngredientAndSteps = new Intent(getActivity(), DetailSteps.class);
            toIngredientAndSteps.putExtra(Constants.STEP_TO_MAKE, (Parcelable) stepsOfMaking);
            startActivity(toIngredientAndSteps);
        } else {
            try {
                mCallback = (OnHeadlineSelectedListener) getActivity();
            } catch (ClassCastException e) {
                throw new ClassCastException(getActivity().toString()
                        + " must implement OnHeadlineSelectedListener");
            }
        }
    }
}
