package com.itver.alayon.chatapplicationtest.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.itver.alayon.chatapplicationtest.R;

public class LoginUserActivity extends AppCompatActivity implements View.OnClickListener{

    //FIREBASE
    private FirebaseAuth mAuth;

    //UI ELEMENTS
    private Toolbar toolbar;
    private TextInputLayout textUserEmail;
    private TextInputLayout textUserPassword;
    private Button buttonLogin;

    //PROGRESS DIALOG
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        initComponents();
    }

    private void initComponents(){
        mAuth = FirebaseAuth.getInstance();
        textUserEmail = (TextInputLayout) findViewById(R.id.inputLayoutEmail);
        textUserPassword = (TextInputLayout) findViewById(R.id.inputLayoutPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        progressDialog = new ProgressDialog(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        buttonLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String email = textUserEmail.getEditText().getText().toString().trim();
        String password = textUserPassword.getEditText().getText().toString().trim();
        if(!email.isEmpty() && !password.isEmpty()){
            progressDialog.setTitle("Login user");
            progressDialog.setMessage("Authenticating " + email);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            getUserAndLoginOnDatabase(email, password);
        }else{
            Toast.makeText(LoginUserActivity.this, "Please complete the fields", Toast.LENGTH_LONG).show();
        }
    }

    private void getUserAndLoginOnDatabase(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    //Ir al main activity
                    Intent intent = new Intent(LoginUserActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }else{
                    progressDialog.hide();
                    Toast.makeText(LoginUserActivity.this, "Have some Error in the credentials, Please try again", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}
