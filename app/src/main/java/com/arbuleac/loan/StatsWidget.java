package com.arbuleac.loan;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;

import com.arbuleac.loan.provider.StatsContract;

import timber.log.Timber;

/**
 * @since 3/25/16.
 */
public class StatsWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        Timber.d("Load widge update");
        // To prevent any ANR timeouts, we perform the update in a service
        context.startService(new Intent(context, UpdateService.class));
    }

    public static class UpdateService extends IntentService {

        public UpdateService() {
            super("UpdateService");
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            Timber.d("Updating widget started");
            Cursor data = getContentResolver().query(StatsContract.StatsEntry.CONTENT_URI, null, null, null, StatsContract.StatsEntry.COLUMN_VALUE + " DESC");
            if (data == null) {
                update("No updates found!");
                return;
            }
            if (data.moveToFirst()) {
                String lastUpdate = data.getString(data.getColumnIndex(StatsContract.StatsEntry.COLUMN_VALUE));
                update(lastUpdate);
            }
            data.close();
        }

        private void update(String message) {
            Timber.d("Updating widget %s", message);
            RemoteViews updateViews = new RemoteViews(getPackageName(), R.layout.widget_message);
            updateViews.setTextViewText(R.id.message, message);

            // Push update for this widget to the home screen
            ComponentName thisWidget = new ComponentName(this, StatsWidget.class);
            AppWidgetManager manager = AppWidgetManager.getInstance(this);
            manager.updateAppWidget(thisWidget, updateViews);
        }
    }
}
