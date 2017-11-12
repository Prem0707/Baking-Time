package adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.prem.android.bakingtime.R;
import com.prem.android.bakingtime.activities.IngredientActivity;

import java.util.ArrayList;

import extras.RecipeImageProvider;
import models.Recipe;

import static android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * Created by Prem on 29-10-2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {


    private ArrayList<Recipe> mRecipeDetails;
    private final RecyclerViewClickListener mClickHandler;
    private Context mContext;


    public interface RecyclerViewClickListener {
        void onRecipeClick(int positionOfSelectedRecipe);
    }

    public RecipeAdapter(RecyclerViewClickListener mClickHandler, Context context) {
        this.mClickHandler = mClickHandler;
        this.mContext = context;
    }

    /**
     * Called when RecyclerView needs a new of the given type to represent an item.
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     */
    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_recipe_view, parent, false);
        return new RecipeViewHolder(view);
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
    public void onBindViewHolder(RecipeViewHolder holder, final int position) {

        Recipe currentRecipe = mRecipeDetails.get(position);
        String recipeName = currentRecipe.getName();
        int servings = currentRecipe.getServings();

        holder.mRecipeName.setText(recipeName);
        holder.mRecipeServing.setText("Servings:" + servings);
        holder.mRecipeImage.setImageBitmap(RecipeImageProvider.provideRecipeImage(mContext.getResources(), position));

        holder.mIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // handle for ingredients
                final int mPosition = position;
                Intent intent = new Intent(mContext, IngredientActivity.class);
                intent.putExtra("RECIPE_INGREDIENTS", (Parcelable) mRecipeDetails.get(mPosition).getIngredients());
                mContext.startActivity(intent);
            }
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if (mRecipeDetails != null) {
            return mRecipeDetails.size();
        } else {
            return 0;
        }
    }

    /**
     * A Simple ViewHolder for the RecyclerView
     */
    public class RecipeViewHolder extends ViewHolder {

        CardView mCardView;
        ImageView mRecipeImage;
        TextView mRecipeName;
        TextView mRecipeServing;
        Button mIngredients;

        private RecipeViewHolder(View itemView) {
            super(itemView);

            mRecipeImage = itemView.findViewById(R.id.recipe_photo);
            mRecipeName = itemView.findViewById(R.id.recipe_name);
            mRecipeServing = itemView.findViewById(R.id.recipe_serving);
            mCardView = itemView.findViewById(R.id.recipe_cardview);
            mIngredients = itemView.findViewById(R.id.goes_to_ingredients);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickHandler.onRecipeClick(getAdapterPosition());
                }
            });
        }
    }

    public void setDataset(ArrayList<Recipe> dataset) {
        this.mRecipeDetails = dataset;
        notifyDataSetChanged();
    }
}
