package com.example.dai.browserhistory;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.Browser;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    String[] proj = {BookmarkColumns.TITLE, BookmarkColumns.URL};
    String[] urls;
    public static final Uri BOOKMARKS_URI =
            Uri.parse("content://browser/bookmarks");
    public static final Uri CHROME_URI = Uri.parse("content://com.android.chrome.browser/bookmarks");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);


        String sel = BookmarkColumns.BOOKMARK + " = 0";
        Cursor cursor = getContentResolver().query(BOOKMARKS_URI, proj, sel, null, null);
        if (cursor == null) {
            return;
        }

        int cnt = cursor.getCount();
        String[] titles = new String[cnt];
        urls = new String[cnt];
        int i = 0;
        Log.i("DEBUG", "Cnt " + cnt);
        while (cnt - i > 0) {
            cursor.moveToNext();
            titles[i] = cursor.getString(cursor.getColumnIndex(proj[0]));
            urls[i] = cursor.getString(cursor.getColumnIndex(proj[1]));

            ++i;

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, titles);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri webPage = Uri.parse(urls[position]);
                startActivity(new Intent(Intent.ACTION_VIEW, webPage));
            }
        });
    }

    public static class BookmarkColumns implements BaseColumns {
        public static final String URL = "url";
        public static final String VISITS = "visits";
        public static final String DATE = "date";
        public static final String BOOKMARK = "bookmark";
        public static final String TITLE = "title";
        public static final String CREATED = "created";
        public static final String FAVICON = "favicon";

        public static final String THUMBNAIL = "thumbnail";

        public static final String TOUCH_ICON = "touch_icon";

        public static final String USER_ENTERED = "user_entered";
    }
}
