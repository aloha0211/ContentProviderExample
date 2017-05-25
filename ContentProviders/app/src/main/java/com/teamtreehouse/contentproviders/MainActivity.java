package com.teamtreehouse.contentproviders;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addButton = (Button) findViewById(R.id.addButton);
        final EditText editText = (EditText) findViewById(R.id.nameEditText);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText.getText().toString();
                if (name.length() > 0) {
                    addContact(name);
                    updateList();
                    editText.setText("");
                }
            }
        });

        updateList();
    }

    private void addContact(String name) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, name);
        Uri uri = getContentResolver().insert(MyContentProvider.CONTENT_URI, values);
        if (uri != null) {
            Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void updateList() {
        linearLayout.removeAllViews();

        Cursor cursor = getContentResolver().query(MyContentProvider.CONTENT_URI, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));

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