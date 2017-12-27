package widgets;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import models.Recipe;

/**
 * Created by Prem on 27-12-2017.
 */

public class AppWidgetIntentService extends IntentService {

    public static final String RECIPE_WIDGET_ACTION_UPDATE = "action_update";
    private static final String BUNDLE_RECIPE_WIDGET_DATA = "recipe_widget_data";

    public AppWidgetIntentService() {
        super("RecipeWidgetService");
    }

    public static void startActionUpdateRecipeWidgets(Context context, Recipe recipe) {
        Intent intent = new Intent(context, AppWidgetIntentService.class);
        intent.setAction(RECIPE_WIDGET_ACTION_UPDATE);
        intent.putExtra(BUNDLE_RECIPE_WIDGET_DATA, recipe);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (RECIPE_WIDGET_ACTION_UPDATE.equals(action) &&
                    intent.getParcelableExtra(BUNDLE_RECIPE_WIDGET_DATA) != null) {
                handleActionUpdateWidgets((Recipe)intent.getParcelableExtra(BUNDLE_RECIPE_WIDGET_DATA));
            }
        }
    }

    private void handleActionUpdateWidgets(Recipe recipe) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, AppWidgetIntentService.class));
        IngredientWidget.updateRecipeWidgets(this, appWidgetManager, recipe, appWidgetIds);
    }
}
