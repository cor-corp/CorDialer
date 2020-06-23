package com.corcorp.cordialer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Dialer extends Activity {

    Button btnOne;
    Button btnTwo;
    Button btnThree;
    Button btnFour;
    Button btnFive;
    Button btnSix;
    Button btnSeven;
    Button btnEight;
    Button btnNine;
    Button btnZero;
    Button btnStar;
    Button btnDigit;
    Button btnDial;
    Button btnDelete;
    EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialer);

        btnOne = findViewById(R.id.buttonOne);
        btnTwo = findViewById(R.id.buttonTwo);
        btnThree = findViewById(R.id.buttonThree);
        btnFour = findViewById(R.id.buttonFour);
        btnFive = findViewById(R.id.buttonFive);
        btnSix = findViewById(R.id.buttonSix);
        btnSeven = findViewById(R.id.buttonSeven);
        btnEight = findViewById(R.id.buttonEight);
        btnNine = findViewById(R.id.buttonNine);
        btnZero = findViewById(R.id.buttonZero);
        btnStar = findViewById(R.id.buttonStar);
        btnDigit = findViewById(R.id.buttonDigit);
        btnDial = findViewById(R.id.buttonDial);
        btnDelete = findViewById(R.id.buttonDelete);

        input = findViewById(R.id.editText);
    }

    public void one(View v) {
        oNButtonClick(btnOne, input, "1");

    }

    public void two(View v) {
        oNButtonClick(btnTwo, input, "2");

    }

    public void three(View v) {
        oNButtonClick(btnThree, input, "3");

    }

    public void four(View v) {
        oNButtonClick(btnFour, input, "4");

    }

    public void five(View v) {
        oNButtonClick(btnFive, input, "5");

    }

    public void six(View v) {
        oNButtonClick(btnSix, input, "6");

    }

    public void seven(View v) {
        oNButtonClick(btnSeven, input, "7");

    }

    public void eight(View v) {
        oNButtonClick(btnEight, input,"8");

    }

    public void nine(View v) {
        oNButtonClick(btnNine, input, "9");

    }

    public void zero(View v) {
        oNButtonClick(btnZero, input, "0");

    }

    public void star(View v) {
        oNButtonClick(btnStar, input, "*");

    }

    public void digit(View v) {
        oNButtonClick(btnDigit, input, "#");

    }



    public void delete(View v) {
        input.setText("");

    }

    public void onDial (View v) {
        if (input.getText().length() <= 3) {
            Toast.makeText(this, "Please Enter Valid Number", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL);

            String digit = input.getText().toString();
            if (digit.contains("#")) {
                digit.replace("#", "#23");
            }

            intent.setData(Uri.parse("tel:" + digit));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }else {
                startActivity(intent);
            }
        }
    }


    public void oNButtonClick(Button button, EditText inputNumber, String number  ) {
        String cache = input.getText().toString();
        inputNumber.setText(cache + number);

    }


}