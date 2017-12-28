package widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.prem.android.bakingtime.R;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidgets extends AppWidgetProvider {


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int i = 0; i < appWidgetIds.length; i++) {

            Intent intent = new Intent(context, WidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.putExtra("Random", Math.random() * 1000);
            // Add a random integer to stop the Intent being ignored.
            // This is needed for some API levels due to intent caching
            //intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widgets);
            views.setRemoteAdapter(R.id.widget_list, intent);


            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetIds[i], views);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds[i], R.id.widget_list);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
        ComponentName thisWidget = new ComponentName(context.getApplicationContext(), IngredientsWidgets.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}


