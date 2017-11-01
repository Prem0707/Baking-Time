package adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prem.android.bakingtime.R;

import java.util.ArrayList;

import models.Step;

/**
 * Created by Prem on 01-11-2017.
 */

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.RecipeStepsHolder> {


    private ArrayList<Step> mStepsArrayList;
    /**
     * Called when RecyclerView needs a new of the given type to represent an item.
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     */
    @Override
    public RecipeStepsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_recipe_view, parent, false);
        return new RecipeStepsHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the  to reflect the item at the given position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(RecipeStepsHolder holder, int position) {

        Step mStep = mStepsArrayList.get(position);
        holder.mRecipeIndex.setText(mStep.getId() + 1);
        holder.mRecipeStep.setText(mStep.getShortDescription());
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if(mStepsArrayList != null){
            return mStepsArrayList.size();
        }else {
            return 0;
        }
    }

    /**
     * A Simple ViewHolder for the RecyclerView
     */
    public class RecipeStepsHolder extends RecyclerView.ViewHolder {

        CardView mCardView;
        TextView mRecipeIndex;
        TextView mRecipeStep;


        private RecipeStepsHolder(View itemView) {
            super(itemView);

            mRecipeIndex = itemView.findViewById(R.id.step_index);
            mRecipeStep = itemView.findViewById(R.id.step_description);
            mCardView = itemView.findViewById(R.id.steps_cardview);
        }
    }
}
