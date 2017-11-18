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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.itver.alayon.chatapplicationtest.R;

import java.util.HashMap;
import java.util.Map;

public class StatusActivity extends AppCompatActivity implements View.OnClickListener {

    //UI ELEMENTS
    private Toolbar toolbar;
    private TextInputLayout textUserStatus;
    private TextInputLayout textUserName;
    private Button buttonSaveChanges;
    private ProgressDialog progressDialog;

    //FIREBASE
    private DatabaseReference reference;
    private String uid;
    private String userName;
    private String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        initComponents();
    }

    private void initComponents() {

        //RECUPERAR LOS DATOS QUE LE LLEGAN DEL OTRO ACTIVITY
        Bundle bundle = getIntent().getExtras();
        uid = bundle.getString("uid");
        userName = bundle.getString("userName");
        status = bundle.getString("status");

        reference = FirebaseDatabase.getInstance().getReference().child("usuarios").child(uid);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        textUserStatus = (TextInputLayout) findViewById(R.id.inputTextStatus);
        textUserName = (TextInputLayout) findViewById(R.id.inputTextNameUser);
        buttonSaveChanges = (Button) findViewById(R.id.buttonSaveStatus);
        progressDialog = new ProgressDialog(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Account Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Volcar los datos en las vistas
        textUserStatus.getEditText().setText(status);
        textUserName.getEditText().setText(userName);

        buttonSaveChanges.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        String newStatus = textUserStatus.getEditText().getText().toString().trim();
        String newUserName = textUserName.getEditText().getText().toString().trim();

        if ((!newStatus.isEmpty() && !newUserName.isEmpty())
                && (!newStatus.equals(status) || !newUserName.equals(userName))) {

            progressDialog.setTitle("Saving changes");
            progressDialog.setMessage("Please wait while saving changes");
            progressDialog.show();

            Map<String, String> update = new HashMap<>();
            update.put("user_name", newUserName);
            update.put("status", newStatus);
            update.put("profile_image", "default");
            update.put("thumb_image", "default");

            reference.setValue(update).addOnCompleteListener(new OnCompleteListener<Void>() {

                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Intent intent = new Intent(StatusActivity.this, SettingsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(StatusActivity.this, "Ocurred some error on Database", Toast.LENGTH_SHORT).show();

                    }
                }

            });

        } else {
            Toast.makeText(StatusActivity.this, "Ocurred some error, please check the fields", Toast.LENGTH_SHORT).show();
        }

    }
}
