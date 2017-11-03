package fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import models.Recipe;
import models.Step;
import utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepsToMakeRecipe extends Fragment implements RecipeStepsAdapter.RecViewListener{

    Context mContext;
    private ArrayList<Step> mSteps;

    public StepsToMakeRecipe() {
        // Required empty public constructor
    }

    public void provideRecipeDetails(Recipe recipeDetails){
        this.mSteps = recipeDetails.getSteps();
    }
    public void provideContext(Context context){
        this.mContext = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_steps_to_make_recipe, container, false);
        RecyclerView mRecyclerview = (RecyclerView) view.findViewById(R.id.ingredient_recyclerview);


        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(mLinearLayoutManager);
        RecipeStepsAdapter mRecipeStepsAdapter = new RecipeStepsAdapter(this);
        mRecipeStepsAdapter.setDataset(mSteps);
        mRecipeStepsAdapter.provideContext(getContext());
        mRecyclerview.setAdapter(mRecipeStepsAdapter);
        return view;
    }

    @Override
    public void onStepClicked(int positionOfSelectedStep) {
        Intent toIngredientAndSteps = new Intent(mContext, DetailSteps.class);
        toIngredientAndSteps.putExtra(Constants.STEP_TO_MAKE, (Parcelable) mSteps.get(positionOfSelectedStep));
        startActivity(toIngredientAndSteps);
    }
}
