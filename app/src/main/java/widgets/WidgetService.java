package widgets;

import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.prem.android.bakingtime.R;

import java.util.List;

import models.Ingredient;
import models.Recipe;
import sharedpreference.UserPreference;

/**
 * Created by Prem on 28-12-2017.
 */

public class WidgetService extends RemoteViewsService {
    List<Ingredient> ingredients;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            @Override
            public void onCreate() {
                initData();
            }

            @Override
            public void onDataSetChanged() {
                initData();
            }

            @Override
            public void onDestroy() {

            }

            @Override
            public int getCount() {
                return ingredients.size();
            }

            @Override
            public RemoteViews getViewAt(int position) {

                Ingredient ingredient = ingredients.get(position);
                RemoteViews view = new RemoteViews(getApplicationContext().getPackageName(), R.layout.ingredient_list_content_for_widgets);

                view.setTextViewText(R.id.ingredient, ingredient.getIngredient() + "-" + ingredient.getQuantity()
                                                       + ingredient.getMeasure() );


                return view;
            }

            @Override
            public RemoteViews getLoadingView() {
                return null;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }

    private void initData() {
        Recipe recipes = UserPreference.getSharedPref(this.getApplicationContext());
        if (recipes != null) {
            ingredients = recipes.getIngredients();
        }
    }
}

