package adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prem.android.bakingtime.R;

import java.util.ArrayList;

import models.Ingredient;

/**
 * Created by Prem on 12-11-2017.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.RecipeIngredientsHolder> {

    private Context mContext;
    private ArrayList<Ingredient> mIngredientsData;


    public IngredientsAdapter(Context context) {
        mContext = context;
    }

    /**
     * Called when RecyclerView needs a new  of the given type to represent an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @Override
    public RecipeIngredientsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredients_of_single_recipe,
                parent, false);
        return new RecipeIngredientsHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the  to reflect the item at the given position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    boolean[] checkedStatus = new boolean[15];
    @Override
    public void onBindViewHolder(final RecipeIngredientsHolder holder, int position) {

        Ingredient mIngredient = mIngredientsData.get(position);
        String ingredientValue = mIngredient.getIngredient();
        String measure = mIngredient.getMeasure();
        String quantity = Double.toString(mIngredient.getQuantity());
        String textToShow = ingredientValue + " -   " + quantity + measure;

        holder.mIngName.setText(textToShow);

        //holder.bind should not trigger onCheckedChanged, it should just update UI
       // holder.mCheckBox.setOnCheckedChangeListener(null);

        //holder.bind(position);

//        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    checkedStatus[holder.getAdapterPosition()] = true;
//                } else {
//                    checkedStatus[holder.getAdapterPosition()] = false;
//                }
//            }
//        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if (mIngredientsData != null) {
            return mIngredientsData.size();
        } else {
            return 0;
        }
    }


    /**
     * A Simple ViewHolder for the RecyclerView
     */
    class RecipeIngredientsHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView mIngName;
        //CheckBox mCheckBox;

        private RecipeIngredientsHolder(View itemView) {
            super(itemView);

            mIngName = itemView.findViewById(R.id.ingredients_name);
            cardView = itemView.findViewById(R.id.ingredient_cardView);
           // mCheckBox = itemView.findViewById(R.id.checkBox);
        }

//        public void bind(int position) {
//            boolean checked = checkedStatus[position];
//            if (checked) {
//                mCheckBox.setChecked(false);
//            } else {
//                mCheckBox.setChecked(true);
//            }
//        }
    }

    public void setIngredientsData(ArrayList<Ingredient> dataset) {
        this.mIngredientsData = dataset;
        notifyDataSetChanged();
    }
}
