package ominext.com.contentprovideraccessor;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static final String TABLE_VICS = "vics";
    static final String COLUMN_ID = "_ID";
    static final String COLUMN_NAME = "name";

    static final Uri CONTENT_URI = Uri.parse("content://com.teamtreehouse.contentproviders.provider/" + TABLE_VICS);

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        updateList();
    }

    public void updateList() {
        linearLayout.removeAllViews();

        Cursor cursor = getContentResolver().query(CONTENT_URI, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));

                TextView textView = getNewTextView(id, name);
                linearLayout.addView(textView);
            } while (cursor.moveToNext());
            cursor.close();
        }
    }

    private TextView getNewTextView(String id, String name) {
        TextView textView = new TextView(this);
        textView.setText(id + "  " + name);
        textView.setTextSize(24f);
        return textView;
    }
}
