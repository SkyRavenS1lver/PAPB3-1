package com.example.myapplication;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import java.text.DateFormat;
import java.util.Date;

/**
 * Implementation of App Widget functionality.
 */
// file widget a info di folder xml untuk otak atik widget
    // di andro support min 30 min update, kalau lebih kecil akan dipaksa menjadi 30 min.
public class WidgetA extends AppWidgetProvider {
    private static final String sharedPrefFile = "com.example.myapplication";
    private static final String COUNT_KEY = "count";


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

//        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_a);
//        views.setTextViewText(R.id.appwidget_text, widgetText);
        // refer ke widget yang dibuat, content dari mana
        views.setTextViewText(R.id.appwidget_id, String.valueOf(appWidgetId));

        SharedPreferences preferences = context.getSharedPreferences(sharedPrefFile,0);
        int count = preferences.getInt(COUNT_KEY+appWidgetId, 0);
        count++;
        views.setTextViewText(R.id.appwidget_counter, String.valueOf(count));
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(COUNT_KEY+appWidgetId, count);
        editor.apply();
        String dateString = DateFormat.getTimeInstance().format(new Date());
        views.setTextViewText(R.id.appwidget_update, dateString);

        Intent intentUpdate = new Intent(context, WidgetA.class);
        intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] idArray = new int[]{appWidgetId};

        intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,appWidgetId,intentUpdate,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
        views.setOnClickPendingIntent(R.id.button_update, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
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