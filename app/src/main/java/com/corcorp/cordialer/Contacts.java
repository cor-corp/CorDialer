package com.corcorp.cordialer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Contacts extends AppCompatActivity {
    private static final int REQUEST_CODE_READ_CONTACTS = 1;
    private static boolean READ_CONTACTS_GRANTED = false;
    Button btnDelete;
    EditText input;
    ListView contactList;
    ArrayList<String> contacts = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        contactList = (ListView) findViewById(R.id.contactList);
        btnDelete = findViewById(R.id.buttonDelete);
        input = findViewById(R.id.editText);


        // получаем разрешения
        int hasReadContactPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        // если устройство до API 23, устанавливаем разрешение
        if (hasReadContactPermission == PackageManager.PERMISSION_GRANTED) {
            READ_CONTACTS_GRANTED = true;
        } else {
            // вызываем диалоговое окно для установки разрешений
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE_READ_CONTACTS);
        }
        // если разрешение установлено, загружаем контакты
        if (READ_CONTACTS_GRANTED) {
            loadContacts();


            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
            bottomNavigationView.setSelectedItemId(R.id.contacts);
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
                            startActivity(new Intent(getApplicationContext()
                                    , Recents.class));
                            overridePendingTransition(0, 0);
                            return true;

                        case R.id.contacts:
                            return true;
                    }
                    return false;
                }
            });
        }
    }

    public void clickDialer(View view) {
        Intent intent = new Intent(Contacts.this, Dialer.class);
        startActivity(intent);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case REQUEST_CODE_READ_CONTACTS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    READ_CONTACTS_GRANTED = true;
                }
        }
        if (READ_CONTACTS_GRANTED) {
            loadContacts();
        } else {
            Toast.makeText(this, "Требуется установить разрешения", Toast.LENGTH_LONG).show();
        }
    }

    private void loadContacts() {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {

                // получаем каждый контакт
                String contact = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                String hasPhone = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                // добавляем контакт в список
                contacts.add(contact);
                contacts.add(hasPhone);
            }
            cursor.close();
        }

        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, contacts);
        // устанавливаем для списка адаптер
        contactList.setAdapter(adapter);
    }
}