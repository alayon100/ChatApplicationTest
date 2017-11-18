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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.itver.alayon.chatapplicationtest.R;

import java.util.HashMap;

public class RegisterUserActivity extends AppCompatActivity implements View.OnClickListener{

    //FIREBASE
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    //UI ELEMENTS
    private Toolbar toolbar;
    private TextInputLayout textUserName;
    private TextInputLayout textEmail;
    private TextInputLayout textPassword;
    private Button buttonRegister;

    //PROGRESS DIALOG
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        initComponents();
    }

    private void initComponents(){
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        textUserName = (TextInputLayout) findViewById(R.id.inputLayoutUserName);
        textEmail = (TextInputLayout) findViewById(R.id.inputLayoutEmail);
        textPassword = (TextInputLayout) findViewById(R.id.inputLayoutPassword);
        buttonRegister = (Button) findViewById(R.id.buttonCreateUser);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        progressDialog = new ProgressDialog(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        buttonRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        String userName = textUserName.getEditText().getText().toString().trim();
        String email = textEmail.getEditText().getText().toString().trim();
        String password = textPassword.getEditText().getText().toString().trim();
        if(!userName.isEmpty() && !email.isEmpty() && !password.isEmpty()){
            progressDialog.setTitle("Registering user");
            progressDialog.setMessage("Registering user " + userName);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            registerUserOnDataBase(userName, email, password);
        }else{
            Toast.makeText(RegisterUserActivity.this, "Please complete all fields", Toast.LENGTH_LONG).show();

        }

    }


    private void registerUserOnDataBase(final String userName, String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    //Recuperar el id del nuevo usuario y registrarlo en la  realTimeDatabase
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    String uid = currentUser.getUid();
                    //recuperar la referencia de la bd en donde se escribira al nuevo usuario
                    reference = database.getReference().child("usuarios").child(uid);

                    HashMap<String, String> user = new HashMap<>();
                    user.put("user_name", userName);
                    user.put("status", "Conectado");
                    user.put("profile_image", "default");
                    user.put("thumb_image", "default");

                    reference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                //Regresamos al main activity
                                Intent intent = new Intent(RegisterUserActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });

                 }else{
                    progressDialog.hide();
                    Toast.makeText(RegisterUserActivity.this, "Have some Error in the fields", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
