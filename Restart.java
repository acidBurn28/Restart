package com.thegame.Restart;

import android.app.Activity;

import android.content.Intent;

import android.os.Bundle;
import android.os.UserManager;
import android.view.View;
import android.widget.Button;

public class Restart extends Activity {

    Button btnSignUp;
    Button btnSignIn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_log);

        // Buttons
        btnSignUp = (Button) findViewById(R.id.signin);
        btnSignIn = (Button) findViewById(R.id.signup);

        // view products click event
        btnSignUp.setOnClickListener(view -> {
            // Launching All products Activity
            Intent i = new Intent(getApplicationContext(), Reg.class);
            startActivity(i);

        });

        // view products click event
        btnSignIn.setOnClickListener(view -> {
            // Launching create new product activity
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);

        });
    }
}
