package fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.prem.android.bakingtime.R;

import java.util.ArrayList;

import adapters.RecipeAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import interfaces.TaskCompleted;
import utils.AsyncTaskRecipe;

/**
 * A simple fragment which will contain the MainRecipe cards.
 */
public class SelectRecipe extends Fragment implements TaskCompleted{


    ArrayList<models.Recipe> recipeList;
    private RecipeAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    // required Constructor
    public SelectRecipe() { }

    @BindView(R.id.recipe_recyclerview) RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_recipe, container, false);
        ButterKnife.bind(this, view);

        //Initialisation of AsyncTask to get raw data
        AsyncTaskRecipe asyncTaskRecipe = new AsyncTaskRecipe(this);
        asyncTaskRecipe.execute();

        mRecyclerView = new RecyclerView(getContext());
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecipeAdapter(getContext());
        if(recipeList != null) {
            adapter.setDataset(recipeList);
        }else{
            Toast.makeText(getContext(), "recipeList is null", Toast.LENGTH_LONG).show();
        }
        mRecyclerView.setAdapter(adapter);
        return view;
    }

     //Called when the Fragment is visible to the user
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onTaskCompleted(ArrayList<models.Recipe> mRecipe) {
        this.recipeList = mRecipe;
    }
}
