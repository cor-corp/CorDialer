package com.corcorp.cordialer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Locale;

public class Recents extends AppCompatActivity {

    Button btnDelete;
    EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recents);
        init();

        btnDelete = findViewById(R.id.buttonDelete);

        input = findViewById(R.id.editText);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.recents);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.favorites:
                        startActivity(new Intent(getApplicationContext()
                                , Favorites.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.recents:
                        return true;

                    case R.id.contacts:
                        startActivity(new Intent(getApplicationContext()
                                , Contacts.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;


            }
        });

    }


    public void clickDialer(View view) {
        Intent intent = new Intent(Recents.this, Dialer.class);
        startActivity(intent);


    }

    public void delete(View v) {
        input.setText("");
    }

// TEST MIC
    private void init() {
        input = findViewById(R.id.editText);

    }

    public void clickMic(View view) {
        Intent intent = new Intent (RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        startActivityForResult(intent, 10);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case 10:
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    input.setText(text.get(0));
                    break;
            }
        }
//            else {
//                Toast.makeText(getApplicationContext(), "Faild to recognize spech!", Toast.LENGTH_LONG).show();
//            }
    }
}


