package com.corcorp.cordialer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.speech.RecognizerIntent;
import android.telecom.Call;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Recents extends AppCompatActivity {
    ArrayList<String> recents = new ArrayList<String>();


    Button btnDelete;
    EditText input;
    ListView miss_call;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recents);
        init();
//        TelephonyManager.listen(stateListener, PhoneStateListener.LISTEN_CALL_STATE);
        miss_call = (ListView) findViewById(R.id.miss_call);


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


    // SEARCHING WITH VOICE
    private void init() {
        input = findViewById(R.id.editText);
    }

    public void clickMic(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        startActivityForResult(intent, 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case 10:
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    input.setText(text.get(0));
                    break;
            }
        }
    }

    private void loadRecents() {
        ContentResolver contentResolver = getContentResolver();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Cursor cursor = contentResolver.query(CallLog.Calls.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {

                // получаем каждый контакт
                String contact = cursor.getString(
                        cursor.getColumnIndex(String.valueOf(CallLog.Calls.OUTGOING_TYPE)));
                String hasPhone = cursor.getString(
                        cursor.getColumnIndex(String.valueOf(CallLog.Calls.INCOMING_TYPE)));
                // добавляем контакт в список
                recents.add(contact);
                recents.add(hasPhone);
            }
            cursor.close();
        }

        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, recents);
        // устанавливаем для списка адаптер
        miss_call.setAdapter(adapter);
    }








 /*           PhoneStateListener stateListener = new PhoneStateListener() {
                public void onCallStateChanged(int state, String incomingNumber) {
                    switch (state) {
                        case TelephonyManager.CALL_STATE_IDLE: break;
                        case TelephonyManager.CALL_STATE_OFFHOOK: break;
                        case TelephonyManager.CALL_STATE_RINGING:
                            doMagicWork(incomingNumber); // Поступил звонок с номера incomingNumber
                            break;
                    }
                }
            };

            TelephonyManager.listen(stateListener, PhoneStateListener.LISTEN_CALL_STATE); // Помещаем в onCreate активности  */


/*            @Override
            public void onReceive(Context context, Intent intent) {
                String phoneState = intent.getStringExtra (TelephonyManager.EXTRA_STATE);
                if (phoneState.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                    String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                    doMagicWork(incomingNumber); // Поступил звонок с номера incomingNumber
                }
            }
        } */




        }




