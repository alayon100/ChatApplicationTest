package com.itver.alayon.chatapplicationtest.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.itver.alayon.chatapplicationtest.R;

public class StartActivity extends AppCompatActivity {

    private Button buttonRegister;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Lanzar la actividad de registro
                Intent intentRegisterUser = new Intent(StartActivity.this, RegisterUserActivity.class);
                startActivity(intentRegisterUser);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoginUser = new Intent(StartActivity.this, LoginUserActivity.class);
                startActivity(intentLoginUser);
            }
        });
    }
}
