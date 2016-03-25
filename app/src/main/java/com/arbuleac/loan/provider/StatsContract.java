package com.arbuleac.loan.provider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class StatsContract {

    public static final String CONTENT_AUTHORITY = "com.arbuleac.loan";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_STATS = "stats";

    public static final class StatsEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_STATS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STATS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STATS;

        public static final String TABLE_NAME = "stats";

        public static final String COLUMN_VALUE = "value";

        public static Uri buildStatsUri(long id) {
            return CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
        }
    }
}
