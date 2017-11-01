package fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prem.android.bakingtime.R;

import adapters.RecipeStepsAdapter;
import models.Recipe;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepsToMakeRecipe extends Fragment {

    Recipe mRecipeDetails;

    public StepsToMakeRecipe() {
        // Required empty public constructor
    }

    public void provideRecipeDetails(Recipe recipeDetails){
        this.mRecipeDetails = recipeDetails;
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
        RecipeStepsAdapter mRecipeStepsAdapter = new RecipeStepsAdapter();
        mRecipeStepsAdapter.setDataset(mRecipeDetails.getSteps());
        mRecipeStepsAdapter.provideContext(getContext());
        mRecyclerview.setAdapter(mRecipeStepsAdapter);
        return view;
    }



}
