package widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.prem.android.bakingtime.R;

import java.util.ArrayList;

import models.Ingredient;
import models.Recipe;

/**
 * WidgetDataProvider acts as the adapter for the collection view widget,
 * providing RemoteViews to the widget in the getViewAt method.
 */

public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory{

    private static final String TAG = "WidgetDataProvider";
    private Recipe mRecipe;
    private String recipeName;
    private ArrayList<Ingredient> mCollection;
    //private List<Ingredient> mCollection = mRecipe != null ? mRecipe.getIngredients() : null;
    private Context mContext = null;



    public WidgetDataProvider(Recipe recipe) {
        this.mRecipe = recipe;
    }

    public WidgetDataProvider(Context mContext, Intent intent) {
        this.mContext = mContext;
    }

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
        return mCollection.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
          RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_list_widget);
        //RemoteViews remoteviews = new RemoteViews(mContext.getPackageName(), android.R.layout.simple_list_item_1);
       // remoteviews.setTextViewText(android.R.id.text1, mCollection.get(i));
          remoteViews.setTextViewText(R.id.widget_title, recipeName);
          remoteViews.setTextViewText(android.R.id.text1, (CharSequence) mCollection.get(i));
        return  remoteViews;
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

    private void initData(){
        //mCollection.clear();
        this.recipeName = mRecipe.getName();
        Toast.makeText(mContext,"Name of Recipe is:" + recipeName, Toast.LENGTH_LONG).show();
        this.mCollection = mRecipe.getIngredients();
    }
}
