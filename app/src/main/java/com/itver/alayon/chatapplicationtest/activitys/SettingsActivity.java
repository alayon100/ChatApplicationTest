package com.itver.alayon.chatapplicationtest.activitys;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.itver.alayon.chatapplicationtest.R;
import com.itver.alayon.chatapplicationtest.utils.Utils;
import com.theartofdev.edmodo.cropper.CropImage;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    //FIREBASE
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    //UI ELEMENTS
    private TextView textViewUserName;
    private TextView textViewStatus;
    private Button buttonChangeStatus;
    private Button buttonChangeImage;

    //DATA FROM DATABASE
    private String uid;
    private String userName;
    private String status;

    private static final int GALLERY_RESULT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        textViewUserName = (TextView) findViewById(R.id.textViewUserName);
        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        buttonChangeStatus = (Button) findViewById(R.id.buttonChangeStatus);
        buttonChangeImage = (Button) findViewById(R.id.buttonChangePhoto);

        buttonChangeStatus.setOnClickListener(this);
        buttonChangeImage.setOnClickListener(this);

        user = FirebaseAuth.getInstance().getCurrentUser();  //RECUPERACION DEL USUARIO DENTRO DE LA APP
        uid = user.getUid();                          //RECUPERACION DEL ID DEL USUARIO
        databaseReference = FirebaseDatabase.getInstance().getReference().child("usuarios").child(uid); //RECUPERACION A LA REFERENCIA DE USUARIOS CON EL UID
        storageReference = FirebaseStorage.getInstance().getReference();

        //LISTENER DE LA REFERENCIA
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                userName = dataSnapshot.child("user_name").getValue().toString();
                status = dataSnapshot.child("status").getValue().toString();

                //Volcar sobre los elementos del UI
                textViewUserName.setText(userName);
                textViewStatus.setText(status);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.buttonChangeStatus:
                //IR A LA ACTIVIDAD DE STATUS ACTIVITY
                Intent i = new Intent(SettingsActivity.this, StatusActivity.class);
                i.putExtra("userName", userName);
                i.putExtra("status", status);
                i.putExtra("uid", uid);
                startActivity(i);
                break;
            case R.id.buttonChangePhoto:
                //IR AL ACTIVITY PARA CAMBIAR LA FOTO -> Galeria

                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Select Image"), GALLERY_RESULT);
                break;

                /*CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
                break;
                */
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GALLERY_RESULT && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            CropImage.activity(imageUri).start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                Uri resultUri = result.getUri();
                //Generar un nombre aleatorio ala imagen y guardarla en la base de datos
                String nameRandomFile = Utils.getRandomString();

                StorageReference pathImage = storageReference.child("imagenes_perfil").child(nameRandomFile + ".jpg");
                pathImage.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(SettingsActivity.this, "Uploading image...", Toast.LENGTH_SHORT).show();
                    }
                });

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
